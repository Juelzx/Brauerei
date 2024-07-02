package julian.scholler.brauerei.data.remote.api

import julian.scholler.brauerei.data.remote.model.Brewery
import retrofit2.http.GET

interface BreweryService {

    @GET("breweries")
    suspend fun getBreweries(): List<Brewery>

    @GET("breweries/random")
    suspend fun getRandomBrewery(): List<Brewery>
}
