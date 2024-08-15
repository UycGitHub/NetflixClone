package com.umutyusufcinar.netflixclone.network

import com.umutyusufcinar.netflixclone.network.model.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type,
                     array: Array<Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {

        //Call <NetworkResponse<MovieResponseDTO, ErrorResponse>>
        if(Call::class.java != getRawType(returnType)){
            return null
        }
        check(returnType is ParameterizedType){
            "return type must be parameterized"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != NetworkResponse::class.java){
            return null
        }

        check(responseType is ParameterizedType){
            "Response must be parameterized"
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        val errorBodyType = getParameterUpperBound(1, responseType)

        val errorBodyConverter= retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, array)

        return NetworkResponseAdapter<Any, Any>(successBodyType, errorBodyConverter)

    }

}