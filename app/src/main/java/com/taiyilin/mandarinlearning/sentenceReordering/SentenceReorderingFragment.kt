package com.taiyilin.mandarinlearning.sentenceReordering

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.MainActivity
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.FragmentSentenceReorderingBinding
import com.taiyilin.mandarinlearning.main.classroom.ClassroomViewModel


class SentenceReorderingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSentenceReorderingBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        //取得上一個Fragment傳過來的參數
        val classroomData = SentenceReorderingFragmentArgs.fromBundle(requireArguments()).classroomData

        //使用factory創建viewModel
        val viewModelFactory = SentenceReorderingViewModelFactory(classroomData, application)
        binding.viewModel= ViewModelProvider(
            this, viewModelFactory
        ).get(SentenceReorderingViewModel::class.java)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

}


