package com.taiyilin.mandarinlearning.homeDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.databinding.FragmentHomeDetailBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.main.home.HomeAdapterFeedback
import com.taiyilin.mandarinlearning.main.home.HomeAdapterRecomdNPop

class HomeDetailFragment : Fragment() {

    private val viewModel by viewModels<HomeDetailViewModel> {
        getVmFactory(
            HomeDetailFragmentArgs.fromBundle(requireArguments()).courseDetail
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Feedback
        val recyclerViewReview = binding.recyclerReview
        val homeAdapterFeedback = HomeAdapterFeedback()
        recyclerViewReview.adapter = homeAdapterFeedback


        //Home page Course
        viewModel.courseDetail.observe(viewLifecycleOwner, Observer {
            it?.let {

                homeAdapterFeedback.submitList(it.feedbackList)
                Log.d("feedback", "feedback=${it.feedbackList}")
            }

        })

      viewModel.feedbackList.observe(viewLifecycleOwner, Observer {
          it?.let {
              homeAdapterFeedback.submitList(it)
          }
      })




        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}

