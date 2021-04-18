package com.evanp.noteapp2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import kotlin.coroutines.coroutineContext


class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>{
    var layoutInflater: LayoutInflater
    var mNotes: ArrayList<Note> = ArrayList()
    var context: Context

    constructor(context: Context, notes: ArrayList<Note>){
        this.layoutInflater = LayoutInflater.from(context)
        mNotes = notes
        this.context = context
    }


    override fun getItemCount(): Int {
        return mNotes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title : TextView
        var date : TextView
        var time : TextView

        init{
            title = itemView.findViewById(R.id.noteTitle)
            date = itemView.findViewById(R.id.noteDate)
            time = itemView.findViewById(R.id.noteTime)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = layoutInflater.inflate(R.layout.fragment_card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(mNotes[position].title.length > 24){
            val noteSubstr: String = mNotes[position].title.toString().substring(0, 20) + "..."
            holder.itemView.findViewById<TextView>(R.id.noteTitle).text = noteSubstr
        }else{
            holder.itemView.findViewById<TextView>(R.id.noteTitle).text = mNotes[position].title
        }

        holder.itemView.findViewById<TextView>(R.id.noteDate).text = mNotes[position].date
        holder.itemView.findViewById<TextView>(R.id.noteTime).text = mNotes[position].time

        holder.itemView.setOnClickListener{v: View ->

            val id: Int = mNotes[position].id
            val action = MainFragmentDirections.actionMainFragmentToViewNoteFragment(id)
            Navigation.findNavController(v).navigate(action)



//            val intent = Intent(context, ViewNote::class.java).apply {
//                    putExtra("id", id)
//            }
//            startActivity(v.context, intent, null)
        }

    }

}