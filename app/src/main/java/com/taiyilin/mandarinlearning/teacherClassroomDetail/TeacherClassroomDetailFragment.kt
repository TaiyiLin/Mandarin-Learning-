package com.taiyilin.mandarinlearning.teacherClassroomDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.FragmentTeacherClassroomBinding
import com.taiyilin.mandarinlearning.databinding.FragmentTeacherClassroomDetailBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory

class TeacherClassroomDetailFragment : Fragment() {

    private val viewModel by viewModels<TeacherClassroomDetailViewModel> {
        getVmFactory(TeacherClassroomDetailFragmentArgs.fromBundle(requireArguments()).classroomKey)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentTeacherClassroomDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = TeacherCLRDetailAdapter()
        val recyclerView = binding.messageList
        recyclerView.adapter = adapter

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonRight.setOnClickListener {
            viewModel.next()
        }

        binding.buttonLeft.setOnClickListener {
            viewModel.back()
        }

        //send msg in chat room
        binding.buttonGetHint.setOnClickListener {

            val message = viewModel.messageContent.value
            if (message != null) {
                if (message.isEmpty()) {

                    Toast.makeText(context, "message is empty", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.sendMessage()
                }
            }

        }

        viewModel.liveMessage.observe(viewLifecycleOwner, Observer {
            it?.let {

                adapter.separateMsgSubmitList(it)
                // set the view to the last element (list size)
//                recyclerView.smoothScrollToPosition(it.size)
            }
        })

        //Observe next button
        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    0 -> {
                        Log.d("aaaaaaaaaaaaa", "aaaaaaaaaa")
                        Toast.makeText(context, "Start your first question", Toast.LENGTH_LONG)
                            .show()
                        viewModel.resetShowToast()
                    }
                    1 -> {
                        Log.d("bbbbbbbbbbb", "aaaaaaaaaa")
                        Toast.makeText(context, "Congrats! You just finished!", Toast.LENGTH_LONG)
                            .show()
                        viewModel.resetShowToast()
                    }
                }
            }
        })


        return binding.root
    }


}