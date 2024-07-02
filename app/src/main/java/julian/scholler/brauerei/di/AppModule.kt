package julian.scholler.brauerei.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import julian.scholler.brauerei.data.local.database.AppDatabase
import julian.scholler.brauerei.data.local.database.dao.ShownBreweryDao
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
            .baseUrl("https://api.openbrewerydb.org/v1/")
            .build()
    }

    @Singleton
    @Provides
    fun provideBreweryService(retrofit: Retrofit): BreweryService {
        return retrofit.create(BreweryService::class.java)
    }

    @Singleton
    @Provides
    fun provideBreweryRepository(
        breweryService: BreweryService,
        shownBreweryDao: ShownBreweryDao
    ): BreweryRepository {
        return BreweryRepository(breweryService, shownBreweryDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "breweries_database"
        ).build()
    }

    @Provides
    fun provideShownBreweryDao(database: AppDatabase): ShownBreweryDao {
        return database.shownBreweryDao()
    }
}
