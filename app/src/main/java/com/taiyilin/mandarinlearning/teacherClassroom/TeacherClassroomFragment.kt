package com.taiyilin.mandarinlearning.teacherClassroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taiyilin.mandarinlearning.R

class TeacherClassroomFragment : Fragment() {

    companion object {
        fun newInstance() =
            TeacherClassroomFragment()
    }

    private lateinit var viewModel: TeacherClassroomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_classroom, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeacherClassroomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}