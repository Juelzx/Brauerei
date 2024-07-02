package julian.scholler.brauerei.breweries.presentation.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import julian.scholler.brauerei.breweries.presentation.viewmodel.BreweriesViewModel
import julian.scholler.brauerei.data.Result
import julian.scholler.brauerei.data.remote.model.Brewery

@Composable
fun BreweriesScreen(
    navController: NavController
) {
    val viewModel: BreweriesViewModel = hiltViewModel()
    val breweriesState by viewModel.breweries.collectAsState()

    when (breweriesState) {
        is Result.Loading -> {
            Text(text = "Loading...")
        }

        is Result.Success -> {
            val breweries = (breweriesState as Result.Success<List<Brewery>>).data
            LazyColumn(Modifier.fillMaxWidth()) {
                items(breweries) { brewery ->
                    BreweryItem(brewery = brewery)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        is Result.Error -> {
            Text(text = "An error occurred: ${(breweriesState as Result.Error).exception.message}")
        }
    }
}
