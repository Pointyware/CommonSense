package org.pointyware.commonsense.ontology.data

import kotlinx.coroutines.flow.Flow
import org.pointyware.commonsense.core.local.KmpFile
import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.ConceptSpace
import org.pointyware.commonsense.ontology.local.ConceptSpaceDataSource

/**
 * Separates data mediation from the rest of the application.
 */
interface ConceptSpaceRepository {
    val openFile: KmpFile?
    val activeSpace: Flow<ConceptSpace>
    suspend fun loadConceptSpace(file: KmpFile): Result<ConceptSpace>
    suspend fun saveConceptSpace(file: KmpFile): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: String): Result<Unit>
}

/**
 * For now this just calls through directly to the local data source.
 */
class ConceptSpaceRepositoryImpl(
    private val dataSource: ConceptSpaceDataSource
): ConceptSpaceRepository {

    override var openFile: KmpFile? = null
        private set

    override val activeSpace: Flow<ConceptSpace>
        get() = dataSource.activeSpace

    override suspend fun loadConceptSpace(file: KmpFile): Result<ConceptSpace> {
        return dataSource.loadConceptSpace(file)
    }

    override suspend fun saveConceptSpace(file: KmpFile): Result<Unit> {
        return dataSource.saveConceptSpace(file)
    }

    override suspend fun createNode(name: String): Result<Concept> {
        return dataSource.createNode(name)
    }
    override suspend fun removeNode(id: String): Result<Unit> {
        return dataSource.removeNode(id)
    }
}
