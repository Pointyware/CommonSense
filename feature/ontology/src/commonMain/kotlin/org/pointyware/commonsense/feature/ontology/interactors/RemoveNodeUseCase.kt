package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 * Removes a node from the concept space and stops tracking it in the arrangement controller.
 */
class RemoveNodeUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository,
    private val arrangementController: ArrangementController
) {
    suspend operator fun invoke(id: Uuid): Result<Unit> {
        return conceptSpaceRepository.removeNode(id).onSuccess {
            arrangementController.removeNode(id)
        }
    }
}
