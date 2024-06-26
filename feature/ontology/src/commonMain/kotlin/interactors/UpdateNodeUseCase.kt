package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 *
 */
class UpdateNodeUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository
) {
    suspend operator fun invoke(newTitle: String) {
        conceptSpaceRepository.updateNode(
            title = newTitle,
        )
    }
}
