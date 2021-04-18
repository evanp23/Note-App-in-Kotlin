package com.evanp.noteapp2

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main_act, container, false)
        val context: Context? = container?.context

        val mToolbar: androidx.appcompat.widget.Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        (activity as AppCompatActivity).title = "My Notes"
        setHasOptionsMenu(true)

        var db: NoteDatabase? = context?.let { NoteDatabase(it) }
        var allNotes: ArrayList<Note> = ArrayList<Note>(db?.displayAllNotes())
        var noItemText: TextView = view.findViewById(R.id.noItemText)

        if(allNotes.isEmpty()){
            noItemText.visibility = View.VISIBLE;
        }else {
            noItemText.visibility = View.GONE;
            var mRecyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

            mRecyclerView.layoutManager = LinearLayoutManager(context)
            mRecyclerView.adapter = context?.let { MainAdapter(it, allNotes) }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.card_view_activity_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.clickToAdd -> {
                view?.let { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_addNoteFragment) }
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mainFragment.
         */

    }
}