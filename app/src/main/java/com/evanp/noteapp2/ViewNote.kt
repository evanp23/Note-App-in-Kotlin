package com.evanp.noteapp2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils

class ViewNote: AppCompatActivity() {
    lateinit var noteViewed: Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_note)

        //TOOLBAR
        val vToolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarView)
        setSupportActionBar(vToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //TEXTVIEWS
        var tvNoteTitle: TextView = findViewById(R.id.viewTitle)
        var tvNoteContent: TextView = findViewById(R.id.viewContent)

        //INTENT GETTING ID
        var noteID: Int
        var myIntent: Intent = getIntent()
        noteID = myIntent.getIntExtra("id", -1)

        //GETTING CLICKED NOTE BY ID
        var gotNote: Note = getNote(noteID.toInt())
        noteViewed = gotNote

        //MORE TOOLBAR HAD TO BE AFTER GETTING NOTE
        tvNoteTitle.text = gotNote.title
        tvNoteContent.text = gotNote.content
        supportActionBar?.title = gotNote.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun getNote(id: Int): Note{
        var note: Note
        var db = NoteDatabase(this)
        note = db.displayOneNote(id)

        return note
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_note_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.deleteNote->{
                var deletingNoteId: Int = noteViewed.id
                var deletingNoteTitle: String = noteViewed.title
                var db = NoteDatabase(this)
                db.deleteNote(deletingNoteId, deletingNoteTitle)
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            R.id.editNote->{
                var editingNoteId: Int = noteViewed.id
                var editingNoteTitle: String = noteViewed.title
                var editingNoteContent: String = noteViewed.content
                var intent = Intent(this, EditNote::class.java)
                intent.putExtra("id", editingNoteId)
                intent.putExtra("title", editingNoteTitle)
                intent.putExtra("content", editingNoteContent)

                startActivity(intent)

                return true
            }
            android.R.id.home ->{
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else-> super.onOptionsItemSelected(item)


        }
    }
}