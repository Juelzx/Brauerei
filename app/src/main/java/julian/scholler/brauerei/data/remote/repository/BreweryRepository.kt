package julian.scholler.brauerei.data.remote.repository

import julian.scholler.brauerei.data.Result
import julian.scholler.brauerei.data.remote.api.BreweryService
import julian.scholler.brauerei.data.remote.model.Brewery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class BreweryRepository @Inject constructor(private val service: BreweryService) {

    suspend fun getBreweries(): Flow<Result<List<Brewery>>> {
        return flow {
            emit(Result.Loading)
            try {
                val breweries = service.getBreweries()
                Timber.d("breweries $breweries")
                emit(Result.Success(breweries))
            } catch (e: Exception) {
                emit(Result.Error(e))
                Timber.e(e)
            }
        }
    }

    suspend fun getRandomBrewery(): Flow<Result<Brewery>> {
        return flow {
            emit(Result.Loading)
            try {
                val randomBrewery = service.getRandomBrewery()
                emit(Result.Success(randomBrewery))
            } catch (e: Exception) {
                emit(Result.Error(e))
                Timber.e(e)
            }
        }
    }
}