package org.pointyware.commonsense.feature.ontology.category.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.stopKoin
import org.koin.mp.KoinPlatform.getKoin
import org.pointyware.commonsense.core.common.Uuid
import org.pointyware.commonsense.feature.ontology.category.data.CategorySqlDataSource
import org.pointyware.commonsense.feature.ontology.test.setupKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CategorySqlDataSourceUnitTest {

    private lateinit var dataSource: CategorySqlDataSource

    @BeforeTest
    fun setUp() {
        setupKoin()
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
}
