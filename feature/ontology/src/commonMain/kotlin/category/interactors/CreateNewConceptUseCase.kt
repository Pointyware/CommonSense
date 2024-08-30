package org.pointyware.commonsense.feature.ontology.category.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.IndependentConcept
import org.pointyware.commonsense.feature.ontology.category.data.CategoryRepository

/**
 *
 */
class CreateNewConceptUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend fun invoke(subject: Uuid, name: String, description: String): Result<Unit> {
        val newConcept = IndependentConcept(Uuid.v4(), name, description)
        return categoryRepository.addConcept(subject, newConcept)
    }
}
