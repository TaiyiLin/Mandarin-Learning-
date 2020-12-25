package com.taiyilin.mandarinlearning.pickRole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.taiyilin.mandarinlearning.MainActivity
import com.taiyilin.mandarinlearning.MainViewModel
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.ActivityPickRoleBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.login.LogInActivity

class PickRoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickRoleBinding
    val viewModel by viewModels<PickRoleViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pick_role)
        binding.lifecycleOwner = this


        viewModel.pickedType.observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
        })


        binding.buttonStudent.setOnClickListener {
            binding.buttonTeacher.isEnabled = false
            binding.buttonTeacher.isClickable = false
            viewModel.pickRole("student")
        }

        binding.buttonTeacher.setOnClickListener {
            binding.buttonStudent.isEnabled = false
            binding.buttonStudent.isClickable = false
            viewModel.pickRole("teacher")
        }


    }
}