package org.pointyware.commonsense.ontology.interactors

import org.pointyware.commonsense.ontology.Concept
import org.pointyware.commonsense.ontology.data.ArrangementController
import org.pointyware.commonsense.ontology.data.ConceptSpaceRepository

/**
 *
 */
class AddNewNodeUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository,
    private val arrangementController: ArrangementController
) {

    suspend operator fun invoke(x: Float, y: Float): Result<Concept> {
        return conceptSpaceRepository.createNode("New Node")
            .onSuccess { newNode ->
                arrangementController.addNode(newNode, x, y)
            }
    }
}
