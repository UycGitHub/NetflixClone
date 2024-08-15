package com.umutyusufcinar.netflixclone.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResponseAdapter <S: Any, E: Any> (
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
): CallAdapter<S, Call<NetworkResponse<S, E>>> {
    override fun responseType(): Type {
        TODO("Not yet implemented") //burası başlık 9, sıra 10. videodan devam
    }

    override fun adapt(p0: Call<S>): Call<NetworkResponse<S, E>> {
        TODO("Not yet implemented")
    }

}