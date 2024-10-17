package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.local.generateRandomId
import kotlin.uuid.ExperimentalUuidApi

/**
 * Creates a new concept in the currently selected category.
 */
@OptIn(ExperimentalUuidApi::class)
class CreateNewConceptUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val categoryRepository: CategoryRepository
) {
    suspend fun invoke(name: String, description: String): Result<Concept> {
        val subject = conceptEditorController.subject?.id
            ?: return Result.failure(IllegalStateException("No subject selected"))
        val newConcept = IndependentConcept(generateRandomId(), name, description)
        return categoryRepository.addConcept(subject, newConcept)
    }
}
