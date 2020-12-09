package com.taiyilin.mandarinlearning.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.taiyilin.mandarinlearning.data.source.local.MandarinLearningLocalDataSource
import com.taiyilin.mandarinlearning.data.source.DefaultMandarinLearningRepository
import com.taiyilin.mandarinlearning.data.source.MandarinLearningDataSource
import com.taiyilin.mandarinlearning.data.source.remote.MandarinLearningRemoteDataSource
import com.taiyilin.mandarinlearning.data.source.MandarinLearningRepository


// A Service Locator for the [MandarinLearningRepository].
object ServiceLocator {

    @Volatile
    var repository: MandarinLearningRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): MandarinLearningRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createMandarinLearningRepository(
                    context
                )
        }
    }

    private fun createMandarinLearningRepository(context: Context): MandarinLearningRepository {
        return DefaultMandarinLearningRepository(
            MandarinLearningRemoteDataSource,
            createLocalDataSource(
                context
            )
        )
    }

    private fun createLocalDataSource(context: Context): MandarinLearningDataSource {
        return MandarinLearningLocalDataSource(
            context
        )
    }





}