package com.example.triviagame.ui
import ResultFragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.example.triviagame.R
import com.example.triviagame.data.Constants
import com.example.triviagame.data.TriviaQuestion
import com.example.triviagame.databinding.FragmentHomeBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import kotlin.system.exitProcess
class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    private val client = OkHttpClient()
    var index:Int = 0
     private  var  point:Int = 0
    var correctAnswer = ""
    var answerQuestion = mutableListOf<String?>()
    override val LOG_TAG: String
        get() = javaClass.simpleName
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding = FragmentHomeBinding::inflate

    override fun setup() {
        showInfo()
        getNextQuestion()
        getCorrectAnswer()
        closeApp()
    }

    override fun addCallBack() {
    }
private fun closeApp(){
    binding?.textQuit?.setOnClickListener{
         quit()
    }}
    private fun showInfo() = if (index>=10) displayResultFragment()else {

        val url = "https://opentdb.com/api.php?amount=10&category=21&difficulty=easy&type=multiple"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("FAILED , ${e.message.toString()}")
            }
            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { jsonString ->
                    val homeInfo = Gson().fromJson(jsonString, TriviaQuestion::class.java)
                    val info = homeInfo.results?.toMutableList()?.get(index)
                    answerQuestion = mutableListOf(
                        info?.incorrect_answers?.get(0),
                        info?.incorrect_answers?.get(1),
                        info?.incorrect_answers?.get(2),
                        info?.correct_answer
                    ).shuffled().toMutableList()
                    correctAnswer = info?.correct_answer.toString()
                    activity?.runOnUiThread {
                        binding?.textQuestionDescription?.text = info?.question
                        binding?.textChoice1?.text = answerQuestion[0]
                        binding?.textChoice2?.text = answerQuestion[1]
                        binding?.textChoice3?.text = answerQuestion[2]
                        binding?.textChoice4?.text = answerQuestion[3]
                        binding?.textQuestionNum?.text = index.toString()

                        isEnabledButton(value = false)
                    }
                    index++
                    println(info?.correct_answer!!)
                }
            }

        })
    }
    private fun displayResultFragment() {
        val resultFragment = ResultFragment()
        val bundle= Bundle()
        bundle.putInt(Constants.POINTS,point)
        resultFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container,resultFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isEnabledButton(value:Boolean){
        when(value){
            false-> binding?.buttonNext?.isEnabled = false
            true -> binding?.buttonNext?.isEnabled = true
        }
    }

    private fun getNextQuestion() {
        binding?.radioGroup?.setOnClickListener {
            isEnabledButton(true)
        }
        binding?.buttonNext?.setOnClickListener {
            showInfo()
            getDefaultStyle()
        }
    }

    @SuppressLint("ResourceType")
    private fun getCorrectAnswer() {
        binding?.textChoice1?.setOnClickListener {
            getAnswer(binding?.textChoice1!!)
            countPoints(binding?.textChoice1!!)
        }
        binding?.textChoice2?.setOnClickListener {
            getAnswer(binding?.textChoice2!!)
            countPoints(binding?.textChoice2!!)

        }
        binding?.textChoice3?.setOnClickListener {
            getAnswer(binding?.textChoice3!!)
            countPoints(binding?.textChoice3!!)

        }
        binding?.textChoice4?.setOnClickListener {
            getAnswer(binding?.textChoice4!!)
            countPoints(binding?.textChoice4!!)

        }
    }

    private fun getAnswer(textView: TextView) {
        when(textView){
            binding?.textChoice1 -> binding?.textChoice1!!.setBackgroundResource(R.drawable.selected_answer)
            binding?.textChoice2 -> binding?.textChoice2!!.setBackgroundResource(R.drawable.selected_answer)
            binding?.textChoice3 -> binding?.textChoice3!!.setBackgroundResource(R.drawable.selected_answer)
            binding?.textChoice4 -> binding?.textChoice4!!.setBackgroundResource(R.drawable.selected_answer)
        }
        isEnabledButton(true)
    }

    private fun getDefaultStyle(){
        binding?.textChoice1?.setBackgroundResource(R.drawable.shape_choice)
        binding?.textChoice2?.setBackgroundResource(R.drawable.shape_choice)
        binding?.textChoice3?.setBackgroundResource(R.drawable.shape_choice)
        binding?.textChoice4?.setBackgroundResource(R.drawable.shape_choice)
    }
    private fun countPoints(answerText:TextView){
        if (answerText.text == correctAnswer)
            point++
        println("POINTS: $point")
    }
     private fun quit() {
         exitProcess(-1)
     }

     }

