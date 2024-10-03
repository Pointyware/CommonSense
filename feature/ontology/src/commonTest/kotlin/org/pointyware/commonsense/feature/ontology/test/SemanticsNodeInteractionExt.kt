package org.pointyware.commonsense.feature.ontology.test

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher.Companion.expectValue
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.text.AnnotatedString

/**
 *
 */
fun SemanticsNodeInteraction.assertEditableTextEquals(expected: String) {
    assert(expectValue(SemanticsProperties.EditableText, AnnotatedString(expected)))
}

fun SemanticsNodeInteraction.performLongPress() =
    performTouchInput {
        down(center)
        move(1000)
        up()
    }
