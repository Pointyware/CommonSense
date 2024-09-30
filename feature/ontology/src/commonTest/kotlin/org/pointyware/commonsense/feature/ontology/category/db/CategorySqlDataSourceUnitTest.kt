package org.pointyware.commonsense.feature.ontology.category.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.category.data.CategorySqlDataSource
import org.pointyware.commonsense.feature.ontology.test.setupKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CategorySqlDataSourceUnitTest {

    private lateinit var dataSource: CategorySqlDataSource

    @BeforeTest
    fun setUp() {
        setupKoin()
        loadKoinModules(module {
            single<CategorySqlDataSource> { CategorySqlDataSource(get(), inMemory = true) }
        })
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        val koin = getKoin()
        dataSource = koin.get()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    //suspend fun createCategory(name: String): Result<Category>
    @Test
    fun insertCategoryAtRoot() = runTest {
        /*
        Given:
        - A category with the name "test"
         */
        val categoryName = "test"

        /*
        When:
        - The category is inserted into the database
        Then:
        - The result is success
        - The uuid of the category is not null
         */
        val result = dataSource.createCategory("test")

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull()?.id)
        assertNotEquals(Uuid.nil(), result.getOrThrow().id)
    }

    //suspend fun addCategory(subject: Uuid, name: String): Result<Category>
    @Test
    fun insertCategoryUnderCategory() = runTest {
        /*
        Given:
        - A category with the name "alpha"
        - A parent category with the name "parent"
         */
        val parentCategoryName = "alpha"
        val parentCategory = dataSource.createCategory(parentCategoryName).getOrThrow()
        val categoryName = "test"

        /*
        When:
        - The category is inserted into the database with the parent category
        Then:
        - The result is success
        - The uuid of the category is not null
         */
        val result = dataSource.addCategory(parentCategory.id, categoryName)

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull()?.id)
        assertNotEquals(Uuid.nil(), result.getOrThrow().id)
    }

    //suspend fun getCategory(id: Uuid): Result<Category>
    @Test
    fun getCategory() = runTest {
        /*
        Given:
        - A category with the name "alpha"
         */
        val categoryName = "alpha"
        val category = dataSource.createCategory(categoryName).getOrThrow()

        /*
        When:
        - The category is retrieved from the database
        Then:
        - The result is success
        - The uuid of the category is not null
         */
        val result = dataSource.getCategory(category.id)

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull()?.id)
        assertNotEquals(Uuid.nil(), result.getOrThrow().id)
        assertEquals(categoryName, result.getOrThrow().name)
        assertEquals(category.id, result.getOrThrow().id)
    }

    //suspend fun getSubcategories(id: Uuid): Result<List<Category>>
    @Test
    fun getSubcategories() = runTest {
        /*
        Given:
        - A category with the name "alpha"
        - A parent category with the name "parent"
         */
        val parentCategoryName = "alpha"
        val parentCategory = dataSource.createCategory(parentCategoryName).getOrThrow()
        val categoryName = "test"
        val category = dataSource.addCategory(parentCategory.id, categoryName).getOrThrow()
        val categoryName2 = "test2"
        val category2 = dataSource.addCategory(parentCategory.id, categoryName2).getOrThrow()

        /*
        When:
        - The category is inserted into the database with the parent category
        Then:
        - The result is success
        - The uuid of the category is not null
         */
        val result = dataSource.getSubcategories(parentCategory.id)

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
        assertTrue(result.getOrThrow().isNotEmpty())
        assertEquals(2, result.getOrThrow().size)
        assertTrue(result.getOrThrow().contains(category))
        assertTrue(result.getOrThrow().contains(category2))
    }

    //suspend fun addConcept(subject: Uuid, name: String, description: String): Result<Concept>
    @Test
    fun insertConceptUnderCategory() = runTest {
        /*
        Given:
        - A category with the name "alpha"
        - A parent category with the name "parent"
         */
        val parentCategoryName = "alpha"
        val parentCategory = dataSource.createCategory(parentCategoryName).getOrThrow()
        val conceptName = "test"
        val conceptDescription = "description"

        /*
        When:
        - The concept is inserted into the database with the parent category
        Then:
        - The result is success
        - The uuid of the concept is not null
         */
        val result = dataSource.addConcept(parentCategory.id, conceptName, conceptDescription)

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull()?.id)
        assertNotEquals(Uuid.nil(), result.getOrThrow().id)
    }

    //suspend fun getConcepts(id: Uuid): Result<List<Concept>>
    @Test
    fun getConceptsUnderCategory() = runTest {
        /*
        Given:
        - A category with the name "alpha"
        - A parent category with the name "parent"
         */
        val parentCategoryName = "alpha"
        val parentCategory = dataSource.createCategory(parentCategoryName).getOrThrow()
        val conceptName = "test"
        val conceptDescription = "description"
        val concept = dataSource.addConcept(parentCategory.id, conceptName, conceptDescription).getOrThrow()
        val concept2 = dataSource.addConcept(parentCategory.id, "test2", "description2").getOrThrow()

        /*
        When:
        - The category is inserted into the database with the parent category
        Then:
        - The result is success
        - The uuid of the category is not null
         */
        val result = dataSource.getConcepts(parentCategory.id)

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
        assertTrue(result.getOrThrow().isNotEmpty())
        assertEquals(2, result.getOrThrow().size)
        assertTrue(result.getOrThrow().contains(concept))
        assertTrue(result.getOrThrow().contains(concept2))
    }
}
