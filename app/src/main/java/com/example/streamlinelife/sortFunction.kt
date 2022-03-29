package com.example.streamlinelife

import java.util.Comparator

/**
 *
 * i am using this class to call all function in other pages
 *
 * */
class sortFunction {
    class sortlistAscending: Comparator<Map.Entry<String, ArrayList<String>>> {
        override fun compare(
            p0: Map.Entry<String, ArrayList<String>>?,
            p1: Map.Entry<String, ArrayList<String>>?
        ): Int {
            return p0!!.value[1].compareTo(p1!!.value[1])
        }
    }

    class sortlistDescending: Comparator<Map.Entry<String, ArrayList<String>>> {
        override fun compare(
            p0: Map.Entry<String, ArrayList<String>>?,
            p1: Map.Entry<String, ArrayList<String>>?
        ): Int {
            return p1!!.value[1].compareTo(p0!!.value[1])
        }
    }

    class sortlistPrioritiesLow: Comparator<Map.Entry<String, ArrayList<String>>> {
        override fun compare(
            p0: Map.Entry<String, ArrayList<String>>?,
            p1: Map.Entry<String, ArrayList<String>>?
        ): Int {
            return p0!!.value[0].compareTo(p1!!.value[0])
        }
    }

    class sortlistPrioritiesHigh: Comparator<Map.Entry<String, ArrayList<String>>> {
        override fun compare(
            p0: Map.Entry<String, ArrayList<String>>?,
            p1: Map.Entry<String, ArrayList<String>>?
        ): Int {
            return p1!!.value[0].compareTo(p0!!.value[0])
        }
    }

    class sortlistDueDateEarlist: Comparator<Map.Entry<String, ArrayList<String>>> {
        override fun compare(
            p0: Map.Entry<String, ArrayList<String>>?,
            p1: Map.Entry<String, ArrayList<String>>?
        ): Int {
            return p0!!.value[3].compareTo(p1!!.value[3])
        }
    }

    class sortlistDueDateLatest: Comparator<Map.Entry<String, ArrayList<String>>> {
        override fun compare(
            p0: Map.Entry<String, ArrayList<String>>?,
            p1: Map.Entry<String, ArrayList<String>>?
        ): Int {
            return p1!!.value[3].compareTo(p0!!.value[3])
        }
    }
}