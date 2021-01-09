package com.taiyilin.mandarinlearning.teacherClassroom

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
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.MobileNavigationDirections
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.FragmentTeacherClassroomBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory

class TeacherClassroomFragment : Fragment() {

    private val teacherClassroomViewModel by viewModels<TeacherClassroomViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_teacher_classroom, container, false)

        val binding = DataBindingUtil.inflate<FragmentTeacherClassroomBinding>(inflater, R.layout.fragment_teacher_classroom, container, false)
        binding.lifecycleOwner = this

        val adapter = TeacherClassroomAdapter(teacherClassroomViewModel, TeacherClassroomAdapter.OnClickListener{
            teacherClassroomViewModel.navigateToDetail(it)
        })
        val recyclerView = binding.recyclerTeacherClassroom
        recyclerView.adapter = adapter

        teacherClassroomViewModel.liveClassrooms.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("tttttt", "ttttttt=$it")
                binding.viewModel = teacherClassroomViewModel
                adapter.submitList(it)
            }
        })

        teacherClassroomViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let{
                Log.wtf("123123123","123$it")
                findNavController().navigate(MobileNavigationDirections.actionGlobalTeacherClassroomDetailFragment(it))

                teacherClassroomViewModel.onDetailNavigated()
            }
        })



        return binding.root
    }



}