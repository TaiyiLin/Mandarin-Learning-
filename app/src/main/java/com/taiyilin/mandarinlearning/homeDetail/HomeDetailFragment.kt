package com.taiyilin.mandarinlearning.homeDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.FragmentHomeDetailBinding
import com.taiyilin.mandarinlearning.databinding.FragmentSentenceReorderingBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingFragmentArgs
import com.taiyilin.mandarinlearning.sentenceReordering.SentenceReorderingViewModel

class HomeDetailFragment : Fragment() {

//private val viewModel by viewModels<HomeDetailViewModel> {getVmFactory(
//    HomeDetailFragmentArgs.fromBundle(requireArguments()).courseDe)}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

//        val course




        return binding.root
    }

}


//private val viewModel by viewModels<SentenceReorderingViewModel> {getVmFactory(
//    SentenceReorderingFragmentArgs.fromBundle(requireArguments()).classroomData)}
//
//override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View? {
//
//    val binding = FragmentSentenceReorderingBinding.inflate(inflater, container, false)
//
//    val application = requireNotNull(activity).application
//
//    binding.lifecycleOwner = this
//
////       //取得上一個Fragment傳過來的參數
////       val classroomData = SentenceReorderingFragmentArgs.fromBundle(requireArguments()).classroomData
//
//    //使用factory創建viewModel
////        val viewModelFactory = SentenceReorderingViewModelFactory(classroomData, application)
////        val viewModel = ViewModelProvider(this, viewModelFactory).get(SentenceReorderingViewModel::class.java)
//
//    binding.viewModel = viewModel