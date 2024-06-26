package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 *
 */
class UpdateNodeUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository
) {
    suspend operator fun invoke(id: Uuid, newTitle: String) {
        conceptSpaceRepository.updateNode(
            id = id,
            title = newTitle,
        )
    }
}
