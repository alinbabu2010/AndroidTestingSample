package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.plcoding.cleanarchitecturenoteapp.di.AppModule
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import com.plcoding.cleanarchitecturenoteapp.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            CleanArchitectureNoteAppTheme {
                NoteNavHost()
            }
        }
    }

    @Test
    fun saveNoteEditAfterwardsTest() {

        // Click FAB to add new note
        composeRule.onNodeWithContentDescription("Add note").performClick()

        // Enter title and content text fields inputs
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("Title test")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("Content test")

        // Save note
        composeRule.onNodeWithContentDescription("Save note").performClick()

        // Checking note is present in list and perform click
        composeRule.onNodeWithText("Title test")
            .assertIsDisplayed()
            .performClick()

        // Check the note title and content with our inputs
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("Title test")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("Content test")

        // Edit note title and save
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextReplacement("Title test 1")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        // Check edited note is displayed in the list
        composeRule.onNodeWithText("Title test 1").assertIsDisplayed()

    }

    @Test
    fun saveNotesAndOrderByTitleDescending() {

        for (i in 1..3) {

            // Click FAB to add new note
            composeRule.onNodeWithContentDescription("Add note").performClick()

            // Enter title and content text fields inputs
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput("Title test $i")
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("Content test $i")

            // Save note
            composeRule.onNodeWithContentDescription("Save note").performClick()

        }

        repeat(3) {
            composeRule.onNodeWithText("Title test ${it + 1}")
                .assertIsDisplayed()
        }

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Title").performClick()
        composeRule.onNodeWithTag("Descending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("Title test 3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("Title test 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("Title test 1")

    }

    @Test
    fun saveNotesAndOrderByTitleAscending() {

        for (i in 1..3) {

            // Click FAB to add new note
            composeRule.onNodeWithContentDescription("Add note").performClick()

            // Enter title and content text fields inputs
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput("Title test $i")
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput("Content test $i")

            // Save note
            composeRule.onNodeWithContentDescription("Save note").performClick()

        }

        repeat(3) {
            composeRule.onNodeWithText("Title test ${it + 1}")
                .assertIsDisplayed()
        }

        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("Title").performClick()
        composeRule.onNodeWithTag("Ascending").performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("Title test 1")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("Title test 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("Title test 3")

    }

}