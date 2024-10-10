package org.pointyware.commonsense.feature.ontology.viewmodels

import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.core.viewmodels.ViewModel

/**
 *
 * @see ConceptEditorViewModel
 */
interface ClassEditorViewModel {

    fun onFieldNameChange(newName: String)

    fun onFieldDefaultValueChange(newValue: Value<*>)

    fun onFieldTypeChange(newType: Type)

}


class ClassEditorViewModelImpl(

): ViewModel(), ClassEditorViewModel {

    override fun onFieldNameChange(newName: String) {
        TODO("Not yet implemented")
    }

    override fun onFieldTypeChange(newType: Type) {
        TODO("Not yet implemented")
    }

    override fun onFieldDefaultValueChange(newValue: Value<*>) {
        TODO("Not yet implemented")
    }
}
