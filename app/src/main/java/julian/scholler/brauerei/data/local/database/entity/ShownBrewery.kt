package julian.scholler.brauerei.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "shown_breweries")
data class ShownBrewery(
    @PrimaryKey val id: String,
    val shownDate: Date
)
