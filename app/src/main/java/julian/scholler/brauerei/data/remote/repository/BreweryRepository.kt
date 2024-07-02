package julian.scholler.brauerei.data.remote.repository

import android.icu.util.Calendar
import julian.scholler.brauerei.data.Result
import julian.scholler.brauerei.data.local.database.dao.ShownBreweryDao
import julian.scholler.brauerei.data.local.database.entity.ShownBrewery
import julian.scholler.brauerei.data.remote.api.BreweryService
import julian.scholler.brauerei.data.remote.model.Brewery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class BreweryRepository @Inject constructor(
    private val service: BreweryService,
    private val shownBreweryDao: ShownBreweryDao
) {

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

    suspend fun getRandomBrewery(): Flow<Brewery> {
        return flow {
            try {
                val dateLimit = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -30)
                }.time
                Timber.d("dateLimit $dateLimit")

                val shownBreweries = shownBreweryDao.getShownBreweriesSince(dateLimit)
                val shownBreweryIds = shownBreweries.map { it.id }.toSet()

                // not rly a good solution but good enough for now
                var brewery: Brewery? = null
                var attempt = 0
                val maxAttempts = 10

                while (attempt < maxAttempts && brewery == null) {
                    val candidateBreweries = service.getRandomBrewery()
                    candidateBreweries.firstOrNull { it.breweryId !in shownBreweryIds }?.let {
                        brewery = it
                    }
                    attempt++
                }

                brewery?.let {
                    emit(it)
                    shownBreweryDao.insertShownBrewery(
                        ShownBrewery(id = it.breweryId, shownDate = Date())
                    )
                } ?: Timber.e("No new brewery found after $maxAttempts attempts")

            } catch (e: Exception) {
                Timber.e(e, "Error fetching random brewery")
            }
        }.flowOn(Dispatchers.IO)
    }
}
