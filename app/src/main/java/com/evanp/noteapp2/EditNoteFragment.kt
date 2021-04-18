package com.evanp.noteapp2

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditNoteFragment : Fragment(){
    var noteID: Int = 0
    var noteTitle: String = ""
    var noteContent: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteID = it.getInt("id")
            noteTitle = it.getString("title").toString()
            noteContent = it.getString("content").toString()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        val context: Context? = container?.context
        setHasOptionsMenu(true)

        var oldNoteTitle: EditText = view.findViewById(R.id.inputNoteTitle)
        var oldNoteDetails: EditText = view.findViewById(R.id.inputNoteDetails)

        oldNoteTitle.setText(noteTitle)
        oldNoteDetails.setText(noteContent)


        val mToolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbarAdd)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        (activity as AppCompatActivity).title = "Edit Note"
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

                var newNoteTitle: EditText = requireView().findViewById(R.id.inputNoteTitle)
                var newNoteDetails: EditText = requireView().findViewById(R.id.inputNoteDetails)

                updateNote(newNoteTitle.text.toString(), newNoteDetails.text.toString())

                //navigation component
                val action = EditNoteFragmentDirections.actionEditNoteFragmentToViewNoteFragment(noteID)
                view?.let { Navigation.findNavController(it).navigate(action) }

                true
            }
            android.R.id.home ->{
                val action = EditNoteFragmentDirections.actionEditNoteFragmentToViewNoteFragment(noteID)
                view?.let { Navigation.findNavController(it).navigate(action) }
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

        var note = Note(noteID, noteTitle, noteDetails, formattedDate, formattedTime)


        var db = NoteDatabase(requireContext())
        db.updateData(note)

    }
}