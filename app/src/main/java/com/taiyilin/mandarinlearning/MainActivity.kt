package com.taiyilin.mandarinlearning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.taiyilin.mandarinlearning.data.User
import com.taiyilin.mandarinlearning.databinding.ActivityMainBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.login.LogInActivity
import com.taiyilin.mandarinlearning.login.LogInViewModel
import com.taiyilin.mandarinlearning.login.UserManager
import com.taiyilin.mandarinlearning.pickRole.PickRoleActivity

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var binding: ActivityMainBinding
    val viewModel by viewModels<MainViewModel> { getVmFactory() }
    companion object{
        const val STUDENT = "student"
        const val TEACHER = "teacher"
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){

                R.id.navigation_home -> {
                    if (UserManager.userType == STUDENT){
                        findNavController(R.id.nav_host_fragment).navigate(MobileNavigationDirections.actionGlobalNavigationHome())
                        return@OnNavigationItemSelectedListener true
                    } else if (UserManager.userType == TEACHER) {
                        findNavController(R.id.nav_host_fragment).navigate(MobileNavigationDirections.actionGlobalTeacherHomeFragment())
                        return@OnNavigationItemSelectedListener true
                    } else {
                        return@OnNavigationItemSelectedListener false
                    }
                }

                R.id.navigation_classroom -> {
                    Log.d("123","UserManager=${UserManager.userType}")
                    if (UserManager.userType == STUDENT){
                        findNavController(R.id.nav_host_fragment).navigate(MobileNavigationDirections.actionGlobalNavigationClassroom())
                        return@OnNavigationItemSelectedListener true
                    } else if (UserManager.userType == TEACHER) {
                        findNavController(R.id.nav_host_fragment).navigate(MobileNavigationDirections.actionGlobalTeacherClassroomFragment())
                        return@OnNavigationItemSelectedListener true
                    } else {
                        return@OnNavigationItemSelectedListener false
                    }
                }

                R.id.navigation_profile -> {
                    if (UserManager.userType == STUDENT){
                        findNavController(R.id.nav_host_fragment).navigate(MobileNavigationDirections.actionGlobalNavigationProfile())
                        return@OnNavigationItemSelectedListener true
                    } else if (UserManager.userType == TEACHER) {
                        findNavController(R.id.nav_host_fragment).navigate(MobileNavigationDirections.actionGlobalTeacherProfileFragment())
                        return@OnNavigationItemSelectedListener true
                    } else {
                        return@OnNavigationItemSelectedListener false
                    }
                }

            }
            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


//        val navView: BottomNavigationView = findViewById(R.id.nav_view)

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

        setUpBottomNav()






//
//        val navController = findNavController(R.id.nav_host_fragment)
//        navView.setupWithNavController(navController)

    }

    fun setUpBottomNav(){
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}