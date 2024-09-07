package com.example.sharesphere.presentation.screens.authentication.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sharesphere.R
import com.example.sharesphere.presentation.components.AuthTopBar
import com.example.sharesphere.presentation.components.ComponentButton
import com.example.sharesphere.presentation.components.ComponentTextField
import com.example.sharesphere.presentation.components.ConnectionLostScreen
import com.example.sharesphere.presentation.components.DefinedSnackBarHost
import com.example.sharesphere.presentation.components.Loading
import com.example.sharesphere.presentation.ui.theme.Black05
import com.example.sharesphere.presentation.ui.theme.Black13
import com.example.sharesphere.presentation.ui.theme.Black43
import com.example.sharesphere.presentation.ui.theme.Black70
import com.example.sharesphere.util.NetworkMonitor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel,
    onEvent: (DetailsEvents) -> Unit,
    onBackClick: () -> Unit,
    navigateToAvatarScreen: (fullName: String, dob: Long, gender: String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val textFieldState = viewModel.textFieldStates
    val dateState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val isEnabled by derivedStateOf {
        textFieldState.fullName.isEmpty() || uiState.isFullNameError || dateState.selectedDateMillis == null
    }


    //showing snackBar
    uiState.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    //for navigation
    DisposableEffect(uiState.navigate) {
        if (uiState.navigate) {
            navigateToAvatarScreen(
                textFieldState.fullName,
                dateState.selectedDateMillis ?: 0,
                uiState.gender.text
            )
        }
        onDispose {
            onEvent(DetailsEvents.OnNavigationDone)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            AuthTopBar(
                modifier = Modifier,
                onBackClick = onBackClick,
                mainTxt = stringResource(R.string.tell_us_your_details),
                supportingTxt = stringResource(id = R.string.sharesphere_application)
            )
        },
        bottomBar = {
            if (!networkState.isAvailable()) {

                focusManager.clearFocus(true)
                keyboard?.hide()
                ConnectionLostScreen()

            } else if (uiState.isLoading) {
                Loading(color = Black05)
            } else {
                ComponentButton(
                    text = stringResource(id = R.string.next),
                    contColor = Black05,
                    txtColor = Color.White,
                    iconTint = Color.White,
                    isTrailingIconButton = true,
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 32.dp, bottom = 32.dp),
                    enabled = !isEnabled
                ) {
                    scope.launch {
                        onEvent(DetailsEvents.OnNextClicked(dateState.selectedDateMillis))
                    }
                }
            }

        },
        snackbarHost = {
            DefinedSnackBarHost(hostState = snackBarHostState) {
                keyboard?.hide()
                onEvent(DetailsEvents.OnSnackBarShown)
            }
        }

    ) { paddingValues ->

        DetailsContent(
            modifier = Modifier.padding(paddingValues),
            isFullNameError = uiState.isFullNameError,
            onKeyboardNextClick = {
                focusManager.clearFocus()
                keyboard?.hide()
            },
            onFullNameValueChange = { onEvent(DetailsEvents.OnFullNameValueChange(it)) },
            fullName = textFieldState.fullName,
            gender = uiState.gender,
            dateState = dateState
        ) {
            onEvent(DetailsEvents.OnGenderSelected(it))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    modifier: Modifier,
    onKeyboardNextClick: () -> Unit,
    onFullNameValueChange: (String) -> Unit,
    fullName: String,
    isFullNameError: Boolean,
    gender: Gender,
    dateState: DatePickerState,
    onGenderSelected: (Gender) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())

    ) {

        ComponentTextField(
            label = stringResource(R.string.full_name),
            modifier = Modifier,
            value = fullName,
            onValueChange = { onFullNameValueChange(it) },
            leadingIconImageVector = Icons.Default.Person,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(onDone = {
                onKeyboardNextClick()
            }),
            showError = isFullNameError,
            errorMessage = stringResource(id = R.string.validateFullNameError),
        )


        GenderRadioButtonGroup(
            modifier = Modifier.padding(top = 40.dp),
            selectedGender = gender,
            onGenderSelected = {
                onGenderSelected(it)
            })


//        Timber.d(state.selectedDateMillis.toString())
//        val selectedDate = LocalDate.ofEpochDay(state.selectedDateMillis!!)
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//        val formattedDate= dateFormat.format(selectedDate)
        DatePicker(
            modifier = Modifier.padding(top = 16.dp),
            state = dateState,
            title = null,
            headline = {
                Text(
                    text = "Date Of Birth",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            showModeToggle = true,
            colors = DatePickerDefaults.colors(
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedTextColor = Black13,
                    unfocusedTextColor = Black13,
                    errorSupportingTextColor = Color.Red,
                    errorTextColor = Color.Red,
                    cursorColor = Black13,
                    errorCursorColor = Color.Red,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedIndicatorColor = Black13,
                    unfocusedIndicatorColor = Black70,
                    errorIndicatorColor = Color.Red,
                    focusedLeadingIconColor = Black13,
                    unfocusedLeadingIconColor = Black70,
                    errorLeadingIconColor = Color.Red,
                    focusedTrailingIconColor = Black13,
                    unfocusedTrailingIconColor = Black13,
                    errorTrailingIconColor = Color.Red,
                    focusedLabelColor = Black13,
                    unfocusedLabelColor = Black70,
                    errorLabelColor = Color.Red,
                ),
                containerColor = Color.White,
                weekdayContentColor = Black05,
                dayContentColor = Black70,
                selectedDayContainerColor = Black05,
                dividerColor = Black70,
                selectedYearContainerColor = Black05,
                currentYearContentColor = Black05,
                selectedDayContentColor = Color.White,
                selectedYearContentColor = Color.White,
                headlineContentColor = Black43,
                todayContentColor = Black05,
                todayDateBorderColor = Black05,
                yearContentColor = Black05,
                navigationContentColor = Black05,
            )
        )

    }

}

@Composable
fun GenderRadioButtonGroup(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Gender",
            color = Black43,
            style = MaterialTheme.typography.bodyLarge
        )

        Row(

        ) {
            RadioButtonWithTxt(
                selected = selectedGender == Gender.MALE,
                text = "Male",
                onClick = { onGenderSelected(Gender.MALE) },
            )
            RadioButtonWithTxt(
                selected = selectedGender == Gender.FEMALE,
                text = "Female",
                onClick = { onGenderSelected(Gender.FEMALE) },
            )
            RadioButtonWithTxt(
                selected = selectedGender == Gender.OTHER,
                text = "Other",
                onClick = { onGenderSelected(Gender.OTHER) },
            )
        }
    }
}

@Composable
fun RadioButtonWithTxt(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    text: String
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            colors = RadioButtonDefaults.colors(selectedColor = Black05, unselectedColor = Black70),
            selected = selected,
            onClick = onClick
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 4.dp, end = 24.dp),
            color = if (selected) Black05 else Black70
        )
    }
}

enum class Gender(val text: String) {
    MALE("male"), FEMALE("female"), OTHER("other")
}

