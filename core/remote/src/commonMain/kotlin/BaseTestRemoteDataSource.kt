package org.pointyware.commonsense.core.remote

/**
 * Simulates a remote data source, i.e. a network API.
 */
interface BaseTestRemoteDataSource<T> {
    /**
     * Retrieves the data from the remote data source.
     */
    suspend fun get(id: String): Result<T>
    /**
     * Stores the data in the remote data source.
     */
    suspend fun put(id: String, data: T)
    /**
     * Removes the data from the remote data source.
     */
    suspend fun delete(id: String)
}
