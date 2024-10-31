package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.data.ArrangementController
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 *
 */
class AddNewNodeUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository,
    private val arrangementController: ArrangementController
) {

    suspend operator fun invoke(x: Float, y: Float, instance: Value.Instance): Result<Unit> {
        return conceptSpaceRepository.addNode(instance)
            .onSuccess {
                arrangementController.addNode(instance, x, y)
            }
    }
}
