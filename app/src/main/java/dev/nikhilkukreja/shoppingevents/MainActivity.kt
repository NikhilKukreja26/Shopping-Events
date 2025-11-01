package dev.nikhilkukreja.shoppingevents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.nikhilkukreja.shoppingevents.destinations.AddEventRoute
import dev.nikhilkukreja.shoppingevents.destinations.EventDetailsRoute
import dev.nikhilkukreja.shoppingevents.destinations.HomeRoute
import dev.nikhilkukreja.shoppingevents.ui.addevent.AddEventPage
import dev.nikhilkukreja.shoppingevents.ui.eventdetails.EventDetailsPage
import dev.nikhilkukreja.shoppingevents.ui.home.HomePage
import dev.nikhilkukreja.shoppingevents.ui.theme.ShoppingEventsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           ShoppingApp()
        }
    }
}


@Composable
fun ShoppingApp(modifier: Modifier = Modifier) {
    ShoppingEventsTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = HomeRoute) {
            composable<HomeRoute> {
                HomePage(
                    modifier = modifier,
                    navigateToAddEvent = {
                        navController.navigate<AddEventRoute>(route = AddEventRoute)
                    },
                    onNavigateToEventDetails = { id, name ->
                        navController.navigate<EventDetailsRoute>(route = EventDetailsRoute(eventId = id, eventName = name))
                    }
                )
            }

            composable<AddEventRoute> {
                AddEventPage(
                    navigateUp = {
                        navController.navigateUp()
                    },
                    navigateBack = {
                        navController.popBackStack()
                    },
                )
            }

            composable<EventDetailsRoute> {
                EventDetailsPage(
                    modifier = modifier,
                    navigateUp = {
                        navController.navigateUp()
                    },
                    navigateBack = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}
