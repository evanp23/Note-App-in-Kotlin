package com.evanp.noteapp2

import java.util.*

class Note{
    var id: Int = 0
    var title: String = ""
    var content: String = ""
    var date: String = ""
    var time: String = ""

    constructor(title:String, content:String, date:String, time:String){
        this.title = title
        this.content = content
        this.date = date
        this.time = time
    }

    constructor(id:Int, title:String, content:String, date:String, time:String){
        this.id = id
        this.title = title
        this.content = content
        this.date = date
        this.time = time
    }

}