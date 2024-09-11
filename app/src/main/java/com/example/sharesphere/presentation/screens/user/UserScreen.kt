package com.example.sharesphere.presentation.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sharesphere.presentation.navigation.Navigator
import com.example.sharesphere.presentation.navigation.ScreenSealedClass
import com.example.sharesphere.presentation.navigation.UserGraph

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@Composable
fun UserScreen(rootNavigator: Navigator) {

    val userNavController = rememberNavController()
    val userNavigator = Navigator(userNavController)

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                BottomBar(userNavController)
            }

        }
    ) { innerPadding ->
        UserGraph(
            navController = userNavController,
            modifier = Modifier.padding(innerPadding),
            userNavigator = userNavigator,
            rootNavigator = rootNavigator
        )
    }

}

@Composable
private fun BottomBar(userNavController: NavHostController) {

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            route = ScreenSealedClass.UserScreens.HomeScreen.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Search",
            route = ScreenSealedClass.UserScreens.SearchScreen.route,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Post",
            route = ScreenSealedClass.UserScreens.PostScreen.route,
            selectedIcon = Icons.Filled.AddBox,
            unselectedIcon = Icons.Outlined.AddBox,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Notification",
            route = ScreenSealedClass.UserScreens.NotificationScreen.route,
            selectedIcon = Icons.Filled.Notifications,
            unselectedIcon = Icons.Outlined.Notifications,
            hasNews = false,
            badgeCount = 23
        ),
        BottomNavigationItem(
            title = "Profile",
            route = ScreenSealedClass.UserScreens.ProfileScreen.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = true
        )
    )

    val selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val navBackStackEntry by userNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.height(56.dp),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    userNavController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(userNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    BadgedBox(badge = {
                        if (screen.badgeCount != null) {
                            Badge {
                                Text(
                                    modifier = Modifier.padding(top = 3.dp),
                                    text = screen.badgeCount.toString()
                                )
                            }
                        } else if (screen.hasNews) {
                            Badge()
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = if (selectedItemIndex == index) screen.selectedIcon else screen.unselectedIcon,
                            contentDescription = screen.title
                        )
                    }
                }
            )
        }
    }
}