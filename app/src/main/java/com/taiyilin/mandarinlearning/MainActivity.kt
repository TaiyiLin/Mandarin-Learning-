package com.taiyilin.mandarinlearning

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.taiyilin.mandarinlearning.databinding.ActivityMainBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.login.LogInActivity
import com.taiyilin.mandarinlearning.login.LogInViewModel
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.pickRole.PickRoleActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //Login Check
        if (!UserManager.isLoggedIn) {

            startActivity(Intent(this, LogInActivity::class.java))

        } else {
            viewModel.getUser(UserManager.userUID!!, UserManager.userName!!)

        }

        binding.textToolbarTitle.setOnClickListener {
            UserManager.clear()
        }
        viewModel.intentToPickType.observe(this, Observer {
            it?.let {
                if (it) {
                    startActivity(Intent(this, PickRoleActivity::class.java))
                }
            }
        })


        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

    }
}