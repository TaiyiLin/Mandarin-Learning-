package com.taiyilin.mandarinlearning.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false)

        val course1 = Course("MA123", "Mandarin for Beginners")
        val course2 = Course("MA225", "Learning Mandarin So Much Fun")
        val course3 = Course("MA402", "Mandarin Grammar for Beginners")
        val list = mutableListOf<Course>()
        list.add(course1)
        list.add(course2)
        list.add(course3)

        val adapter = HomeAdapter() //類別N + () = 實例化

        val recyclerviewContinueCourse = binding.recyclerviewContinueCourse //binding . layout id 就可以拿到view元件
        recyclerviewContinueCourse.adapter = adapter

        adapter.submitList(list)

        //Recommended & Popular courses
        //R
        val courseRNP1 = Course("001","Mandarin 101")
        val courseRNP2 = Course("002","Mandarin 102")
        val courseRNP3 = Course("003","Mandarin 103")
        val listR = mutableListOf<Course>()
        listR.add(courseRNP1)
        listR.add(courseRNP2)
        listR.add(courseRNP3)

        val adapterR = HomeAdapterRecomdNPop() //類別N + () = 實例化

        val recyclerviewRecommendedCourse = binding.recyclerviewRecommendedCourse
        recyclerviewRecommendedCourse.adapter = adapterR

        adapterR.submitList(listR)

        //P
        val courseRNP4 = Course("004","Mandarin 104")
        val courseRNP5 = Course("005","Mandarin 105")
        val courseRNP6 = Course("006","Mandarin 106")

        val listP = mutableListOf<Course>()
        listP.add(courseRNP4)
        listP.add(courseRNP5)
        listP.add(courseRNP6)

        val adapterP = HomeAdapterRecomdNPop() //類別N + () = 實例化

        val recyclerviewPopularCourse = binding.recyclerviewPopularCourse
        recyclerviewPopularCourse.adapter = adapterP

        adapterP.submitList(listP)


        binding.textviewSeeAll.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_global_navigation_classroom)
        }


        return binding.root
    }
}