package com.example.streamlinelife

import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class showInListViewAndSort {
    /**
     *
     * calling this class in the deadline and complete and all reminder pages and calender view
     * so that i can get all the value from db and if the user want to sort they can
     * this class will call and update the list
     *
     * */
    private lateinit var sortentries: Map<String, ArrayList<String>>
    private lateinit var database: DBSupport

    @RequiresApi(Build.VERSION_CODES.O)
    fun getfromAutocompleteSortBy(view: View, autocompleteSortBy: AutoCompleteTextView, classname: String) {
        database = DBSupport(view.context)
        val getallReminderFromDatabase: Map<String,ArrayList<String>> = database.getAllReminders()

        for((i,value) in getallReminderFromDatabase.entries){
            when (value[4]) {
                "3" -> {
                    value[4] = "!!!"
                }
                "2" -> {
                    value[4] = "!!"
                }
                "1" -> {
                    value[4] = "!"
                }
                "0" ->{
                    value[4] = ""
                }
            }
            //making importance at the first position
            val imp = value[4]
            val pos = value.indexOf(imp)
            value.removeAt(pos)
            value.add(0,imp)
        }

        /**
         * sort function
         * madcoderzmadcoderz 4, Oscar SalgueroOscar Salguero 9, djgdjg  1, Ammar BukhariAmmar Bukhari 2, RatneZRatneZ 82277 silver badges99 bronze badges, Mortada IssaMortada Issa  3322 bronze badges, Jasmine JohnJasmine John  81388 silver badges1212 bronze badges, ShivangShivang 90788 silver badges1717 bronze badges, tony giltony gil   9, and YTerleYTerle 2, “How to get text from autocompletetextview?,” Stack Overflow, 01-Nov-1958.
         * [Online]. Available: https://stackoverflow.com/questions/4819813/how-to-get-text-from-autocompletetextview.
         * [Accessed: 26-Mar-2022].
         *
         * */
        if(autocompleteSortBy.text.toString() == "sort by"){
            sortfunction(
                getallReminderFromDatabase,
                autocompleteSortBy.text.toString(),
                classname,
                view
            )
        }
        autocompleteSortBy.onItemClickListener  = AdapterView.OnItemClickListener {
                adapterView: AdapterView<*>, view1: View, i: Int, l: Long -> sortfunction(getallReminderFromDatabase, adapterView.getItemAtPosition(i).toString(), classname, view)
        }
    }

    /**
     * Sort function
     *
     * “Tomap - Kotlin programming language,” Kotlin. [Online]. Available: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/to-map.html. [Accessed: 26-Mar-2022].
     * Baeldung, “Guide to sorting in Kotlin,” Baeldung on Kotlin, 09-Feb-2021. [Online]. Available: https://www.baeldung.com/kotlin/sort. [Accessed: 26-Mar-2022].
     * user2523529user2523529 7711 silver badge88 bronze badges, codeaholicguycodeaholicguy 1, Y kY k 10366 bronze badges, almostimplementedalmostimplemented 9133 bronze badges, and Avinash Kumar PandeyAvinash Kumar Pandey 69211 gold badge55 silver badges1010 bronze badges, “How to sort Hashmap of type Hashmap&gt; by value?,” Stack Overflow, 01-Sep-1963. [Online]. Available: https://stackoverflow.com/questions/33750549/how-to-sort-hashmap-of-type-hashmapstring-arrayliststring-by-value. [Accessed: 26-Mar-2022].
     * “Sort a list by specified comparator in Kotlin,” GeeksforGeeks, 20-Mar-2022. [Online]. Available: https://www.geeksforgeeks.org/sort-a-list-by-specified-comparator-in-kotlin/. [Accessed: 26-Mar-2022].
     * “Mutablemapof - Kotlin programming language,” Kotlin. [Online]. Available: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/mutable-map-of.html. [Accessed: 26-Mar-2022].
     *
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sortfunction(
        getallReminderFromDatabase: Map<String, ArrayList<String>>,
        itemAtPosition: Any,
        classname: String,
        view: View
    ){
        val MutableMap = mutableMapOf<String, java.util.ArrayList<String>>()

        //convert into Map to list so that i can sort by Values
        val entries: List<Map.Entry<String,ArrayList<String>>> = ArrayList<Map.Entry<String,ArrayList<String>>>(getallReminderFromDatabase.entries)

        when (itemAtPosition){
            "Title Ascending" ->{
                Collections.sort(entries, sortFunction.sortlistAscending())
                sortentries = getNewSortedMap(entries, MutableMap, classname)
                Toast.makeText(view.context,"Sort By $itemAtPosition", Toast.LENGTH_SHORT).show()
            }
            "Title Descending" ->{
                Collections.sort(entries, sortFunction.sortlistDescending())
                sortentries = getNewSortedMap(entries, MutableMap, classname)
                Toast.makeText(view.context,"Sort By $itemAtPosition", Toast.LENGTH_SHORT).show()
            }
            "Priorities Low" ->{
                Collections.sort(entries, sortFunction.sortlistPrioritiesLow())
                sortentries = getNewSortedMap(entries, MutableMap, classname)
                Toast.makeText(view.context,"Sort By $itemAtPosition", Toast.LENGTH_SHORT).show()
            }
            "Priorities High" ->{
                Collections.sort(entries, sortFunction.sortlistPrioritiesHigh())
                sortentries = getNewSortedMap(entries, MutableMap, classname)
                Toast.makeText(view.context,"Sort By $itemAtPosition", Toast.LENGTH_SHORT).show()
            }
            "Due Date Earliest" ->{
                Collections.sort(entries, sortFunction.sortlistDueDateEarlist())
                sortentries = getNewSortedMap(entries, MutableMap, classname)
                Toast.makeText(view.context,"Sort By $itemAtPosition", Toast.LENGTH_SHORT).show()
            }
            "Due Date Latest" ->{
                Collections.sort(entries, sortFunction.sortlistDueDateLatest())
                sortentries = getNewSortedMap(entries, MutableMap, classname)
                Toast.makeText(view.context,"Sort By $itemAtPosition", Toast.LENGTH_SHORT).show()
            }
            else -> {
                sortentries = getNewSortedMap(entries, MutableMap, classname)
            }
        }

        val saveInArray: Array<String> = Array(sortentries.count()){""}
        val saveIDs: Array<Int> = Array(sortentries.count()){0}
        var stringvalues = ""
        var index = 0
        var addwhitespace = 0
        for((key,value) in sortentries.entries){
            // deadline and complete is 0 then it will show otherwise it wont show
            saveIDs[index] = key.toInt()

            //saving in the string
            for(i in value){
                if(i.trim().isNotEmpty() && i != "0" && i != "1"){
                    if(addwhitespace == 0 && value[0].trim().isNotEmpty()){
                        stringvalues += "$i "
                    }
                    else if (addwhitespace == 0 && value[0].trim().isEmpty()){
                        stringvalues += "$i\n\n"
                    }
                    else{
                        stringvalues += "$i\n\n"
                    }
                    addwhitespace++
                }
            }
            saveInArray[index] = stringvalues
            index++
            addwhitespace = 0
            stringvalues = ""
        }

        /**
         * List view show all reminders
         *
         * web_page_person, “Kotlin Android Listview example,” TutorialKart, 21-Jan-2021. [Online]. Available: https://www.tutorialkart.com/kotlin-android/kotlin-android-listview-example/. [Accessed: 24-Mar-2022].
         * S. Naveed, “Listing activity using ListView in Android studio - kotlin,” Handy Opinion, 22-May-2021. [Online]. Available: https://handyopinion.com/listing-activity-using-listview-in-android-studio-kotlin/. [Accessed: 25-Mar-2022].
         *
         * */
        val listview: ListView
        val customadapter =  CustomAdapterReminder(view.context, saveInArray, saveIDs)
        when (classname) {
            "CompletedPage" -> {
                listview = view.findViewById(R.id.showallreminderInCompletedPage)
                listview.adapter = customadapter
            }
            "DeadlinePage" -> {
                listview = view.findViewById(R.id.listViewDeadlinesInDeadlinePage)
                listview.adapter = customadapter
            }
            "AllReminderPage" -> {
                listview = view.findViewById(R.id.showallreminderInAllReminderPage)
                listview.adapter = customadapter
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNewSortedMap(
        entries: List<Map.Entry<String, ArrayList<String>>>,
        MutableMap: MutableMap<String, java.util.ArrayList<String>>,
        classname: String
    ): Map<String, java.util.ArrayList<String>> {
        for ((key,value) in entries) {
            if (value[3].trim().isNotEmpty()){
                value[3] = value[3].replace("  ", "/").replace(",", "/")

                // string date to Date
                val getdateFromString = value[3].substring(0,value[3].indexOf("/"))
                val date = LocalDate.parse(getdateFromString, DateTimeFormatter.ISO_DATE)

                //current Date
                val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                val currentDateFormated = LocalDate.parse(currentDate , DateTimeFormatter.ISO_DATE)

                // string time to time
                val gettimeFromString = value[3].substring(value[3].indexOf("/") + 1, value[3].length)
                val convertinTime = LocalTime.parse(gettimeFromString,DateTimeFormatter.ISO_LOCAL_TIME)
                val currentTime = LocalTime.parse(LocalTime.now().toString(),DateTimeFormatter.ISO_LOCAL_TIME)

                when (classname) {
                    "CompletedPage" -> {
                        if (value[8].toInt() == 0 && value[9].toInt() == 1) {
                            getMutableMap(
                                value,
                                key,
                                date,
                                currentDateFormated,
                                MutableMap,
                                convertinTime,
                                currentTime
                            )
                        }
                    }
                    "DeadlinePage" -> {
                        if (value[8].toInt() == 1 && value[9].toInt() == 0) {
                            getMutableMap(
                                value,
                                key,
                                date,
                                currentDateFormated,
                                MutableMap,
                                convertinTime,
                                currentTime
                            )
                        }
                    }
                    "AllReminderPage" -> {
                        if (value[8].toInt() == 0 && value[9].toInt() == 0) {
                            MutableMap[key] = value
                        }
                    }
                }
            }
            else{
                when (classname) {
                    "CompletedPage" -> {
                        if (value[8].toInt() == 0 && value[9].toInt() == 1) {
                            MutableMap[key] = value
                        }
                    }
                    "DeadlinePage" -> {
                        if (value[8].toInt() == 1 && value[9].toInt() == 0) {
                            MutableMap[key] = value
                        }
                    }
                    "AllReminderPage" -> {
                        if (value[8].toInt() == 0 && value[9].toInt() == 0) {
                            MutableMap[key] = value
                        }
                    }
                }
            }
        }
        return MutableMap.toMap()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMutableMap(
        value: ArrayList<String>,
        key: String,
        date: LocalDate,
        currentDateFormated: LocalDate?,
        MutableMap: MutableMap<String, java.util.ArrayList<String>>,
        convertinTime: LocalTime,
        currentTime: LocalTime?
    ) {
        /**
         * “How to compare current date and time with another date and time in Android Code example,” how to compare current date and time with another date and time in android Code Example. [Online]. Available: https://www.codegrepper.com/code-examples/java/how+to+compare+current+date+and+time+with+another+date+and+time++in+android. [Accessed: 26-Mar-2022].
         *
         * */
        if (value[5].isNotEmpty()) {
            var imp = 0
            when (value[0]) {
                "!!!" -> {
                    imp = 3
                }
                "!!" -> {
                    imp = 2
                }
                "!" -> {
                    imp = 1
                }
                else -> {
                    imp = 0
                }
            }
            database.updateReminder(
                key.toInt(),
                value[1],
                value[2],
                value[3],
                value[4],
                imp,
                value[5],
                value[6],
                value[7],
                0,
                0
            )
        } else {
            if (date.isBefore(currentDateFormated)) {
                MutableMap[key] = value
            } else if (date.isEqual(currentDateFormated)) {
                if (convertinTime.isBefore(currentTime)) {
                    MutableMap[key] = value
                }
            }
        }
    }

    //calender view
    @RequiresApi(Build.VERSION_CODES.O)
    fun getfromCalenderView(view: View, calenderView: CalendarView, s: String) {
        database = DBSupport(view.context)
        val getallReminderFromDatabase: Map<String,ArrayList<String>> = database.getAllReminders()
        for((i,value) in getallReminderFromDatabase.entries){
            when (value[4]) {
                "3" -> {
                    value[4] = "!!!"
                }
                "2" -> {
                    value[4] = "!!"
                }
                "1" -> {
                    value[4] = "!"
                }
                "0" ->{
                    value[4] = ""
                }
            }
            //making importance at the first position
            val imp = value[4]
            val pos = value.indexOf(imp)
            value.removeAt(pos)
            value.add(0,imp)
        }


        /**
         * taken date and time format from the given link
         * JUNSJUNS 7766 bronze badges, aminographyaminography 20.3k1313 gold badges6262 silver badges7070 bronze badges, Shalu T DShalu T D                    3, and Vishal PawarVishal Pawar                    4, “Android studio Kotlin - how to display 2 digit number in text?,” Stack Overflow, 01-May-1968. [Online]. Available: https://stackoverflow.com/questions/63010209/android-studio-kotlin-how-to-display-2-digit-number-in-text. [Accessed: 27-Mar-2022].
         * */
        calenderView.setOnDateChangeListener(CalendarView.OnDateChangeListener {
                calendarView, year, month, day ->
                val formatTime: NumberFormat = DecimalFormat("00")
                showallReminderInCalenderPage(view, getallReminderFromDatabase,"${formatTime.format(year)}-${formatTime.format(month+1)}-${formatTime.format(day)}")
        })

        //current Date
        val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        showallReminderInCalenderPage(view, getallReminderFromDatabase,currentDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showallReminderInCalenderPage(
        view: View,
        allreeminders: Map<String, ArrayList<String>>,
        datechosebyUser: String
    ) {
        val MutableMap = mutableMapOf<String, java.util.ArrayList<String>>()

        for (i in allreeminders) {
            i.value[3] = i.value[3].replace("  ", "/").replace(",", "/")

            //database date
            val getdateFromString = i.value[3].substring(0, i.value[3].indexOf("/"))
            val date = LocalDate.parse(getdateFromString, DateTimeFormatter.ISO_DATE)

            //user chose date in calender
            val userdate = LocalDate.parse(datechosebyUser, DateTimeFormatter.ISO_DATE)

            //current date
            val currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            val currentDateFormated = LocalDate.parse(currentDate, DateTimeFormatter.ISO_DATE)

            println("$date -----------------  $userdate ------------------  $currentDateFormated")

            if (date.isEqual(userdate)){
                MutableMap[i.key] = i.value
            }
//            else if (){
//            else if(date.compareTo(currentDateFormated) == 0){
//                MutableMap[i.key] = i.value
//            }
        }

        val newMapsortedDate = MutableMap

        val saveInArray: Array<String> = Array(newMapsortedDate.count()){""}
        val saveIDs: Array<Int> = Array(newMapsortedDate.count()){0}
        var stringvalues = ""
        var index = 0
        var addwhitespace = 0
        for((key,value) in newMapsortedDate.entries){
            // deadline and complete is 0 then it will show otherwise it wont show
            saveIDs[index] = key.toInt()

            //saving in the string
            for(i in value){
                if(i.trim().length != 0 && i != "0" && i != "1"){
                    if(addwhitespace == 0){
                        stringvalues += i
                    }
                    else{
                        stringvalues += i + "\n\n"
                    }
                    addwhitespace++
                }
            }
            saveInArray[index] = stringvalues
            index++
            addwhitespace = 0
            stringvalues = ""
        }

        /**
         * List view show all reminders
         *
         * web_page_person, “Kotlin Android Listview example,” TutorialKart, 21-Jan-2021. [Online]. Available: https://www.tutorialkart.com/kotlin-android/kotlin-android-listview-example/. [Accessed: 24-Mar-2022].
         * S. Naveed, “Listing activity using ListView in Android studio - kotlin,” Handy Opinion, 22-May-2021. [Online]. Available: https://handyopinion.com/listing-activity-using-listview-in-android-studio-kotlin/. [Accessed: 25-Mar-2022].
         *
         * */
        val listview = view.findViewById<ListView>(R.id.showallreminderInCalenderView)
        val customadapter =  CustomAdapterReminder(view.context, saveInArray, saveIDs)
        listview.adapter = customadapter
    }
}