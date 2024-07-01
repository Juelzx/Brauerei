package julian.scholler.brauerei.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import julian.scholler.brauerei.data.remote.api.BreweryService
import julian.scholler.brauerei.data.remote.repository.BreweryRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://akabab.github.io/starwars-api/api/")
            .build()
    }

    @Singleton
    @Provides
    fun provideBreweryService(retrofit: Retrofit): BreweryService {
        return retrofit.create(BreweryService::class.java)
    }

    @Singleton
    @Provides
    fun provideStarWarsRepository(breweryService: BreweryService): BreweryRepository {
        return BreweryRepository(breweryService)
    }
}