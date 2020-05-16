package com.example.restrofinder.dashboard.di

import com.example.restrofinder.networkClasses.UseCaseHandler
import com.example.restrofinder.dashboard.useCase.DummyUseCase
import com.example.restrofinder.dashboard.viewModel.DashboardViewModel
import dagger.Module
import dagger.Provides

@Module
class DashboardActivityModule {

    @Provides
    fun provideMainViewModel(useCaseHandler: UseCaseHandler, dummyUseCase: DummyUseCase): DashboardViewModel {
        return DashboardViewModel(
            useCaseHandler,
            dummyUseCase
        )
    }

}