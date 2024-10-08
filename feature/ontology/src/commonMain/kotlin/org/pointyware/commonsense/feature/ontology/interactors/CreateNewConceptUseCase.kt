package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController

/**
 * Creates a new concept in the currently selected category.
 */
class CreateNewConceptUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val categoryRepository: CategoryRepository
) {
    suspend fun invoke(name: String, description: String): Result<Concept> {
        val subject = conceptEditorController.subject?.id
            ?: return Result.failure(IllegalStateException("No subject selected"))
        val newConcept = IndependentConcept(Uuid.v4(), name, description)
        return categoryRepository.addConcept(subject, newConcept)
    }
}
