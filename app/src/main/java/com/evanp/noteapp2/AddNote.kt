package com.evanp.noteapp2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddNote: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)

        var noteTitle: EditText = findViewById(R.id.inputNoteTitle)
        var noteDetails: EditText = findViewById(R.id.inputNoteDetails)
        val addToolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarAdd)
        setSupportActionBar(addToolbar)
        supportActionBar?.title = "New Note"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.clickToSave -> {

                var noteTitle: EditText = findViewById(R.id.inputNoteTitle)
                var noteDetails: EditText = findViewById(R.id.inputNoteDetails)

                addNote(noteTitle.text.toString(), noteDetails.text.toString())


                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            android.R.id.home ->{
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addNote(noteTitle: String, noteDetails: String){

        var todaysDate = LocalDateTime.now()

        val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
        val dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy")

        val formattedTime = todaysDate.format(timeFormat)
        val formattedDate = todaysDate.format(dateFormat)

        var note = Note(noteTitle, noteDetails, formattedDate, formattedTime)

        var db = NoteDatabase(this)
        db.insertData(note)

    }

}