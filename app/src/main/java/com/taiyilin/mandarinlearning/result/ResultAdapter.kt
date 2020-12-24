package com.taiyilin.mandarinlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Question
import com.taiyilin.mandarinlearning.databinding.ItemClassroomResultBinding

class ResultAdapter() :
    ListAdapter<Question, RecyclerView.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResultViewHolder(
            ItemClassroomResultBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //把holder 轉型為下面自己建的ViewHolder
        val holder = holder as ResultViewHolder

        //用getItem方法取得course的資料list
        val question = getItem(position)

        //呼叫自己的方法
        holder.bind(question)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }
}


class ResultViewHolder(private var binding: ItemClassroomResultBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(question: Question) {

        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
        binding.question = question

        binding.executePendingBindings()

        binding.root
    }

}