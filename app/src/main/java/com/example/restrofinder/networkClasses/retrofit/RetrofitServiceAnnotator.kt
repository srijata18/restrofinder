package com.example.restrofinder.networkClasses.retrofit

import com.example.restrofinder.dashboard.dataModel.RestroModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitServiceAnnotator {

    @GET
    fun doGetRequest(@Url url : String) : Call<RestroModel>

}