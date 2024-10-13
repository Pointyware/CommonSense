package org.pointyware.commonsense.feature.ontology

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilAtLeastOneExists
import androidx.compose.ui.test.waitUntilDoesNotExist
import androidx.compose.ui.test.waitUntilExactlyOneExists
import kotlinx.coroutines.runBlocking
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.entities.Type
import org.pointyware.commonsense.core.entities.Value
import org.pointyware.commonsense.feature.ontology.data.RecordsDataSource
import org.pointyware.commonsense.feature.ontology.data.RecordsSqlDataSource
import org.pointyware.commonsense.feature.ontology.local.Persistence
import org.pointyware.commonsense.feature.ontology.test.assertEditableTextEquals
import org.pointyware.commonsense.feature.ontology.test.performLongPress
import org.pointyware.commonsense.feature.ontology.test.setupKoin
import org.pointyware.commonsense.feature.ontology.ui.CategoryExplorerScreen
import org.pointyware.commonsense.feature.ontology.viewmodels.CategoryExplorerViewModel
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi

/**
 *
 */
@OptIn(ExperimentalTestApi::class, ExperimentalUuidApi::class)
class CategoryExplorerScreenUiTest {

    private lateinit var viewModel: CategoryExplorerViewModel
    private lateinit var dataSource: RecordsDataSource

    private lateinit var zooRecord: Type.Record
    private lateinit var fooRecord: Type.Record

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(module {
            single<RecordsDataSource> { RecordsSqlDataSource(get(), persistence = Persistence.InMemory) }
        })
        val koin = getKoin()
        viewModel = koin.get()
        dataSource = koin.get()
        runBlocking {
            val zooRecord: Type.Record = dataSource.createRecord("Zoo").getOrThrow()
            dataSource.addField(zooRecord, "kar", Type.Int, Value.IntValue(36))
            val fooRecord: Type.Record = dataSource.createRecord("Foo").getOrThrow()
            dataSource.addField(fooRecord, "bar", Type.Boolean, Value.BoolValue(false))

            dataSource.addField(zooRecord, "kaz", fooRecord, null)
            dataSource.addField(fooRecord, "baz", zooRecord, null)

            val zooInstance: Value.Instance = dataSource.createInstance(zooRecord).getOrThrow()
            val fooInstance: Value.Instance = dataSource.createInstance(fooRecord).getOrThrow()
            dataSource.setAttribute(zooInstance, "kar", Value.IntValue(42))
            dataSource.setAttribute(fooInstance, "bar", Value.BoolValue(true))
            dataSource.setAttribute(zooInstance, "kaz", fooInstance)
            dataSource.setAttribute(fooInstance, "baz", zooInstance)

            this@CategoryExplorerScreenUiTest.fooRecord = dataSource.getRecord(fooRecord.uuid).getOrThrow()
            this@CategoryExplorerScreenUiTest.zooRecord = dataSource.getRecord(zooRecord.uuid).getOrThrow()
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
    fun tapping_add_instance_should_display_instance_editor() = runComposeUiTest {
        /*
        Given:
        - A concept space with instances
         */

        /*
        When:
        - The screen is displayed
        Then:
        - The category is displayed
         */
        contentUnderTest()

        /*
        When:
        - A the user taps "Add Instance"
        Then:
        - The instance editor is displayed
        - And the selected template is "Note"
        - And the fields are empty
        - And the "Save" button is disabled
         */
        onNodeWithText("New Concept").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Instance Editor"))
        onNodeWithText("Title")
            .assertEditableTextEquals("")
        onNodeWithText("Body")
            .assertEditableTextEquals("")
        onNodeWithText("Save")
            .assertIsNotEnabled()

        /*
        When:
        - The user enters a name and description
        Then:
        - The "Save" button is enabled
         */
        onNodeWithText("Title")
            .performTextInput("Note Title")
        onNodeWithText("Body")
            .performTextInput("Note Body")
        onNodeWithText("Save")
            .assertIsEnabled()

        /*
        When:
        - The user taps "Save"
        Then:
        - The concept editor is dismissed
         */
        onNodeWithText("Save").performClick()
        waitUntilDoesNotExist(hasContentDescription("Instance Editor"))

        // TODO: add test for new concept presence
    }

    /**
     * User Journey: Create New Concept and Cancel
     */
    @Test
    fun tapping_add_instance_should_display_concept_editor_and_cancel() = runComposeUiTest {
            /*
            Given:
            - A concept space
             */
            contentUnderTest()

            /*
            When:
            - The user taps "New Instance"
            Then:
            - The instance editor is displayed
            - And the "Save" button is disabled
             */
            onNodeWithText("New Instance").performClick()

            waitUntilExactlyOneExists(hasContentDescription("Instance Editor"))
//            onNodeWithText("Name") // TODO: replace with record property checks
//                .assertEditableTextEquals("")
//            onNodeWithText("Description")
//                .assertEditableTextEquals("")
            onNodeWithText("Save")
                .assertIsNotEnabled()

            /*
            When:
            - The user enters field value
            Then:
            - The "Save" button is enabled
             */
//            onNodeWithText("Name") // TODO: replace with record property checks
//                .performTextInput("Concept Name")
//            onNodeWithText("Description")
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
    @Test
    fun long_pressing_instance_should_select_then_cancel() = runComposeUiTest {
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
        onNodeWithText("Concept 1").performLongPress()

        waitUntilAtLeastOneExists(hasContentDescription("Select"))
        onNodeWithText("Concept 1").assert(
            hasAnySibling(
                hasContentDescription("Deselect"))
        )

        /*
        When:
        - The "Cancel" button is pressed
        Then:
        - The selection state is removed
         */
        onNodeWithText("Cancel").performClick()

        onAllNodes(hasContentDescription("Concept", substring = true))
            .assertAll(hasAnyDescendant(
                hasContentDescription("Select").or(
                    hasContentDescription("Deselect"))
            ).not())
    }

    /**
     * User Journey: Select and Delete Concepts - Selection, Delete, Cancel
     */
    @Test
    fun long_pressing_instance_should_select_then_delete_cancel() = runComposeUiTest {
        /*
        Given:
        - a category is shown in the explorer with at least one concept
         */
        contentUnderTest()

        /*
        When:
        - When a concept is long-pressed (mobile) or CMD+clicked (desktop/web)
        - The "Delete" menu item is tapped
        Then:
        - A confirmation dialog is shown with the number of concepts to be deleted
         */
        onNodeWithText("Concept 1").performLongPress()
        onNodeWithText("Delete").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Delete Concepts"))
        onNodeWithContentDescription("Delete Concepts")
            .assert(hasAnyDescendant(hasText("You are about to delete 1 concepts and 0 categories.")))

        /*
        When:
        - The "Cancel" button is pressed
        Then:
        - The dialog is hidden
        - And the selection state is removed
         */
        onNodeWithContentDescription("Delete Concepts")
            .onChildren().filterToOne(hasText("Cancel"))
            .performClick()

        waitUntilDoesNotExist(hasContentDescription("Delete Concepts"))

        onAllNodes(hasContentDescription("Concept", substring = true))
            .assertAll(hasAnyDescendant(
                hasContentDescription("Select").or(
                    hasContentDescription("Deselect"))
            ).not())
    }

    /**
     * User Journey: Select and Delete Concepts - Selection, Delete, Confirm
     */
    @Test
    fun long_pressing_instance_should_select_then_delete_confirm() = runComposeUiTest {
        /*
        Given:
        - a category is shown in the explorer with at least one concept
         */
        contentUnderTest()

        /*
        When:
        - When a concept is long-pressed (mobile) or CMD+clicked (desktop/web)
        - The "Delete" menu item is tapped
        Then:
        - A confirmation dialog is shown with the number of concepts to be deleted
         */
        onNodeWithText("Note 1").performLongPress()
        onNodeWithText("Delete").performClick()

        waitUntilExactlyOneExists(hasContentDescription("Delete Instances"))
        onNodeWithContentDescription("Delete Instances")
            .assert(hasAnyDescendant(hasText("You are about to delete 1 instances and 0 categories.")))

        /*
        When:
        - The "Confirm" button is pressed
        Then:
        - The dialog is hidden
        - And the selected concepts are deleted
        - And the selection state is removed
         */
        onNodeWithText("Confirm").performClick()

        waitUntilDoesNotExist(hasContentDescription("Delete Instances"))
        onNodeWithText("Concept 1").assertDoesNotExist()
        onAllNodes(hasContentDescription("Instance", substring = true))
            .assertAll(hasAnyDescendant(
                hasContentDescription("Select").or(
                    hasContentDescription("Deselect"))
            ).not())
    }
}
