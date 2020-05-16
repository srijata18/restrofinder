package com.example.restrofinder.dashboard.useCase

import com.example.restrofinder.networkClasses.UseCase
import com.example.restrofinder.dashboard.dataRepositories.DashboardDataRepository

class DummyUseCase(private val dataRepository: DashboardDataRepository) :
    UseCase<DummyUseCase.RequestValues, DummyUseCase.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        dataRepository.getVariantDetails(
            object :
                IDataSource.getDetails {
                override fun onPostSuccess(response: Any) {
                    val res =
                        ResponseValue(
                            response
                        )
                    useCaseCallback?.onSuccess(res)
                }

                override fun onPostFailure(errorMsg: String, code: Int?) {
                    useCaseCallback?.onError(errorMsg, code)
                }
            }
        )
    }

    class RequestValues :
        UseCase.RequestValues

    class ResponseValue(val response: Any) : UseCase.ResponseValue

}