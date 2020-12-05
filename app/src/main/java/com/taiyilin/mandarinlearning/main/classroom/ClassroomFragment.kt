package com.taiyilin.mandarinlearning.main.classroom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.taiyilin.mandarinlearning.MobileNavigationDirections
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.data.*
import com.taiyilin.mandarinlearning.databinding.FragmentClassroomBinding



class ClassroomFragment : Fragment() {

    private lateinit var classroomViewModel: ClassroomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        classroomViewModel =
            ViewModelProvider(this).get(ClassroomViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_classroom, container, false)

        val binding = DataBindingUtil.inflate<FragmentClassroomBinding>(inflater, R.layout.fragment_classroom, container, false)
        binding.lifecycleOwner = this

        //Answer for classroom1
        val answer101 = Answer(1, "你昨天晚上幾點回家?", "Intermediate")
        val answer102 = Answer(2, "我昨天通宵沒有回家", "Intermediate")
        val answerList1 = mutableListOf<Answer>()
        answerList1.add(answer101)
        answerList1.add(answer102)

        //Answer for classroom2
        val answer201 = Answer(1, "今天過得如何?", "Intermediate")
        val answer202 = Answer(2, "今天過得一樣糟", "Intermediate")
        val answerList2 = mutableListOf<Answer>()
        answerList2.add(answer201)
        answerList2.add(answer202)

        //Message for classroom1
        val message101 = Message("S01","T01", "嗨嗨Auntie Lin",20121205,true)
        val message102 = Message("T01","S01", "你好",20121205,false)
        val messageList1 = mutableListOf<Message>()
        messageList1.add(message101)
        messageList1.add(message102)

        //Message for classroom2
        val message201 = Message("S02", "T02", "Done!",20121205,true)
        val message202 = Message("T02", "S02", "Good work!",20121205,false)
        val messageList2 = mutableListOf<Message>()
        messageList2.add(message201)
        messageList2.add(message202)

        //Classroom
        val classroom1 = Classroom("CR01","C01","S01","T01", answerList1, messageList1)
        val classroom2 = Classroom("CR02","C02","S02","T02", answerList2, messageList2)

        val classroomList = mutableListOf<Classroom>()
        classroomList.add(classroom1)
        classroomList.add(classroom2)

        //TODO
        val adapter = ClassroomSelectedClassAdapter(classroomViewModel, ClassroomSelectedClassAdapter.OnClickListener{
            classroomViewModel.navigateToDetail(it)
        }) //類別N + () = 實例化

        val recyclerSelectedClassName = binding.recyclerSelectedClassName //binding . layout id 就可以拿到view元件
        recyclerSelectedClassName.adapter = adapter

        adapter.submitList(classroomList)

        classroomViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
              findNavController().navigate(MobileNavigationDirections.actionGlobalSentenceReorderingFragment(it))

                classroomViewModel.onDetailNavigated()

                }
            })

        return binding.root
    }

}