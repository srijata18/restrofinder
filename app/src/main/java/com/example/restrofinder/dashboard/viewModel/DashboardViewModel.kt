package com.example.restrofinder.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.restrofinder.view.base.BaseViewModel
import com.example.restrofinder.networkClasses.ErrorModel
import com.example.restrofinder.networkClasses.UseCase
import com.example.restrofinder.networkClasses.UseCaseHandler
import com.example.restrofinder.dashboard.dataModel.RestroModel
import com.example.restrofinder.dashboard.useCase.DummyUseCase

class DashboardViewModel(
    override val useCaseHandler: UseCaseHandler,
    val dummyUseCase: DummyUseCase
) : BaseViewModel(useCaseHandler) {

    val receivedResponse = MutableLiveData<RestroModel>()
    val errorModel = MutableLiveData<ErrorModel>()

    fun fetchDetails(){
        val requestValue = DummyUseCase.RequestValues()
        useCaseHandler.execute(
            dummyUseCase, requestValue,
            object : UseCase.UseCaseCallback<DummyUseCase.ResponseValue>{
                override fun onSuccess(response: DummyUseCase.ResponseValue) {
                    if (response.response is RestroModel) {
                        receivedResponse.value = response.response
                    }
                }

                override fun onError(t: String, code: Int?) {
                    errorModel.value = ErrorModel(t,code)
                }
            }
        )
    }
}