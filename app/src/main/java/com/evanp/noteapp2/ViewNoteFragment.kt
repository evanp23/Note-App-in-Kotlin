package com.evanp.noteapp2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class ViewNoteFragment : Fragment() {
    val VIEW_TAG: String = "ViewNote"
    lateinit var noteViewed: Note
    private var noteID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteID = it.getInt("id")
            Log.d(VIEW_TAG, "onCreate: Note ID passed to ViewNote as: $noteID")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_note, container, false)
        setHasOptionsMenu(true)

        //TEXTVIEWS
        var tvNoteTitle: TextView = view.findViewById(R.id.viewTitle)
        var tvNoteContent: TextView = view.findViewById(R.id.viewContent)

        val context: Context? = container?.context

        //TOOLBAR
        val mToolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbarView)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        (activity as AppCompatActivity).title = "View Note"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)


        //GETTING CLICKED NOTE BY ID
        var gotNote: Note = getNote(noteID)
        noteViewed = gotNote

        //MORE TOOLBAR HAD TO BE AFTER GETTING NOTE
        tvNoteTitle.text = gotNote.title
        tvNoteContent.text = gotNote.content

        return view
    }

    private fun getNote(id: Int): Note{
        Log.d(VIEW_TAG, "getNote: Note ID passed to getNote as: $id")
        lateinit var note: Note
        var db = NoteDatabase(requireContext())
        if (db != null) {
            note = db.displayOneNote(id)
        }

        return note
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_note_activity_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.deleteNote->{
                var deletingNoteId: Int = noteViewed.id
                var deletingNoteTitle: String = noteViewed.title
                var db = NoteDatabase(requireContext())
                db.deleteNote(deletingNoteId, deletingNoteTitle)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_viewNoteFragment_to_mainFragment) }
                true
            }
            R.id.editNote->{
                var editingNoteId: Int = noteViewed.id
                var editingNoteTitle: String = noteViewed.title
                var editingNoteContent: String = noteViewed.content

                val action = ViewNoteFragmentDirections.actionViewNoteFragmentToEditNoteFragment(editingNoteId, editingNoteTitle, editingNoteContent)
                view?.let { Navigation.findNavController(it).navigate(action) }

//                intent.putExtra("id", editingNoteId)
//                intent.putExtra("title", editingNoteTitle)
//                intent.putExtra("content", editingNoteContent)
//
//                startActivity(intent)
//
                return true
            }
            android.R.id.home ->{
                view?.let { Navigation.findNavController(it).navigate(R.id.action_viewNoteFragment_to_mainFragment) }
                return true
            }
            else-> super.onOptionsItemSelected(item)


        }
    }

    companion object {

    }
}