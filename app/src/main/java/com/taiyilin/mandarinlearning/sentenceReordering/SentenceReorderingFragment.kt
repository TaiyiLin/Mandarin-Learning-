package com.taiyilin.mandarinlearning.sentenceReordering

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.MainActivity
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.FragmentSentenceReorderingBinding


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
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SentenceReorderingViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = SRChatRoomAdapter()
        binding.messageList.adapter = adapter

        val list = classroomData.messageList!!
//      Log.d("aaa", "$list")
        classroomData.messageList?.let { adapter.separateMsgSubmitList(it) }



        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonHint.setOnClickListener {
            if (binding.recyclerMessage.visibility == View.GONE){
                binding.recyclerMessage.visibility = View.VISIBLE
            }else{
                binding.recyclerMessage.visibility = View.GONE
            }
        }

        binding.buttonRight.setOnClickListener {
            viewModel.next()
        }

        binding.buttonLeft.setOnClickListener {
            viewModel.back()
        }

        //Observe next button
        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it){
                    0 -> {
                        Log.d("aaaaaaaaaaaaa", "aaaaaaaaaa")
                        Toast.makeText(context,"Congrats! You just finished!", Toast.LENGTH_LONG).show()
                        viewModel.resetShowToast()
                    }
                    1 ->{
                        Log.d("bbbbbbbbbbb", "aaaaaaaaaa")
                        Toast.makeText(context,"Start your first question", Toast.LENGTH_LONG).show()
                        viewModel.resetShowToast()
                    }
                }
            }
        })
//                    Toast.makeText(context,"Congrats! You just finished!", Toast.LENGTH_LONG).show()
//                    viewModel.resetShowToast()






        return binding.root
    }

}


