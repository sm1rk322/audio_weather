package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place){
        sharedPreferences().edit {
            putString("place",Gson().toJson(place)) //转换成JSON存储起来
        }
    }

    fun getSavedPlace():Place{
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java) //Gson 转换Place类型
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",
        Context.MODE_PRIVATE) //获得全局上下文的shared

}
