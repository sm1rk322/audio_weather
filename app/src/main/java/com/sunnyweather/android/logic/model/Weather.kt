package com.sunnyweather.android.logic.model
//封装Realtime和Daliy对象
data class Weather(val realtime:RealtimeResponsse.Realtime,val daily:DailyResponse.Daily) {
}