package com.taiyilin.mandarinlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.databinding.ItemClassroomResultBinding

class ResultAdapter(private val viewModel: ResultViewModel) :
    ListAdapter<Classroom, RecyclerView.ViewHolder>(DiffCallback) {

    

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
        val classroom = getItem(position)

        //呼叫自己的方法
        holder.bind(viewModel, classroom)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Classroom>() {
        override fun areItemsTheSame(oldItem: Classroom, newItem: Classroom): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Classroom, newItem: Classroom): Boolean {
            return oldItem == newItem
        }
    }
}


class ResultViewHolder(private var binding: ItemClassroomResultBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: ResultViewModel, classroom: Classroom) {

//        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
//        binding.viewModel = classroom


        binding.root


        binding.executePendingBindings()

    }

}