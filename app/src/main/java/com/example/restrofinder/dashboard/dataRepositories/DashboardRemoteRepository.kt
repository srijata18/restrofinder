package com.example.restrofinder.dashboard.dataRepositories

import android.util.Log
import com.example.restrofinder.dashboard.dataModel.RestroModel
import com.example.restrofinder.networkClasses.retrofit.RequestUrl
import com.example.restrofinder.networkClasses.retrofit.RetrofitServiceAnnotator
import com.example.restrofinder.dashboard.useCase.IDataSource
import com.example.restrofinder.utils.AppInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class DashboardRemoteRepository (private val retrofitServiceAnnotator: RetrofitServiceAnnotator):
    IDataSource {

    override fun getVariantDetails(callBack: IDataSource.getDetails) {
        val url = String.format("${RequestUrl.BASE_URL}${RequestUrl.DATA_URL}",AppInitializer.location)
        Log.i("location::url",url)
        val call = retrofitServiceAnnotator.doGetRequest(url)
        call.enqueue(object : Callback<RestroModel> {
            override fun onFailure(call: Call<RestroModel>, t: Throwable) {
                if (t is HttpException)
                    callBack.onPostFailure(t.message(),t.code())
                else
                    t.message?.let { callBack.onPostFailure(it) }

            }
            override fun onResponse(call: Call<RestroModel>, response: Response<RestroModel>) {
                if(response.body() != null && response.body() is RestroModel){
                    callBack.onPostSuccess(response.body() as RestroModel)
                }else{
                    callBack.onPostFailure(response.message(),response.code())
                }
            }
        })
    }

}