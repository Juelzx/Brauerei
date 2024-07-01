package julian.scholler.brauerei.data.remote.repository

import julian.scholler.brauerei.data.remote.api.BreweryService
import julian.scholler.brauerei.data.remote.model.Brewery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class BreweryRepository @Inject constructor(private val service: BreweryService) {

    suspend fun getBreweries(): Flow<List<Brewery>> {
        return flow {
            try {
                val breweries = service.getBreweries()
                emit(breweries)
            } catch (e: Exception) {
                emit(emptyList())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRandomBrewery(): Flow<Brewery> {
        return flow<Brewery> {
            try {
                val brewery = service.getRandomBrewery()
                // implement logic to avoid duplicates within 30 days
            } catch (e: Exception) {
                Timber.e("$e getRandomBrewery() no brewery available")
            }
        }.flowOn(Dispatchers.IO)
    }
}