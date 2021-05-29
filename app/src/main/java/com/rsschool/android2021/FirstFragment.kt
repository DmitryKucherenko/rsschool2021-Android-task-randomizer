package com.rsschool.android2021

import android.R.string
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment


class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var actionPerformedListener:ActionPerformedListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.fragment_first, container, false)
        //преобразуем context MainActivity к интерфейсу ActionPerformedListener
        actionPerformedListener = context as ActionPerformedListener
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)


        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val min = view.findViewById<EditText>(R.id.min_value)
        val max = view.findViewById<EditText>(R.id.max_value)
        generateButton?.isEnabled=false
        //Добавляем слушателя на поле min для валидации введенных данных
        min.addTextChangedListener{
           if(!validation(min,max)) generateButton?.isEnabled=false else
               generateButton?.isEnabled=true
        }
        //Добавляем слушателя на поле max для валидации введенных данных
        max.addTextChangedListener{
                       if(!validation(min,max)) generateButton?.isEnabled=false else
                generateButton?.isEnabled=true
        }


        generateButton?.setOnClickListener {
        if(validation(min,max))
            actionPerformedListener.actionPerformed2(min.text.toString().toInt(),
                max.text.toString().toInt())
        }

    }
  // Валидация и отображение сообщения об ошибках ввода
    fun validation(min:EditText, max:EditText ):Boolean{
        min.setError(null)
        max.setError(null)
        val minValue = min.text.toString().toIntOrNull()
        val maxValue =  max.text.toString().toIntOrNull()
        if(minValue==null||minValue<0||minValue>Int.MAX_VALUE){
            min.setError(ENTER_CORRECT)
            return false
        }
        if(maxValue==null||maxValue<0||maxValue>Int.MAX_VALUE){
            max.setError(ENTER_CORRECT)
            return false
        }

        if(maxValue<minValue){
            min.setError(MIN_LESS)
            return false
        }

        return true
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }



        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val ENTER_CORRECT = "enter correct number"
        private const val MIN_LESS = "Min < Max!"

    }
}