package com.taiyilin.mandarinlearning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TeacherClassroomDetailFragment : Fragment() {

    companion object {
        fun newInstance() = TeacherClassroomDetailFragment()
    }

    private lateinit var viewModel: TeacherClassroomDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.teacher_classroom_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeacherClassroomDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}