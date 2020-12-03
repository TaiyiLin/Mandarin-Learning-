package com.taiyilin.mandarinlearning.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.databinding.ItemHomeRecomdNPopCourseBinding


class HomeAdapterRecomdNPop : ListAdapter<Course, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CourseRNPViewHolder(
            ItemHomeRecomdNPopCourseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //把holder 轉型為下面自己建的ViewHolder
        val courseRNPViewHolder = holder as CourseRNPViewHolder

        //用getItem方法取得course的資料list
        val course = getItem(position)

        //呼叫自己的方法
        courseRNPViewHolder.bind(course)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

}

class CourseRNPViewHolder(private var binding: ItemHomeRecomdNPopCourseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) {

        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
        binding.recomdNPopCourse = course
        binding.executePendingBindings()
        binding.btnLearnMore.setOnClickListener {
            if (binding.hiddenView.visibility == View.GONE){
                binding.hiddenView.visibility=View.VISIBLE
                binding.btnLearnMore.text="Back"
            }else{
                binding.hiddenView.visibility = View.GONE
                binding.btnLearnMore.text="Learn More"
            }
        }

    }
}
