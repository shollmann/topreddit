package com.shollmann.topreddit.repository

import androidx.test.runner.AndroidJUnit4
import com.shollmann.topreddit.model.Listing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TripsRepositoryIntegrationTest {

    @Test
    fun getTop() = runBlocking {
        val serviceResponse = withContext(Dispatchers.IO) {
            RedditRepository.getTop()
        }
        assertNotNull(serviceResponse)
        assertNotNull(serviceResponse.response)
        assertEquals(ResponseCode.OK, serviceResponse.code)
        assertNotNull((serviceResponse.response as Listing).getPosts()[0].title)
    }
}
