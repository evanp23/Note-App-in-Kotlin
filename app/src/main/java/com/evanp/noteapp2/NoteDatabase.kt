package com.evanp.noteapp2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

val DATABASE_NAME = "NoteDB"
val TABLE_NAME = "Notes"
val COL_ID = "id"
val COL_TITLE = "title"
val COL_CONTENT = "content"
val COL_DATE = "date"
val COL_TIME = "time"
val DB_TAG = "NoteDatabase"

class NoteDatabase (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2){
    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery : String = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " VARCHAR(256), " +
                COL_CONTENT + " VARCHAR(5000), " +
                COL_DATE + " TINYTEXT, " +
                COL_TIME + " TNYTEXT)"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(note: Note){
        val db: SQLiteDatabase = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, note.title)
        cv.put(COL_CONTENT, note.content)
        cv.put(COL_DATE, note.date)
        cv.put(COL_TIME, note.time)

        var result = db.insert(TABLE_NAME, null, cv)
        if(result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }


    }

    fun displayOneNote(noteId: Int): Note{

        val db: SQLiteDatabase = this.readableDatabase
        val oneNoteQuery: String = "SELECT * FROM $TABLE_NAME WHERE $COL_ID = $noteId"


        var cursor = db.rawQuery(oneNoteQuery, null)
        var noteID: Int = 0
        var noteTitle: String = "error"
        var noteContent: String = ""
        var noteDate: String = ""
        var noteTime: String = ""

        if(cursor.moveToFirst()){
            do {
                Log.d(DB_TAG, "displayOneNote: Got into database")
                noteID = cursor.getInt(0)
                noteTitle = cursor.getString(1)
                noteContent = cursor.getString(2)
                noteDate = cursor.getString(3)
                noteTime = cursor.getString(4)

                Log.d(DB_TAG, "displayOneNote: found note with title: " + noteTitle)


            }while(cursor.moveToNext())

        }

        return Note(noteID, noteTitle, noteContent, noteDate, noteTime)

    }

    fun displayAllNotes(): ArrayList<Note>{
        var noteList: ArrayList<Note> = ArrayList()
        val db: SQLiteDatabase = this.readableDatabase
        val getQuery: String = "SELECT * FROM $TABLE_NAME"

        val cursor = db.rawQuery(getQuery, null)

        if(cursor.moveToFirst()){
            do{
                var noteID: Int = cursor.getInt(0)
                var noteTitle: String = cursor.getString(1)
                var noteContent: String = cursor.getString(2)
                var noteDate: String = cursor.getString(3)
                var noteTime: String = cursor.getString(4)

                var note = Note(noteID, noteTitle, noteContent, noteDate, noteTime)

                Log.d(DB_TAG, "displayAllNotes: found note with title: $noteTitle", null)

                noteList.add(note)

            }while(cursor.moveToNext())
        }

        return noteList;
    }

    fun deleteNote(noteId: Int, noteTitle: String){
        val deleteQuery: String = "DELETE FROM $TABLE_NAME WHERE $COL_ID = $noteId"
        var db: SQLiteDatabase = this.writableDatabase

        val cursor = db.rawQuery(deleteQuery, null)
        Toast.makeText(this.context, "Note with title: $noteTitle deleted.", Toast.LENGTH_SHORT).show()

        if(cursor.moveToFirst()){
                Toast.makeText(this.context, "Note with title: $noteTitle deleted.", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateData(note: Note){
//        var noteId: Int = note.id
//        var newTitle: String = note.title
//        var newContent: String = note.content
//        var newDate: String = note.date
//        var newTime: String = note.time
        val db: SQLiteDatabase = this.writableDatabase
        val noteId: Int = note.id
        val noteTitle: String = note.title
        var cv = ContentValues()
        cv.put(COL_TITLE, note.title)
        cv.put(COL_CONTENT, note.content)
        cv.put(COL_DATE, note.date)
        cv.put(COL_TIME, note.time)

        db.update(TABLE_NAME, cv, "id = ?", arrayOf(noteId.toString()))


//        //val updateQuery: String = "UPDATE $TABLE_NAME SET $COL_TITLE = $newTitle, " +
//          //      "$COL_CONTENT = $newContent, $COL_DATE = $newDate, $COL_TIME = $newTime, " +
//            //    "WHERE $COL_ID = $noteId"
//
//        db.execSQL(updateQuery)
        Toast.makeText(this.context, "Note with title: $noteTitle updated.", Toast.LENGTH_SHORT).show()


    }
}
