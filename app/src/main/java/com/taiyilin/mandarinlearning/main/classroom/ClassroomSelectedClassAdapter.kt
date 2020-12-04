package com.taiyilin.mandarinlearning.main.classroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.databinding.ItemClassroomSelectedCoursesBinding

class ClassroomSelectedClassAdapter(private val viewModel: ClassroomViewModel, private val onClickListener: OnClickListener ) :
    ListAdapter<Classroom, RecyclerView.ViewHolder>(DiffCallback) {


    class OnClickListener(val clickListener: (classroom: Classroom) -> Unit) {
        fun onClick(classroom: Classroom) = clickListener(classroom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ClassroomViewHolder(
            ItemClassroomSelectedCoursesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //把holder 轉型為下面自己建的ViewHolder
        val holder = holder as ClassroomViewHolder

        //用getItem方法取得course的資料list
        val classroom = getItem(position)

        //呼叫自己的方法
        holder.bind(viewModel, classroom, onClickListener)
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


class ClassroomViewHolder(private var binding: ItemClassroomSelectedCoursesBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: ClassroomViewModel, classroom: Classroom, onClickListener: ClassroomSelectedClassAdapter.OnClickListener) {

        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
        binding.classroom = classroom

        viewModel.getCourseById(classroom.courseId)
        val course = viewModel.course.value
        binding.selectedCourse = course

        binding.root.setOnClickListener {
            if (course != null) {
                onClickListener.onClick(classroom)
            }
        }

        binding.executePendingBindings()

    }

}