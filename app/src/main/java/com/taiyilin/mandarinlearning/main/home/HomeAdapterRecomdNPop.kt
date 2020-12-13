package com.taiyilin.mandarinlearning.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taiyilin.mandarinlearning.data.Classroom
import com.taiyilin.mandarinlearning.data.Course
import com.taiyilin.mandarinlearning.databinding.ItemHomeRecomdNPopCourseBinding
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.main.classroom.ClassroomSelectedClassAdapter
import com.taiyilin.mandarinlearning.main.classroom.ClassroomViewModel


class HomeAdapterRecomdNPop(private val homeViewModel: HomeViewModel) : ListAdapter<Course, RecyclerView.ViewHolder>(DiffCallback) {


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
        courseRNPViewHolder.bind(homeViewModel, course)
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

    fun bind(homeViewModel: HomeViewModel, course: Course) {

        //binding . 小layout id = 取得真正的值(在上面getItem方法取得的list)
        binding.recomdNPopCourse = course
        binding.btnLearnMore.setOnClickListener {
            if (binding.hiddenView.visibility == View.GONE){
                binding.hiddenView.visibility=View.VISIBLE
                binding.btnLearnMore.text="See Less"
            }else{
                binding.hiddenView.visibility = View.GONE
                binding.btnLearnMore.text="See More"
            }
        }

        val recyclerViewReview = binding.recyclerReview
        val homeAdapterFeedback = HomeAdapterFeedback()
        recyclerViewReview.adapter = homeAdapterFeedback
        homeAdapterFeedback.submitList(course.feedbackList)

        // 按下首頁推薦/熱門課程的+按鈕時，會把課程加入Classroom同時也更新課程的studentList名單
        binding.buttonPlus.setOnClickListener{
            homeViewModel.addSelectedCourse(course)
            UserManager.userUID?.let { it1 -> homeViewModel.updateCourse(course.id, it1) }
        binding.executePendingBindings()
        }

    }
}
