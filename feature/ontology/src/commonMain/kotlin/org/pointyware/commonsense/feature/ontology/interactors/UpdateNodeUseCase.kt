@file:OptIn(ExperimentalUuidApi::class)

package org.pointyware.commonsense.feature.ontology.interactors

import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
