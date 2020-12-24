package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object SunnyWeatherNetwork {

    private val placeService = ServiceCreator.create<PlaceService>() //搜索位置 动态代理接口
    private val weatherService = ServiceCreator.create<WeahterService>() //天气 动态代理接口

    suspend fun searchPlaces(query:String) = placeService.searchPlaces(query).await() //直接搜索位置数据

    suspend fun getDailyWeather(lng:String,lat:String) = weatherService.getDailyWeather(lng,lat).await()

    suspend fun getRealtimeWeather(lng:String,lat:String) = weatherService.getRealtimeWeather(lng,lat).await()

    private suspend fun <T> Call<T>.await() : T {
        return suspendCoroutine{ continuation ->
            enqueue(object : Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}