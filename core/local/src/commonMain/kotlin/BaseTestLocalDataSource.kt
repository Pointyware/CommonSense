package org.pointyware.commonsense.core.local

/**
 * Simulates a local data store, i.e. a cache or database.
 */
interface BaseTestLocalDataSource<T> {
    /**
     * Retrieves the data from the local data store.
     */
    suspend fun get(key: String): Result<T>
    /**
     * Stores the data in the local data store.
     */
    suspend fun put(key: String, data: T)
    /**
     * Removes the data from the local data store.
     */
    suspend fun remove(key: String)
}
