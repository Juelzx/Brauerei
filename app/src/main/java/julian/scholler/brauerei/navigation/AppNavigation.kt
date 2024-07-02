package julian.scholler.brauerei.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import julian.scholler.brauerei.breweriedaily.presentation.view.BreweryDailyScreen
import julian.scholler.brauerei.breweries.presentation.view.BreweriesScreen

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier, startDestination: String = Routes.BREWERIES) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.BREWERIES) {
            BreweriesScreen(navController)
        }
        composable(Routes.BREWERY_DAILY) {
            BreweryDailyScreen(navController)
        }
    }
}