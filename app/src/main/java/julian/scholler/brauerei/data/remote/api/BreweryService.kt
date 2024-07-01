package julian.scholler.brauerei.data.remote.api

import julian.scholler.brauerei.data.remote.model.Brewery
import retrofit2.http.GET

interface BreweryService {

    @GET("breweries")
    suspend fun getBreweries(): List<Brewery>

    @GET("random")
    suspend fun getRandomBrewery(): Brewery

}