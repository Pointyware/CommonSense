package org.pointyware.commonsense.feature.ontology.category.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.category.data.CategoryRepository

/**
 *
 */
class GetSelectedConceptUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Uuid, conceptId: Uuid): Result<Concept> {
        return categoryRepository.getConcepts(categoryId).mapCatching {  list ->
                list.find { it.id == conceptId } ?: throw Exception("Concept not found")
            }
    }
}
