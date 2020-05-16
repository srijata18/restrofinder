package com.example.restrofinder.di.module

import com.example.restrofinder.networkClasses.UseCaseHandler
import com.example.restrofinder.networkClasses.retrofit.RetrofitClient
import com.example.restrofinder.networkClasses.retrofit.RetrofitServiceAnnotator
import com.example.restrofinder.dashboard.dataRepositories.DashboardDataRepository
import com.example.restrofinder.dashboard.dataRepositories.DashboardLocalRepository
import com.example.restrofinder.dashboard.dataRepositories.DashboardRemoteRepository
import com.example.restrofinder.dashboard.useCase.DummyUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    internal fun provideUseCaseHandler() = UseCaseHandler.getInstance()

    @Provides
    @Singleton
    internal fun provideRetrofitServiceAnnotator(): RetrofitServiceAnnotator = RetrofitClient.create()

    @Provides
    internal fun provideUseCase(dataRepository: DashboardDataRepository): DummyUseCase =
        DummyUseCase(dataRepository)

    @Provides
    internal fun provideRemoteData(retrofitServiceAnnotator: RetrofitServiceAnnotator) : DashboardRemoteRepository =
        DashboardRemoteRepository(
            retrofitServiceAnnotator
        )

    @Provides
    internal fun provideLocalData() : DashboardLocalRepository =
        DashboardLocalRepository()

    @Provides
    internal fun provideDataRepository(remoteRepository: DashboardRemoteRepository,
                                       localRepository: DashboardLocalRepository
    ) : DashboardDataRepository =
        DashboardDataRepository(
            remoteRepository,
            localRepository
        )

}