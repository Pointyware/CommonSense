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
    suspend operator fun invoke(instanceId: Uuid): Result<Value.Instance> {
        return recordsRepository.getInstance(instanceId)
    }
}
