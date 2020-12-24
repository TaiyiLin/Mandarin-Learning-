package com.taiyilin.mandarinlearning.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.taiyilin.mandarinlearning.MandarinLearningApplication
import com.taiyilin.mandarinlearning.data.User

object UserManager {
    private const val USER_DATA = "user_data"
    private const val USER_UID = "user_uid"
    private const val USER_NAME = "user_name"

        private val _user = MutableLiveData<User>()
    //    val user: LiveData<Users>
    //        get() = _user

    var userUID: String? = null
        get() = MandarinLearningApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_UID, null)
        set(value) {
            field = when (value) {
                null -> {
                    MandarinLearningApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_UID)
                        .apply()
                    null
                }
                else -> {
                    MandarinLearningApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_UID, value)
                        .apply()
                    value
                }
            }
        }

    var userName: String? = null
        get() = MandarinLearningApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_NAME, null)
        set(value) {
            field = when (value) {
                null -> {
                    MandarinLearningApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_NAME)
                        .apply()
                    null
                }
                else -> {
                    MandarinLearningApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_NAME, value)
                        .apply()
                    value
                }
            }
        }
    //check Log In status
    val isLoggedIn: Boolean
        get() = userUID != null

    fun clear() {
        userUID = null
    }
}