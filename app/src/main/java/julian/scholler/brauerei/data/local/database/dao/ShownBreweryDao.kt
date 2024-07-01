package julian.scholler.brauerei.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import julian.scholler.brauerei.data.local.database.entity.ShownBrewery
import java.util.Date

@Dao
interface ShownBreweryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShownBrewery(shownBrewery: ShownBrewery)

    @Query("SELECT * FROM shown_breweries WHERE shownDate > :dateLimit")
    suspend fun getShownBreweriesSince(dateLimit: Date): List<ShownBrewery>
}