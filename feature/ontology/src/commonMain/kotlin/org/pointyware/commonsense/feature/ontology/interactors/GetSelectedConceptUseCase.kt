package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.data.CategoryRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 *
 */
@OptIn(ExperimentalUuidApi::class)
class GetSelectedConceptUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Uuid, conceptId: Uuid): Result<Concept> {
        return categoryRepository.getConcepts(categoryId).mapCatching {  list ->
                list.find { it.id == conceptId } ?: throw Exception("Concept not found")
            }
    }
}
