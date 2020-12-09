package com.taiyilin.mandarinlearning
import android.app.Application
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository
import com.taiyilin.mandarinlearning.util.ServiceLocator
import kotlin.properties.Delegates


// An application that lazily provides a repository. Note that this Service Locator pattern is used to simplify the sample. Consider a Dependency Injection framework.
class MandarinLearningApplication :Application() {


    // Depends on the flavor,
    val repository: MandarinLearningRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: MandarinLearningApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLiveDataDesign() = true


}