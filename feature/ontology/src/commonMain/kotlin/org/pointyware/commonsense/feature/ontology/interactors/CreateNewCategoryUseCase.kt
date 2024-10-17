package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.local.generateRandomId
import kotlin.uuid.ExperimentalUuidApi

/**
 * Creates a new concept in the currently selected category.
 */
@OptIn(ExperimentalUuidApi::class)
class CreateNewCategoryUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val categoryRepository: CategoryRepository
) {
    suspend fun invoke(name: String): Result<Category> {
        val subject = conceptEditorController.subject?.id
            ?: return Result.failure(IllegalStateException("No subject selected"))
        val newCategory = Category(generateRandomId(), name)
        return categoryRepository.addCategory(subject, newCategory)
    }
}
