package com.taiyilin.mandarinlearning.sentenceReordering

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.Question

class SentenceReorderingViewModel(classroomData: Classroom, application: Application) :
    ViewModel() {

    //Classroom Data
    private val _classroomData = MutableLiveData<Classroom>().apply {
        value = classroomData
    }
    val classroomData: LiveData<Classroom>
        get() = _classroomData


    //Course Data
    private var courseData: Course? = null
    private var questionList = mutableListOf<Question>()


    //Question Data
    private val _questionData = MutableLiveData<Question>()

    val questionData: LiveData<Question>
        get() = _questionData

    private var position = 0

    //For blocking clicking next question
    private val _showToast = MutableLiveData<Int>()
    val showToast: LiveData<Int>
        get() = _showToast


    init {
        _classroomData.value = classroomData

        getCourseData("C01")
        _questionData.value = questionList[0]
    }

    private fun getCourseData(id: String) {

        if (id == "C01") {
            //Question for course1
            val question101 = Question(1, "回家/昨天/晚上/你/幾點?", "Sentence Reodering", "你昨天晚上幾點回家?")
            val question102 = Question(2, "沒有/昨天/回家/通宵/我", "Sentence Reodering", "我昨天通宵沒有回家")
            val questionList1 = mutableListOf<Question>()
            questionList1.add(question101)
            questionList1.add(question102)
            //Course1
            val course1 = Course(
                "C01",
                "Mandarin 101",
                "Intermediate",
                "3",
                "O001",
                1,
                20201203,
                null,
                questionList1
            )
            courseData = course1
            questionList = questionList1

        } else if (id == "C02") {
            //Question for course2
            val question201 = Question(1, "過得/今天/如何?", "Sentence Reodering", "今天過得如何?")
            val question202 = Question(2, "糟/今天/一樣/過得", "Sentence Reodering", "今天過得一樣糟")
            val questionList2 = mutableListOf<Question>()
            questionList2.add(question201)
            questionList2.add(question202)
            //Course2
            val course2 = Course(
                "C02",
                "Learning Mandarin So Much Fun",
                "Basic",
                "5",
                "O002",
                0,
                20201201,
                null,
                questionList2
            )
            courseData = course2
            questionList = questionList2

        }

    }

    fun next() {

        var size = questionList.size

        if (position < size) {

            position += 1

            if (position < size) {
                _questionData.value = questionList[position]  //有下一題
            }

        } else {
            _showToast.value = 1 //沒有下一題了
        }
    }

    fun resetShowToast() {
        Log.d("resetShowToast", "resetShowToast")
        _showToast.value = null
    }

    fun back() {
        if (position > 0) {
            position -= 1

            if (position >= 0) {
                _questionData.value = questionList[position]
            }

        } else {
            _showToast.value = 0 //第一題
        }

    }

}
