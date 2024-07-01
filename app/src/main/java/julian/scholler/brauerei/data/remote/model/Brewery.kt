package julian.scholler.brauerei.data.remote.model

import com.google.gson.annotations.SerializedName

data class Brewery(
    @SerializedName("id") var breweryId: String = "",
    @SerializedName("name") var breweryName: String = "",
    @SerializedName("address_1") var breweryAddress: String? = null,
    @SerializedName("city") var breweryCity: String = "",
    @SerializedName("country") var breweryCountry: String = "",
    @SerializedName("longitude") var breweryLon: String? = null,
    @SerializedName("latitude") var breweryLat: String? = null,
    @SerializedName("website_url") var breweryUrl: String? = null,
)
