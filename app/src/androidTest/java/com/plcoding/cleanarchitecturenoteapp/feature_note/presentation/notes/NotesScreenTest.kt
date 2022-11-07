package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plcoding.cleanarchitecturenoteapp.di.AppModule
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.MainActivity
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import com.plcoding.cleanarchitecturenoteapp.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            CleanArchitectureNoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    /*
       Getting android context in an instrumentation test
       val context = ApplicationProvider.getApplicationContext<android.content.Context>()
    */

    @Test
    fun clickToggleOrderSectionIsVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

    @Test
    fun clickToggleOrderWithTitleTest() {
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Title").performClick()
        composeRule.onNodeWithTag("Title").assertIsSelected()
    }

    @Test
    fun clickToggleOrderWithDateTest() {
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Date").performClick()
        composeRule.onNodeWithTag("Date").assertIsSelected()
    }

    @Test
    fun clickToggleOrderWithColorTest() {
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Color").performClick()
        composeRule.onNodeWithTag("Color").assertIsSelected()
    }

    @Test
    fun clickToggleOrderAscendingTest() {
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Ascending").performClick()
        composeRule.onNodeWithTag("Ascending").assertIsSelected()
    }

    @Test
    fun clickToggleOrderDescendingTest() {
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Descending").performClick()
        composeRule.onNodeWithTag("Descending").assertIsSelected()
    }


}