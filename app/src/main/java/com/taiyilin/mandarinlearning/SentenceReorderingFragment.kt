package com.taiyilin.mandarinlearning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SentenceReorderingFragment : Fragment() {

    companion object {
        fun newInstance() = SentenceReorderingFragment()
    }

    private lateinit var viewModel: SentenceReorderingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sentence_reordering, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SentenceReorderingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}