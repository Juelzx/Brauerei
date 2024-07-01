package julian.scholler.brauerei.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import julian.scholler.brauerei.data.local.database.dao.ShownBreweryDao
import julian.scholler.brauerei.data.local.database.entity.ShownBrewery
import julian.scholler.brauerei.data.local.database.utils.DateConverter

@Database(entities = [ShownBrewery::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shownBreweryDao(): ShownBreweryDao
}