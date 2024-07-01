package julian.scholler.brauerei.breweriedaily.data.repository

import android.icu.util.Calendar
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

class BreweryDailyRepository @Inject constructor(
    private val service: BreweryService,
    private val shownBreweryDao: ShownBreweryDao
) {

    suspend fun getRandomBrewery(): Flow<Brewery> {
        return flow {
            try {
                val dateLimit = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, -30)
                }.time
                Timber.d("dateLimit $dateLimit")

                val shownBreweries = shownBreweryDao.getShownBreweriesSince(dateLimit)
                val shownBreweryIds = shownBreweries.map { it.id }.toSet()

                var brewery: Brewery? = null
                var attempt = 0
                val maxAttempts = 10

                while (attempt < maxAttempts && brewery == null) {
                    val candidateBrewery = service.getRandomBrewery()
                    if (candidateBrewery.breweryId !in shownBreweryIds) {
                        brewery = candidateBrewery
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
