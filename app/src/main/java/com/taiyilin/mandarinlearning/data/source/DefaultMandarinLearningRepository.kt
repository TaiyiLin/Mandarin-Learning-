package com.taiyilin.mandarinlearning.data.source


// Concrete implementation to load Mandarin Learning sources.
class DefaultMandarinLearningRepository (private val remoteDataSource: MandarinLearningDataSource,
                                         private val localDataSource: MandarinLearningDataSource
): MandarinLearningRepository {

//    override suspend fun loginMockData(id: String): Result<Author> {
//        return localDataSource.login(id)
//    }
//
//    override suspend fun getArticles(): Result<List<Article>> {
//        return remoteDataSource.getArticles()
//    }
//
//    override fun getLiveArticles(): MutableLiveData<List<Article>> {
//        return remoteDataSource.getLiveArticles()
//    }
//
//    override suspend fun publish(article: Article): Result<Boolean> {
//        return remoteDataSource.publish(article)
//    }
//
//    override suspend fun delete(article: Article): Result<Boolean> {
//        return remoteDataSource.delete(article)
//    }


}