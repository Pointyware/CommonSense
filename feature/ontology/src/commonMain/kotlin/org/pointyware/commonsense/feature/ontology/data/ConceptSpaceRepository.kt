@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.data

import kotlinx.coroutines.flow.Flow
import kotlinx.io.files.Path
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceDataSource
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Separates data mediation from the rest of the application.
 */
interface ConceptSpaceRepository {
    val openFile: Path?
    val activeSpace: Flow<ConceptSpace>
    suspend fun loadConceptSpace(file: Path): Result<ConceptSpace>
    suspend fun saveConceptSpace(file: Path): Result<Unit>
    suspend fun addNode(instance: Value.Instance): Result<Unit>
    suspend fun removeNode(id: Uuid): Result<Unit>
    suspend fun updateNode(id: Uuid, title: String, description: String? = null): Result<Unit>
}

/**
 * For now this just calls through directly to the local data source.
 */
class ConceptSpaceRepositoryImpl(
    private val dataSource: ConceptSpaceDataSource
): ConceptSpaceRepository {

    override var openFile: Path? = null
        private set

    override val activeSpace: Flow<ConceptSpace>
        get() = dataSource.activeSpace

    override suspend fun loadConceptSpace(file: Path): Result<ConceptSpace> {
        return dataSource.loadConceptSpace(file)
    }

    override suspend fun saveConceptSpace(file: Path): Result<Unit> {
        return dataSource.saveConceptSpace(file)
    }

    override suspend fun addNode(instance: Value.Instance): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateNode(id: Uuid, title: String, description: String?): Result<Unit> {
        return dataSource.updateNode(id, title, description)
    }

    override suspend fun removeNode(id: Uuid): Result<Unit> {
        return dataSource.removeNode(id)
    }
}
