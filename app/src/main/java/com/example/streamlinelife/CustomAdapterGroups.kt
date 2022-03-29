package com.example.streamlinelife

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.ByteArrayOutputStream

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

        //all ids
        val saveIDs = saveIDs[index]

        //set data in the listview
        val imageView = view.findViewById<ImageView>(R.id.showcolorandlogo)
        imageView.setBackgroundColor(saveInArraycolor[index].toInt())

        val title = view.findViewById<TextView>(R.id.showgroupsinHomePage)
        title.text = saveInArrayname[index]

        val shownumber = view.findViewById<TextView>(R.id.showreminderinthehomepage)
        shownumber.text = saveInArraynumberofreminder[index]

        /**
         * BitMap
         * 262588213843476, “Android bitmap conversion to and from Byte Array,” Gist. [Online]. Available: https://gist.github.com/vvkirillov/6e0475a56b9b2b14cd97. [Accessed: 28-Mar-2022].
         * adesh singhadesh singh 1, Ankur ShanbhagAnkur Shanbhag  7, user2927772user2927772 5311 silver badge66 bronze badges, SpringLearnerSpringLearner  13.5k2020 gold badges7171 silver badges113113 bronze badges, and VahidVahid 13788 bronze badges, “How to store byte in an array and get back the string,” Stack Overflow, 01-Sep-1961. [Online]. Available: https://stackoverflow.com/questions/19900432/how-to-store-byte-in-an-array-and-get-back-the-string. [Accessed: 28-Mar-2022].
         * BenbenBenben  1, AAnkitAAnkit 26.6k1111 gold badges5757 silver badges7070 bronze badges, and AngeloAngelo  4, “Android: How to convert Byte Array to bitmap?,” Stack Overflow, 01-May-1960. [Online]. Available: https://stackoverflow.com/questions/11613594/android-how-to-convert-byte-array-to-bitmap. [Accessed: 28-Mar-2022].
         * ByteArrayOutputStream (java platform SE 7 ), 24-Jun-2020. [Online]. Available: https://docs.oracle.com/javase/7/docs/api/java/io/ByteArrayOutputStream.html. [Accessed: 28-Mar-2022].
         */
        if(saveInArraydrawbal[index].isNotEmpty()){
            // Converting string into bitmap
            val stream = ByteArrayOutputStream()
            // this string array that was split by commas
            val byte = saveInArraydrawbal[index].split(",")
            for (stringtobyte in byte){
                if (stringtobyte.isNotEmpty()){
                    stream.write(stringtobyte.toByte().toInt())
                }
            }
            val byteArray: ByteArray = stream.toByteArray()
            val convertbitArraytoBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            imageView.setImageBitmap(convertbitArraytoBitmap)
        }

        //button to see the group and list under that
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

