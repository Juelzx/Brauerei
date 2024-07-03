package julian.scholler.brauerei.navigation

import BreweriesScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigationGraph(startDestination: String = Routes.BREWERIES) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.BREWERIES) {
            BreweriesScreen(navController)
        }
        composable(Routes.BREWERY_DAILY) {
//            BreweryDailyScreen(navController) removed for simplicity
        }
    }
}