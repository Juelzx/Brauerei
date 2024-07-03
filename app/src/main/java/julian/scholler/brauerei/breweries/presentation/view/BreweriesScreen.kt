import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import julian.scholler.brauerei.breweries.presentation.view.BreweryItem
import julian.scholler.brauerei.breweries.presentation.viewmodel.BreweriesViewModel
import julian.scholler.brauerei.data.Result
import julian.scholler.brauerei.data.remote.model.Brewery

@Composable
fun BreweriesScreen(
    navController: NavController
) {
    val breweriesViewModel: BreweriesViewModel = hiltViewModel()

    val breweriesDailyState by breweriesViewModel.dailyBrewery.collectAsState()
    val breweriesState by breweriesViewModel.breweries.collectAsState()
    val searchQuery by breweriesViewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { breweriesViewModel.updateSearchQuery(it) },
            label = { Text("Search breweries") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Text(text = "Today we present you the following brewery:")
                when (breweriesDailyState) {
                    is Result.Loading -> {
                        Text(text = "Loading daily brewery...")
                    }
                    is Result.Success -> {
                        val dailyBrewery = (breweriesDailyState as Result.Success<Brewery>).data
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation()
                        ) {
                            Column {
                                BreweryItem(brewery = dailyBrewery)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                    is Result.Error -> {
                        Text(
                            text = "An error occurred: ${(breweriesDailyState as Result.Error).exception.message}"
                        )
                    }
                }
            }

            when (breweriesState) {
                is Result.Loading -> {
                    item {
                        Text(text = "Loading breweries...")
                    }
                }
                is Result.Success -> {
                    val breweries =
                        (breweriesState as Result.Success<List<Brewery>>).data
                            .filter { it.breweryName.contains(searchQuery, ignoreCase = true) }
                    items(breweries) { brewery ->
                        BreweryItem(brewery = brewery)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                is Result.Error -> {
                    item {
                        Text(
                            text = "An error occurred: ${(breweriesState as Result.Error).exception.message}"
                        )
                    }
                }
            }
        }
    }
}
