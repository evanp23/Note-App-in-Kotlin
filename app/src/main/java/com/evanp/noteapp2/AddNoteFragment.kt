package com.evanp.noteapp2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.navigation.Navigation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



/**
 * A simple [Fragment] subclass.
 * Use the [addNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNoteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        val context: Context? = container?.context
        setHasOptionsMenu(true)

        var noteTitle: EditText = view.findViewById(R.id.inputNoteTitle)
        var noteDetails: EditText = view.findViewById(R.id.inputNoteDetails)

        val mToolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbarAdd)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        (activity as AppCompatActivity).title = "Add Note"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_activity_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.clickToSave -> {

                var noteTitle: EditText? = view?.findViewById(R.id.inputNoteTitle)
                var noteDetails: EditText? = view?.findViewById(R.id.inputNoteDetails)

                addNote(noteTitle?.text.toString(), noteDetails?.text.toString())


                view?.let { Navigation.findNavController(it).navigate(R.id.action_addNoteFragment_to_mainFragment) }
                true
            }
            android.R.id.home ->{

                view?.let { Navigation.findNavController(it).navigate(R.id.action_addNoteFragment_to_mainFragment) }
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

        var db = NoteDatabase(context as Activity)
        db.insertData(note)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addNoteFragment.
         */

    }
}