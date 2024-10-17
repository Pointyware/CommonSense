@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.Category
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 */
class GetSelectedCategoryUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Uuid): Result<CategoryInfo> {
        return categoryRepository.getCategory(categoryId).map { category ->
            val subcategories = categoryRepository.getSubcategories(category.id)
                .onFailure { return Result.failure(it) }.getOrNull() ?: emptyList()
            val concepts = categoryRepository.getConcepts(category.id)
                .onFailure { return Result.failure(it) }.getOrNull() ?: emptyList()
            conceptEditorController.subject = category
            CategoryInfo(category.id, category, subcategories, concepts)
        }
    }
}

data class CategoryInfo(
    val id: Uuid,
    val subject: Category,
    val subcategories: List<Category>,
    val concepts: List<Concept>
)
