package com.example.restrofinder.di.component

import com.example.restrofinder.dashboard.views.DashBoardActivity
import com.example.restrofinder.dashboard.di.DashboardActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [DashboardActivityModule::class])
    internal abstract fun bindMainActivity(): DashBoardActivity


}