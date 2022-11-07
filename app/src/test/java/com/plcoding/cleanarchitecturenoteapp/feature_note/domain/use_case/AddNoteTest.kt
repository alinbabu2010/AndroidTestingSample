package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var note: Note
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        note = Note(
            title = "",
            content = "",
            timestamp = 1532L,
            color = 152
        )
        fakeNoteRepository = FakeNoteRepository()
    }

    @Test(expected = InvalidNoteException::class)
    fun titleAndContentExceptionTest() {

        if (note.title.isBlank())
            throw InvalidNoteException("The title of the note can't be empty.")

        if (note.content.isBlank())
            throw InvalidNoteException("The content of the note can't be empty.")

    }

    @Test
    fun insertNoteTest() {
        runBlocking {
            fakeNoteRepository.insertNote(note)
            assertTrue(fakeNoteRepository.getNotes().first().contains(note))
        }
    }


}