package com.shollmann.topreddit.repository

import java.net.UnknownHostException

object RedditRepository {

    private var remoteDataSource: RedditDataSource = RedditDataSource

    fun getTop(): ServiceResponse {
        try {
            val response = remoteDataSource.getTop()

            if (response.isSuccessful) {
                return ServiceResponse.buildSuccessful(response.body())
            }

            return ServiceResponse.buildServiceError("Response was not successful")
        } catch (e: UnknownHostException) {
            return ServiceResponse.buildNetworkError(e.message)
        } catch (e: Exception) {
            return ServiceResponse.buildServiceError(e.message)
        }
    }

}
