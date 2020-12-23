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

        val adapter = ResultAdapter(viewModel)
        val recyclerResult = binding.recyclerviewResult
        recyclerResult.adapter = adapter

        //拿到整包Classroom
        viewModel.classroom.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("classroomResult", "classroomResult= $it")
                binding.viewModel = viewModel

//                adapter.submitList(/)
            }
        })



        return binding.root
    }


}