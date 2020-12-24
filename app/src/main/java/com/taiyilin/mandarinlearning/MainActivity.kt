package com.taiyilin.mandarinlearning

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.login.LogInActivity
import com.taiyilin.mandarinlearning.login.LogInViewModel
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.pickRole.PickRoleActivity

class MainActivity : AppCompatActivity() {


    val viewModel by viewModels<MainViewModel>{getVmFactory()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //Login Check
        if (!UserManager.isLoggedIn) {

            startActivity(Intent(this, LogInActivity::class.java))

        } else {

            startActivity(Intent(this, PickRoleActivity::class.java))

//            val userName = UserManager.userName  ?:  "No Name"
//            viewModel.loginAndSetUser(UserManager.userUID!!, userName)
        }

        viewModel.intentToPickType.observe(this, Observer {
            it?.let {
                if (it){
                    startActivity(Intent(this, PickRoleActivity::class.java))
                }
            }
        })


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)
        
    }
}