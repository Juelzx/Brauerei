package julian.scholler.brauerei.breweriedaily.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import julian.scholler.brauerei.breweriedaily.presentation.viewmodel.BreweryDailyViewModel
import julian.scholler.brauerei.breweries.presentation.viewmodel.BreweriesViewModel

@Composable
fun BreweryDailyScreen(
    navController: NavController
) {

    val viewModel: BreweryDailyViewModel = hiltViewModel()
//    val breweriesState by viewModel

}