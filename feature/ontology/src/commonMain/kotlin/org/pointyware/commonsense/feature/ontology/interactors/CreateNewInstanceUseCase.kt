package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.data.RecordsRepository
import kotlin.uuid.ExperimentalUuidApi

/**
 * Creates a new concept in the currently selected category.
 */
@OptIn(ExperimentalUuidApi::class)
class CreateNewInstanceUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val recordsRepository: RecordsRepository
) {
    suspend fun invoke(newInstance: Value.Instance): Result<Value.Instance> {
        val subject = conceptEditorController.subject?.id
            ?: return Result.failure(IllegalStateException("No subject selected"))
        return recordsRepository.addInstance(subject, newInstance)
    }
}
