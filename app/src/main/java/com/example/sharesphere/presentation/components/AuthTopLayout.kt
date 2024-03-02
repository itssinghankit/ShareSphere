package com.example.sharesphere.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun AuthTopLayout(
    modifier: Modifier,
    onBackClick: () -> Unit,
    mainTxt: String,
    supportingTxt: String
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface),

        ) {

        OutlinedIconButton(
            modifier = Modifier.size(32.dp),
            onClick = onBackClick,
            shape = RectangleShape,
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline)
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = supportingTxt,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = mainTxt,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
//        Column(modifier=Modifier.wrapContentSize(),verticalArrangement = Arrangement.Bottom) {
//
//
//        }
//        Box(modifier = Modifier
//
//            .padding(32.dp),
//            contentAlignment = Alignment.BottomStart
//        ) {
//
//
//
//
//        }
        HorizontalDivider(
            Modifier
                .height(8.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
    }
}

@Composable
fun AuthTopBar(
    modifier: Modifier,
    onBackClick: () -> Unit,
    mainTxt: String,
    supportingTxt: String,
    showBackButton:Boolean=true
) {
   Surface{
       ConstraintLayout(
           modifier = modifier
               .height(250.dp)
               .fillMaxWidth()
       ) {
           val (button, text, subtext, divider) = createRefs()

           if(showBackButton){
               OutlinedIconButton(
                   modifier = Modifier
                       .size(32.dp)
                       .constrainAs(button) {
                           top.linkTo(parent.top, margin = 32.dp)
                           start.linkTo(parent.start, margin = 32.dp)
                       },
                   onClick = onBackClick,
                   shape = RectangleShape,
                   border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline)
               ) {
                   Icon(
                       modifier = Modifier.size(18.dp),
                       imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                       contentDescription = null,
                       tint = MaterialTheme.colorScheme.onSurfaceVariant
                   )

               }
           }
           Text(
               modifier = Modifier.constrainAs(subtext) {
                   bottom.linkTo(text.top, margin = 4.dp)
                   start.linkTo(parent.start, margin = 32.dp)
               },
               text = supportingTxt,
               style = MaterialTheme.typography.labelSmall,
               color = MaterialTheme.colorScheme.onSurfaceVariant
           )
           Text(
               modifier = Modifier.constrainAs(text) {
                   bottom.linkTo(divider.top, margin = 32.dp)
                   start.linkTo(parent.start, margin = 32.dp)
               },
               text = mainTxt,
               style = MaterialTheme.typography.headlineSmall,
               color = MaterialTheme.colorScheme.onSurface
           )
           HorizontalDivider(
               Modifier
                   .height(4.dp)
                   .background(MaterialTheme.colorScheme.outlineVariant)
                   .constrainAs(divider) {
                       bottom.linkTo(parent.bottom)
                   }
           )

       }
   }
}
