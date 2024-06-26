package org.pointyware.commonsense.feature.ontology.data

import kotlinx.coroutines.flow.Flow
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.core.local.LocalStorage
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.local.ConceptSpaceDataSource

/**
 * Separates data mediation from the rest of the application.
 */
interface ConceptSpaceRepository {
    val openFile: LocalStorage?
    val activeSpace: Flow<ConceptSpace>
    suspend fun loadConceptSpace(file: LocalStorage): Result<ConceptSpace>
    suspend fun saveConceptSpace(file: LocalStorage): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: Uuid): Result<Unit>
    suspend fun updateNode(id: Uuid, title: String, description: String? = null): Result<Unit>
}

/**
 * For now this just calls through directly to the local data source.
 */
class ConceptSpaceRepositoryImpl(
    private val dataSource: ConceptSpaceDataSource
): ConceptSpaceRepository {

    override var openFile: LocalStorage? = null
        private set

    override val activeSpace: Flow<ConceptSpace>
        get() = dataSource.activeSpace

    override suspend fun loadConceptSpace(file: LocalStorage): Result<ConceptSpace> {
        return dataSource.loadConceptSpace(file)
    }

    override suspend fun saveConceptSpace(file: LocalStorage): Result<Unit> {
        return dataSource.saveConceptSpace(file)
    }

    override suspend fun createNode(name: String): Result<Concept> {
        return dataSource.createNode(name)
    }

    override suspend fun updateNode(id: Uuid, title: String, description: String?): Result<Unit> {
        return dataSource.updateNode(id, title, description)
    }

    override suspend fun removeNode(id: Uuid): Result<Unit> {
        return dataSource.removeNode(id)
    }
}
