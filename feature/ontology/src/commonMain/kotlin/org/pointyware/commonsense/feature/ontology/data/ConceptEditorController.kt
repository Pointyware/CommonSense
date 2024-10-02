package org.pointyware.commonsense.feature.ontology.data

import org.pointyware.commonsense.feature.ontology.Category

/**
 *
 */
interface ConceptEditorController {
    var subject: Category?
}

class ConceptEditorControllerImpl: ConceptEditorController {
    override var subject: Category? = null
}
