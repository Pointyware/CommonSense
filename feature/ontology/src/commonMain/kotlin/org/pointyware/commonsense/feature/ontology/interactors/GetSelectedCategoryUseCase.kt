@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.Category
import org.pointyware.commonsense.feature.ontology.data.ConceptEditorController
import org.pointyware.commonsense.feature.ontology.data.RecordsRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 */
class GetSelectedCategoryUseCase(
    private val conceptEditorController: ConceptEditorController,
    private val recordsRepository: RecordsRepository
) {
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(categoryId: Uuid): Result<CategoryInfo> {
        return recordsRepository.getCategory(categoryId).map { category ->
            val subcategories = recordsRepository.getSubcategories(category.id)
                .onFailure { return Result.failure(it) }.getOrNull() ?: emptyList()
            val instances = recordsRepository.getInstances(category.id)
                .onFailure { return Result.failure(it) }.getOrNull() ?: emptyList()
            conceptEditorController.subject = category
            CategoryInfo(category.id, category, subcategories, instances)
        }
    }
}

data class CategoryInfo(
    val id: Uuid,
    val subject: Category,
    val subcategories: List<Category>,
    val instances: List<Value.Instance>
)
