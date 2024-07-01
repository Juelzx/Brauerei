package julian.scholler.brauerei.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.util.*

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("breweries_prefs", Context.MODE_PRIVATE)

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun saveShownBrewery(id: String, date: Date) {
        sharedPreferences.edit {
            putString(id, dateFormat.format(date))
        }
    }

    fun getShownBreweriesSince(dateLimit: Date): Set<String> {
        val shownBreweries = sharedPreferences.all
            .filterValues { value -> 
                val date = dateFormat.parse(value as String)
                date?.after(dateLimit) == true
            }
            .keys
        return shownBreweries
    }
    
    fun clearOldEntries(dateLimit: Date) {
        val editor = sharedPreferences.edit()
        sharedPreferences.all.forEach { (key, value) ->
            val date = dateFormat.parse(value as String)
            if (date?.before(dateLimit) == true) {
                editor.remove(key)
            }
        }
        editor.apply()
    }
}
