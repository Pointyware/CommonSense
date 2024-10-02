package org.pointyware.commonsense.feature.ontology.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import org.pointyware.commonsense.core.common.Log
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 * Passes along the active concept space.
 */
class GetActiveConceptSpaceUseCase(
    private val repository: ConceptSpaceRepository
) {

    operator fun invoke(): Flow<ConceptSpace> =
        repository.activeSpace.onEach {
            Log.v("GetActiveConceptSpaceUseCase: $it")
        }
}
