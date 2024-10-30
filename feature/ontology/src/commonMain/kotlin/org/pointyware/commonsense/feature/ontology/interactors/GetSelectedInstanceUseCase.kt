package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.data.RecordsRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class GetSelectedInstanceUseCase(
    private val recordsRepository: RecordsRepository
) {
    suspend operator fun invoke(categoryId: Uuid, instanceId: Uuid): Result<Value.Instance> {
        return recordsRepository.getConcepts(categoryId).mapCatching {  list ->
                list.find { it.id == conceptId } ?: throw Exception("Concept not found")
            }
    }
}
