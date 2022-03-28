package com.example.streamlinelife

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CustomAdapterGroups(
    context: Context,
    saveInArrayname: Array<String>,
    saveInArraycolor: Array<String>,
    saveInArraydrawbale: Array<String>,
    saveIDs: Array<Int>,
    saveInArraynumberofreminder: Array<String>,
    )   : BaseAdapter() {

    lateinit var context: Context
    var saveInArrayname: Array<String>
    var saveInArraycolor: Array<String>
    var saveInArraydrawbal: Array<String>
    var saveIDs: Array<Int>
    var saveInArraynumberofreminder: Array<String>

    var inflater: LayoutInflater
    override fun getCount(): Int {
        return saveInArrayname.size
    }

    override fun getItem(p0: Int): Any? {
       return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams", "UseCompatLoadingForDrawables")
    override fun getView(index: Int, view: View?, p2: ViewGroup?): View {
        var view = view
        view = inflater.inflate(R.layout.showgroupswithlogocolorandcountreminders, null)

        val saveIDs = saveIDs[index]

        //set data in the listview
        val imageView = view.findViewById<ImageView>(R.id.showcolorandlogo)
        imageView.setBackgroundColor(saveInArraycolor[index].toInt())

        val title = view.findViewById<TextView>(R.id.showgroupsinHomePage)
        title.text = saveInArrayname[index]

        val shownumber = view.findViewById<TextView>(R.id.showreminderinthehomepage)
        shownumber.text = saveInArraynumberofreminder[index]

        val button = view.findViewById<ImageButton>(R.id.ToastButton)
        button.setOnClickListener {
            Toast.makeText(view.context,"This is not Working",Toast.LENGTH_LONG).show()
        }
        return view
    }

    init {
        this.saveInArrayname = saveInArrayname
        this.saveInArraycolor = saveInArraycolor
        this.saveInArraydrawbal = saveInArraydrawbale
        this.saveIDs = saveIDs
        this.saveInArraynumberofreminder = saveInArraynumberofreminder
        inflater = LayoutInflater.from(context)
    }

}

