package com.example.kotlinlogin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class uczenActivityTest {


    fun znajdzLiczbe(wiadomosc:String):Int {
        for(i in 0..wiadomosc.length)
            if (wiadomosc[i].isDigit())
                return wiadomosc[i].toInt()
        return 0
    }

    @Test
    fun test1(){
        var a ="2"
        assertEquals(2,znajdzLiczbe(a))
    }
    }
