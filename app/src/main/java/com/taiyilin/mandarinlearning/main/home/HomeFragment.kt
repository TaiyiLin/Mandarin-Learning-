package com.taiyilin.mandarinlearning.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.data.Feedback
import com.taiyilin.mandarinlearning.databinding.FragmentHomeBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import kotlin.math.log


class HomeFragment : Fragment() {


    private val homeViewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)

        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this

        //Continue Adapter
        val adapter = HomeAdapter() //類別N + () = 實例化
        val recyclerviewContinueCourse = binding.recyclerviewContinueCourse //binding . layout id 就可以拿到view元件
        recyclerviewContinueCourse.adapter = adapter

        //Recommended Adapter
        val adapterR = HomeAdapterRecomdNPop(homeViewModel) //類別N + () = 實例化
        val recyclerviewRecommendedCourse = binding.recyclerviewRecommendedCourse
        recyclerviewRecommendedCourse.adapter = adapterR

        //Popular Adapter
        val adapterP = HomeAdapterRecomdNPop(homeViewModel) //類別N + () = 實例化
        val recyclerviewPopularCourse = binding.recyclerviewPopularCourse
        recyclerviewPopularCourse.adapter = adapterP

        homeViewModel.liveCourses.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("aaaaaaa", "aaaaaaaa  = $it")
            }
        })

        homeViewModel.liveUserCourse.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                homeViewModel.getNonSelectedCourses()
            }
        })

        homeViewModel.liveNonSelectedCourses.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("ccccccc", "cccccc = $it")
                adapterR.submitList(it)
            }
        })

        //Home page Course
        homeViewModel.course.observe(viewLifecycleOwner, Observer {
            it?.let {

                adapterP.submitList(it)

            }
        })


        //Continue Course
//        val course1 = Course("MA123", "Mandarin for Beginners", "Basic")
//        val course2 = Course("MA225", "Learning Mandarin So Much Fun", "Advanced")
//        val course3 = Course("MA402", "Mandarin Grammar for Beginners", "Basic")
//        val list = mutableListOf<Course>()
//        list.add(course1)
//        list.add(course2)
//        list.add(course3)

//        val adapter = HomeAdapter() //類別N + () = 實例化
//
//        val recyclerviewContinueCourse =
//            binding.recyclerviewContinueCourse //binding . layout id 就可以拿到view元件
//        recyclerviewContinueCourse.adapter = adapter
//
//        adapter.submitList(list)


//        //Feedback
//        val feedback1 = Feedback("001", "I highly recommend it! The best course ever.")
//        val feedback2 = Feedback("002", "Auntie Lin is very helpful!!!!!")
//        val feedbackList1 = mutableListOf<Feedback>()
//        feedbackList1.add(feedback1)
//        feedbackList1.add(feedback2)

        //Recommended & Popular courses
        //R
//        val courseRNP1 =
//            Course("001", "Mandarin 101", "Basic", "4.5", "T105", null, 20201208, feedbackList1)
//        val courseRNP2 = Course("002", "Mandarin 102", "Intermediate")
//        val courseRNP3 = Course("003", "Mandarin 103", "Advanced")
//        val listR = mutableListOf<Course>()
//        listR.add(courseRNP1)
//        listR.add(courseRNP2)
//        listR.add(courseRNP3)
//
//        val adapterR = HomeAdapterRecomdNPop() //類別N + () = 實例化
//
//        val recyclerviewRecommendedCourse = binding.recyclerviewRecommendedCourse
//        recyclerviewRecommendedCourse.adapter = adapterR
//
//        adapterR.submitList(listR)


        //P
//        val courseRNP4 = Course("004", "Mandarin 104", "Basic")
//        val courseRNP5 = Course("005", "Mandarin 105", "Intermediate")
//        val courseRNP6 = Course("006", "Mandarin 106", "Advanced")
//
//        val listP = mutableListOf<Course>()
//        listP.add(courseRNP4)
//        listP.add(courseRNP5)
//        listP.add(courseRNP6)

//        val adapterP = HomeAdapterRecomdNPop() //類別N + () = 實例化
//
//        val recyclerviewPopularCourse = binding.recyclerviewPopularCourse
//        recyclerviewPopularCourse.adapter = adapterP
//
//        adapterP.submitList(listP)


        binding.textviewSeeAll.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_global_navigation_classroom)
        }

        return binding.root
    }
}