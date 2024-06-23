package org.pointyware.commonsense.ontology.data

import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.ConceptSpace
import org.pointyware.commonsense.ontology.local.ConceptSpaceDataSource

/**
 * Separates data mediation from the rest of the application.
 */
interface ConceptSpaceRepository {
    suspend fun loadConceptSpace(id: String): Result<ConceptSpace>
    suspend fun saveConceptSpace(space: ConceptSpace): Result<Unit>
    suspend fun createNode(name: String): Result<Concept>
    suspend fun removeNode(id: String): Result<Unit>
}

/**
 * For now this just calls through directly to the local data source.
 */
class ConceptSpaceRepositoryImpl(
    private val dataSource: ConceptSpaceDataSource
): ConceptSpaceRepository {

    private var activeSpace: ConceptSpace? = null

    override suspend fun loadConceptSpace(id: String): Result<ConceptSpace> {
        activeSpace = null
        return dataSource.loadConceptSpace(id).onSuccess {
            activeSpace = it
        }
    }

    override suspend fun saveConceptSpace(space: ConceptSpace): Result<Unit> {
        return dataSource.saveConceptSpace(space)
    }

    override suspend fun createNode(name: String): Result<Concept> {
        return dataSource.createNode(name)
    }
    override suspend fun removeNode(id: String): Result<Unit> {
        return Result.failure(NotImplementedError("Not yet implemented"))
    }
}
