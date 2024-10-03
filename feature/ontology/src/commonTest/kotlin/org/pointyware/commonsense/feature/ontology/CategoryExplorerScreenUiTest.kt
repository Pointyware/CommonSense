package org.pointyware.commonsense.feature.ontology

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilDoesNotExist
import androidx.compose.ui.test.waitUntilExactlyOneExists
import kotlinx.coroutines.runBlocking
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.data.CategoryDataSource
import org.pointyware.commonsense.feature.ontology.data.CategorySqlDataSource
import org.pointyware.commonsense.feature.ontology.local.Persistence
import org.pointyware.commonsense.feature.ontology.ui.CategoryExplorerScreen
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerViewModel
import org.pointyware.commonsense.feature.ontology.test.assertEditableTextEquals
import org.pointyware.commonsense.feature.ontology.test.performLongPress
import org.pointyware.commonsense.feature.ontology.test.setupKoin
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 *
 */
@OptIn(ExperimentalTestApi::class)
class CategoryExplorerScreenUiTest {

    private lateinit var viewModel: CategoryExplorerViewModel
    private lateinit var dataSource: CategoryDataSource

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(module {
            single<CategoryDataSource> { CategorySqlDataSource(get(), persistence = Persistence.InMemory)}
        })
        val koin = getKoin()
        viewModel = koin.get()
        dataSource = koin.get()
        runBlocking {
            dataSource.addConcept(Uuid.nil(), "Concept 1", "Description 1")
            dataSource.addConcept(Uuid.nil(), "Concept 2", "Another Description")
        }
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
    fun tapping_add_concept_should_display_concept_editor() = runComposeUiTest {
        /*
        Given:
        - A concept space with concepts
         */

        /*
        When:
        - The screen is displayed
        Then:
        - The concepts are displayed
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

    /**
     * User Journey: Create New Concept and Cancel
     */
    @Test
    fun tapping_add_concept_should_display_concept_editor_and_cancel() = runComposeUiTest {
            /*
            Given:
            - A concept space
             */
            contentUnderTest()

            /*
            When:
            - The user taps "New Concept"
            Then:
            - The concept editor is displayed
            - And the name and description fields are empty
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
            - The user taps "Cancel"
            Then:
            - The concept editor is dismissed
             */
            onNodeWithText("Cancel").performClick()
            waitUntilDoesNotExist(hasContentDescription("Concept Editor"))

            // TODO: add test for new concept absence
        }

    /**
     * User Journey: Create New Category and Confirm
     */
    @Test
    fun tapping_add_category_should_display_category_editor_and_confirm() = runComposeUiTest {
        /*
        Given:
        - A concept space
         */
        contentUnderTest()

        /*
        When:
        - The user taps "New Category"
        Then:
        - The category editor is displayed
        - And the name field is empty
        - And the "Save" button is disabled
         */
        onNodeWithText("New Category").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Category Editor"))

        /*
        When:
        - The user enters a name
        Then:
        - The "Save" button is enabled
         */
        onNodeWithText("Name")
            .performTextInput("Category Name")
        onNodeWithText("Save")
            .assertIsEnabled()

        /*
        When:
        - The user taps "Save"
        Then:
        - The category editor is dismissed
         */
        onNodeWithText("Save").performClick()
        waitUntilDoesNotExist(hasContentDescription("Category Editor"))

        // TODO: add test for new category presence
    }

    /**
     * User Journey: Create New Category and Cancel
     */
    @Test
    fun tapping_add_category_should_display_category_editor_and_cancel() = runComposeUiTest {
        /*
        Given:
        - A concept space
         */
        contentUnderTest()

        /*
        When:
        - The user taps "New Category"
        Then:
        - The category editor is displayed
        - And the name field is empty
        - And the "Save" button is disabled
         */
        onNodeWithText("New Category").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Category Editor"))

        /*
        When:
        - The user enters a name
        Then:
        - The "Save" button is enabled
         */
        onNodeWithText("Name")
            .performTextInput("Category Name")
        onNodeWithText("Save")
            .assertIsEnabled()

        /*
        When:
        - The user taps "Cancel"
        Then:
        - The category editor is dismissed
         */
        onNodeWithText("Cancel").performClick()
        waitUntilDoesNotExist(hasContentDescription("Category Editor"))
        // TODO: add test for new category absence
    }

    /**
     * User Journey: Select and Delete Concepts - Selection, Cancel
     */
    fun long_pressing_concept_should_select_then_cancel() = runComposeUiTest {
        /*
        Given:
        - a category is shown in the explorer with at least one concept
         */
        contentUnderTest()

        /*
        When:
        - When a concept is long-pressed (mobile) or CMD+clicked (desktop/web)
        Then:
        - Checkboxes are shown next to all concepts
        - And the long-pressed concept is selected
        - And the "Delete" and "Cancel" buttons are shown
         */
        onNodeWithText("Concept 1").performLongPress(mainClock)

        onAllNodes(hasContentDescription("Concept", substring = true))
            .assertAll(hasAnyDescendant(
                hasContentDescription("Select").or(
                    hasContentDescription("Deselect"))
            ))

        /*
        When:
        - The "Delete" menu item is tapped
        Then:
        - A confirmation dialog is shown with the number of concepts to be deleted
         */
        onNodeWithText("Delete").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Delete Concepts"))
        onNodeWithContentDescription("Delete Concepts")
            .assert(hasAnyDescendant(hasText("1 concept will be deleted")))

        /*
        When:
        - The "Cancel" button is pressed
        Then:
        - The dialog is hidden
        - And the selection state is removed
         */
        onNodeWithText("Cancel").performClick()

        waitUntilDoesNotExist(hasContentDescription("Delete Concepts"))

        onAllNodes(hasContentDescription("Concept", substring = true))
            .assertAll(hasAnyDescendant(
                hasContentDescription("Select").or(
                    hasContentDescription("Deselect"))
            ).not())
        /*
        When:
        - The "Confirm" button is pressed
        Then:
        - The dialog is hidden
        - And the selected concepts are deleted
        - And the selection state is removed
         */

        /*
        When:
        - The "Cancel" button is pressed
        Then:
        - The selection state is removed
         */

    }

    /**
     * User Journey: Select and Delete Concepts - Selection, Delete, Cancel
     */

    /**
     * User Journey: Select and Delete Concepts - Selection, Delete, Confirm
     */
}
