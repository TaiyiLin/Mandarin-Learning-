package com.taiyilin.mandarinlearning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ClassesNFeedbackFragment : Fragment() {

    companion object {
        fun newInstance() = ClassesNFeedbackFragment()
    }

    private lateinit var viewModel: ClassesNFeedbackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classes_n_feedback, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassesNFeedbackViewModel::class.java)
        // TODO: Use the ViewModel
    }

}