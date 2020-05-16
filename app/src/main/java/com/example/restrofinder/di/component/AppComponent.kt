package com.example.restrofinder.di.component

import com.example.restrofinder.MyApplication
import com.example.restrofinder.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [(AndroidInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
@Singleton
abstract class AppComponent : AndroidInjector<MyApplication>{
    abstract override fun inject(instance: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder
        fun build(): AppComponent
    }
}