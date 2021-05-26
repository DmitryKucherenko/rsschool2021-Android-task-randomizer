package com.rsschool.android2021
//интерфейс для взаимодействия фрагмента с хостом MainActivity
interface ActionPerformedListener {
    fun actionPerformed1(number:Int)
    fun actionPerformed2(min:Int, max:Int)
}