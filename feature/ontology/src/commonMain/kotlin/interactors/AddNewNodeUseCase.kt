package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.Concept
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

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
