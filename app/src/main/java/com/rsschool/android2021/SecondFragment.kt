package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import kotlin.random.Random

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null
    private  var actionPerformedListener:ActionPerformedListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //преобразуем context MainActivity к интерфейсу ActionPerformedListener, чтобы потом вызывать метод из MainActivity
        if(context is ActionPerformedListener) {
            actionPerformedListener = context as ActionPerformedListener
        }else{
            throw RuntimeException("$context must implement ActionPerformedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0
        val genNumber =  generate(min, max)
        result?.text = genNumber.toString()
        //изменяем поведеним системной кнопки back для SecondFragment
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            actionPerformedListener?.actionPerformed1(genNumber)
        }
         // добавляем действи на кнопку backButton
        backButton?.setOnClickListener {
            //вызываем метод actionPerformed1 для отображеия фрагмента1 из хоста MainActivity с передачей сгенирированного числа
            // через интерфейсную ссылку ActionPerformedListener
            actionPerformedListener?.actionPerformed1(genNumber)
        }
    }
//генерируем число с max включительно. Без использования (min..max), max будет не включительно
    private fun generate(min: Int, max: Int): Int {
        return (min..max).random()
    }

    companion object {
        // Аннтотация JvmStatic указывает , что компилятору необходимо сгенерировать статический метод newInstance во всеобъемлеющем классе FirstFragment
// Это позволит сделать вызов из Java как FirstFragment.newInstance(..) вместо FirstFragment.COMPANION.newInstance(..)
        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            //создаем экземплят SecondFragment
            val fragment = SecondFragment()
            //Получаем ссылку на Bundle(). Bundle - это хранилище в видте Ключ/значение
            val args = Bundle()
            //ложим значения min, max в Bundle
            args.putInt(MIN_VALUE_KEY,min)
            args.putInt(MAX_VALUE_KEY,max)
            fragment.arguments = args
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}