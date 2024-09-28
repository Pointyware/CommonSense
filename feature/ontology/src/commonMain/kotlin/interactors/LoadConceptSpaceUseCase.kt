package org.pointyware.commonsense.feature.ontology.interactors

import kotlinx.io.files.Path
import org.pointyware.commonsense.feature.ontology.ConceptSpace
import org.pointyware.commonsense.feature.ontology.data.ConceptSpaceRepository

/**
 *
 */
class LoadConceptSpaceUseCase(
    private val conceptSpaceRepository: ConceptSpaceRepository
) {

    suspend operator fun invoke(file: Path): Result<ConceptSpace> {
        return conceptSpaceRepository.loadConceptSpace(file)
    }
}
