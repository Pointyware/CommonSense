package org.pointyware.commonsense.feature.ontology.category.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.category.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.entities.Category

/**
 * Creates a new concept in the currently selected category.
 */
class CreateNewCategoryUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val categoryRepository: CategoryRepository
) {
    suspend fun invoke(name: String): Result<Unit> {
        val subject = conceptEditorController.subject?.id
            ?: return Result.failure(IllegalStateException("No subject selected"))
        val newCategory = Category(Uuid.v4(), name)
        return categoryRepository.addCategory(subject, newCategory)
    }
}
