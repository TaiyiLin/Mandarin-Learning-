package com.taiyilin.mandarinlearning.teacherHome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taiyilin.mandarinlearning.R

class TeacherHomeFragment : Fragment() {

    companion object {
        fun newInstance() =
            TeacherHomeFragment()
    }

    private lateinit var viewModel: TeacherHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeacherHomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}