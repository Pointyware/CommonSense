package org.pointyware.commonsense.feature.ontology

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilDoesNotExist
import androidx.compose.ui.test.waitUntilExactlyOneExists
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.feature.ontology.category.ui.CategoryExplorerScreen
import org.pointyware.commonsense.feature.ontology.category.viewmodels.CategoryExplorerViewModel
import org.pointyware.commonsense.feature.ontology.test.assertEditableTextEquals
import org.pointyware.commonsense.feature.ontology.test.setupKoin
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class CategoryExplorerScreenUiTest {

    private lateinit var viewModel: CategoryExplorerViewModel

    @BeforeTest
    fun setUp() {
        setupKoin()
        val koin = getKoin()
        viewModel = koin.get()
    }

    @BeforeTest
    fun tearDown() {
        stopKoin()
    }

    private fun ComposeUiTest.contentUnderTest() {
        setContent {
            CategoryExplorerScreen(viewModel)
        }
    }

    @Test
    fun should_display_concepts() = runComposeUiTest {
        /*
        Given:
        - A concept space with concepts
         */
        // TODO: get test repository; add concepts

        /*
        When:
        - The screen is displayed
        Then:
        - The concepts are displayed // TODO
        - The
         */
        contentUnderTest()

        /*
        When:
        - A the user taps "Add Concept"
        Then:
        - The concept editor is displayed
        - And the selected template is "Concept"
        - And the concept editor fields are empty
        - And the "Save" button is disabled
         */
        onNodeWithText("New Concept").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Concept Editor"))
        onNodeWithText("Name")
            .assertEditableTextEquals("")
        onNodeWithText("Description")
            .assertEditableTextEquals("")
        onNodeWithText("Save")
            .assertIsNotEnabled()

        /*
        When:
        - The user enters a name and description
        Then:
        - The "Save" button is enabled
         */
        onNodeWithText("Name")
            .performTextInput("Concept Name")
        onNodeWithText("Description")
            .performTextInput("Concept Description")
        onNodeWithText("Save")
            .assertIsEnabled()

        /*
        When:
        - The user taps "Save"
        Then:
        - The concept editor is dismissed
         */
        onNodeWithText("Save").performClick()
        waitUntilDoesNotExist(hasContentDescription("Concept Editor"))

        // TODO: add test for new concept presence
    }
}
