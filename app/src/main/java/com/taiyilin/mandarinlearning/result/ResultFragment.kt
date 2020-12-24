package com.taiyilin.mandarinlearning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.taiyilin.mandarinlearning.databinding.FragmentResultBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory

class ResultFragment : Fragment() {

    private val viewModel by viewModels<ResultViewModel> {getVmFactory(ResultFragmentArgs.fromBundle(requireArguments()).classroomKey)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_result, container, false)

        val binding = DataBindingUtil.inflate<FragmentResultBinding>(inflater,R.layout.fragment_result,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ResultAdapter()
        val recyclerResult = binding.recyclerviewResult
        recyclerResult.adapter = adapter

        //拿到整包QuestionList w/ student's answer and correct answer
        viewModel.questionList.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("qNa", "qNa= $it")

                adapter.submitList(it)
            }
        })



        return binding.root
    }


}