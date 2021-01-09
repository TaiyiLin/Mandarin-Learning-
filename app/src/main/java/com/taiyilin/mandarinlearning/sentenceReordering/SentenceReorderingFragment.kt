package com.taiyilin.mandarinlearning.sentenceReordering

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.MobileNavigationDirections
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.FragmentSentenceReorderingBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory


class SentenceReorderingFragment : Fragment() {

    private val viewModel by viewModels<SentenceReorderingViewModel> { getVmFactory(SentenceReorderingFragmentArgs.fromBundle(requireArguments()).classroomData)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSentenceReorderingBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

//       //取得上一個Fragment傳過來的參數
//       val classroomData = SentenceReorderingFragmentArgs.fromBundle(requireArguments()).classroomData

        //使用factory創建viewModel
//        val viewModelFactory = SentenceReorderingViewModelFactory(classroomData, application)
//        val viewModel = ViewModelProvider(this, viewModelFactory).get(SentenceReorderingViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = SRChatRoomAdapter()
        val recyclerView = binding.messageList
        recyclerView.adapter = adapter

//        val list = classroomData.messageList!!
//        Log.d("aaa", "$list")
//        classroomData.messageList?.let { adapter.separateMsgSubmitList(it) }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

//        binding.buttonHint.setOnClickListener {
//            if (binding.recyclerMessage.visibility == View.GONE){
//                binding.recyclerMessage.visibility = View.VISIBLE
//            }else{
//                binding.recyclerMessage.visibility = View.GONE
//            }
//        }

        binding.buttonRight.setOnClickListener {
            viewModel.next()
        }

        binding.buttonLeft.setOnClickListener {
            viewModel.back()
        }

      binding.buttonSubmit.setOnClickListener {
          viewModel.sendAnswer()
      }

       binding.buttonGetHint.setOnClickListener {

           val message = viewModel.messageContent.value
           if (message != null) {
               if (message.isEmpty()){

                   Toast.makeText(context,"message is empty", Toast.LENGTH_SHORT).show()
               }else{
                   viewModel.sendMessage()
               }
           }

       }


        viewModel.liveMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.separateMsgSubmitList(it)
                // set the view to the last element (list size)
                recyclerView.smoothScrollToPosition(it.size)
            }
        })

        //Click Result button navigate to resultFragment with Classroom Argument
        viewModel.classroomWithAnswers.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(MobileNavigationDirections.actionGlobalResultFragment(it))

                viewModel.onResultNavigated()
            }
        })


        //Observe next button
        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it){
                    0 -> {
                        Log.d("aaaaaaaaaaaaa", "aaaaaaaaaa")
                        Toast.makeText(context,"Start your first question", Toast.LENGTH_LONG).show()
                        viewModel.resetShowToast()
                    }
                    1 ->{
                        Log.d("bbbbbbbbbbb", "aaaaaaaaaa")
                        Toast.makeText(context,"Congrats! You just finished!", Toast.LENGTH_LONG).show()
                        viewModel.resetShowToast()
                    }
                }
            }
        })
//                    Toast.makeText(context,"Congrats! You just finished!", Toast.LENGTH_LONG).show()
//                    viewModel.resetShowToast()
        viewModel.liveAnswer.observe(viewLifecycleOwner, Observer {

        })


        return binding.root
    }

}


