package julian.scholler.brauerei

import julian.scholler.brauerei.data.Result
import julian.scholler.brauerei.data.local.database.dao.ShownBreweryDao
import julian.scholler.brauerei.data.remote.api.BreweryService
import julian.scholler.brauerei.data.remote.model.Brewery
import julian.scholler.brauerei.data.remote.repository.BreweryRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BreweryRepositoryTest {

    @Mock
    private lateinit var service: BreweryService

    @Mock
    private lateinit var shownBreweryDao: ShownBreweryDao

    @Mock
    private lateinit var repository: BreweryRepository

    @Before
    fun setUp() {
        // TODO: some dependency issue avoiding proper mock setup
        repository = BreweryRepository(service, shownBreweryDao)
    }

    @Test
    fun testGetBreweriesEmitsSuccessResult() = runTest {
        val mockBreweries = listOf(
            Brewery(breweryId = "1", breweryName = "Brewery 1"),
            Brewery(breweryId = "2", breweryName = "Brewery 2")
        )
        `when`(service.getBreweries()).thenReturn(mockBreweries)

        val result = repository.getBreweries().first()

        assertTrue(result is Result.Success)
        assertEquals(mockBreweries, (result as Result.Success).data)
    }


    @Test
    fun testGetBreweriesEmitsErrorResult() = runTest {
        val exception = Exception("Network error")
        `when`(service.getBreweries()).thenThrow(exception)

        val result = repository.getBreweries().first()

        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }
}
