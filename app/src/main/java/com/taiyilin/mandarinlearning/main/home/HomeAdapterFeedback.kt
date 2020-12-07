package com.taiyilin.mandarinlearning.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Feedback
import com.taiyilin.mandarinlearning.databinding.ItemHomeCourseReviewBinding


class HomeAdapterFeedback : ListAdapter<Feedback, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CourseReviewViewHolder(
            ItemHomeCourseReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //把holder 轉型為下面自己建的ViewHolder
        val courseReviewViewHolder = holder as CourseReviewViewHolder

        //用getItem方法取得course的資料list
        val feedback = getItem(position)

        //呼叫自己的方法
        courseReviewViewHolder.bind(feedback)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Feedback>() {
        override fun areItemsTheSame(oldItem: Feedback, newItem: Feedback): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Feedback, newItem: Feedback): Boolean {
            return oldItem == newItem
        }
    }
}


class CourseReviewViewHolder(private var binding: ItemHomeCourseReviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(feedback: Feedback) {

        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
        binding.feedback = feedback
        binding.executePendingBindings()


    }
}
