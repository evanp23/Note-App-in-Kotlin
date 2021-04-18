package com.evanp.noteapp2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if(savedInstanceState == null){
//            val fragment = MainFragment()
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.mainFragment, fragment)
//                .commit()
//        }



//        var db: NoteDatabase = NoteDatabase(this)
//        var allNotes: ArrayList<Note> = ArrayList<Note>(db.displayAllNotes())
//        var noItemText: TextView = findViewById(R.id.noItemText)
//
//
//        val mToolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMain)
//        setSupportActionBar(mToolbar)
//        supportActionBar?.title = "My Notes"
//
//        if(allNotes.isEmpty()){
//            noItemText.setVisibility(View.VISIBLE);
//        }else {
//            noItemText.setVisibility(View.GONE);
//            var mRecyclerView: RecyclerView = findViewById(R.id.recyclerView)
//
//            mRecyclerView.layoutManager = LinearLayoutManager(this)
//            mRecyclerView.adapter = MainAdapter(this, allNotes)
//        }

    }

//    fun show(mainActivity: MainActivity){
//        val intent = Intent(this, MainFragment::class.java)
//        startActivity(intent)
//    }

}
