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

class EditNote: AppCompatActivity() {
    var thisNoteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)

        var noteTitle: EditText = findViewById(R.id.inputNoteTitle)
        var noteDetails: EditText = findViewById(R.id.inputNoteDetails)
        val addToolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarAdd)
        setSupportActionBar(addToolbar)
        supportActionBar?.title = "Edit Note"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var myIntent: Intent = getIntent()
        var noteID = myIntent.getIntExtra("id", -1)
        var gotNoteTitle = myIntent.getStringExtra("title")
        var gotNoteContent = myIntent.getStringExtra("content")
        thisNoteId = noteID

        noteTitle.setText(gotNoteTitle)
        noteDetails.setText(gotNoteContent)


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

                updateNote(noteTitle.text.toString(), noteDetails.text.toString())


                intent = Intent(this, ViewNote::class.java)
                intent.putExtra("id", thisNoteId)
                intent.putExtra("title", noteTitle.text.toString())
                intent.putExtra("content", noteDetails.text.toString())

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
    private fun updateNote(noteTitle: String, noteDetails: String){

        var todaysDate = LocalDateTime.now()

        val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
        val dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy")

        val formattedTime = todaysDate.format(timeFormat)
        val formattedDate = todaysDate.format(dateFormat)

        var note = Note(thisNoteId, noteTitle, noteDetails, formattedDate, formattedTime)


        var db = NoteDatabase(this)
        db.updateData(note)

    }

}