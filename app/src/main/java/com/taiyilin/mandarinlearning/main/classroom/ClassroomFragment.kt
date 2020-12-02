package com.taiyilin.mandarinlearning.main.classroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taiyilin.mandarinlearning.R

class ClassroomFragment : Fragment() {

    companion object {
        fun newInstance() =
            ClassroomFragment()
    }

    private lateinit var viewModel: ClassroomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classroom, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassroomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}