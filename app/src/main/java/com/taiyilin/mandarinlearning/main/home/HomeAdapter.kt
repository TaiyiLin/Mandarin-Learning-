package com.taiyilin.mandarinlearning.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.databinding.ItemHomeContinueCourseBinding

//class HomeAdapter(private val showDetail: Boolean): ListAdapter<Course, RecyclerView.ViewHolder> (DiffCallback){


class HomeAdapter: ListAdapter<Course, RecyclerView.ViewHolder> (DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CourseViewHolder(
            ItemHomeContinueCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //把holder 轉型為下面自己建的ViewHolder
        val holder = holder as CourseViewHolder

        //用getItem方法取得course的資料list
        val course = getItem(position)

        //呼叫自己的方法
        holder.bind(course)
    }



    companion object DiffCallback : DiffUtil.ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

}


class CourseViewHolder(private var binding: ItemHomeContinueCourseBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(course: Course){

        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
        binding.continuingCourse = course
        binding.executePendingBindings()

//        if (showDetail){
//
//        } else {
//
//        }


    }

}