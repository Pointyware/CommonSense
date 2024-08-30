package org.pointyware.commonsense.feature.ontology.category.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.category.data.CategoryRepository
import org.pointyware.commonsense.feature.ontology.entities.Category

/**
 */
class GetSelectedCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Uuid): Result<CategoryInfo> {
        val category = categoryRepository.getCategory(categoryId)
            .onSuccess { category ->
                val subcategories = categoryRepository.getSubcategories(category.id)
                    .onFailure { return Result.failure(it) }.getOrNull() ?: emptyList()
                val concepts = categoryRepository.getConcepts(category.id)
                    .onFailure { return Result.failure(it) }.getOrNull() ?: emptyList()
                return Result.success(CategoryInfo(category.id, category, subcategories, concepts))
            }
        return Result.failure(category.exceptionOrNull() ?: Exception("Category not found"))
    }
}

data class CategoryInfo(
    val id: Uuid,
    val subject: Category,
    val subcategories: List<Category>,
    val concepts: List<Concept>
)
