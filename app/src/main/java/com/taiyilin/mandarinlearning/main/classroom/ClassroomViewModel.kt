package com.taiyilin.mandarinlearning.main.classroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.Question

class ClassroomViewModel : ViewModel() {


    val course = MutableLiveData<Course>()


    private val _navigateToDetail = MutableLiveData<Classroom>()
     val navigateToDetail: LiveData<Classroom>
    get() = _navigateToDetail


    fun getCourseById(id: String) {
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

            course.value = course1
        } else if(id == "C02"){
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
            course.value = course2
        }



        }


    fun navigateToDetail(classroom: Classroom) {
        _navigateToDetail.value = classroom
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    }

