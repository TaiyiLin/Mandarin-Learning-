package com.taiyilin.mandarinlearning.teacherProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taiyilin.mandarinlearning.R

class TeacherProfileFragment : Fragment() {

    companion object {
        fun newInstance() =
            TeacherProfileFragment()
    }

    private lateinit var viewModel: TeacherProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_profile_, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeacherProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}