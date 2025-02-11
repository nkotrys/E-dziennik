package com.example.kotlinlogin

data class Zadanie (val id: Int, val opis: String, var zrobione: Boolean) {
    override fun toString(): String {
        if (zrobione) {
            return "${this.opis} [v]"
        } else {
            return "${this.opis} [ ]"
        }
    }
}