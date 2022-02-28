package com.example.mysqlretrofitrecycler.api

import com.example.moviedatabase.api.models.CategoriesApiModel
import com.example.moviedatabase.api.models.FilmsApiModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("insertCategory.php")
    fun insertCategory(
        @Field("name") name: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("insertFilm.php")
    fun insertFilm(
        @Field("name") name: String?,
        @Field("category") category: String?,
        @Field("duration") duration: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("updateCategory.php")
    fun updateCategory(
        @Field("id") id: Int,
        @Field("name") name: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("updateFilm.php")
    fun updateFilm(
        @Field("id") id: Int,
        @Field("name") name: String?,
        @Field("category") category: String?,
        @Field("duration") duration: String?
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("deleteCategory.php")
    fun deleteCategory(
        @Field("id") id: Int
    ): Call<ResponseBody?>?

    @FormUrlEncoded
    @POST("deleteFilm.php")
    fun deleteFilm(
        @Field("id") id: Int
    ): Call<ResponseBody?>?

    @DELETE("clearCategories.php")
    fun clearCategories(): Call<ResponseBody?>?

    @DELETE("clearFilms.php")
    fun clearFilms(): Call<ResponseBody?>?

    @GET("getCategory.php")
    fun getCategory(): Call<ArrayList<CategoriesApiModel>>

    @GET("getFilm.php")
    fun getFilm(): Call<ArrayList<FilmsApiModel>>

    @GET("getFilmFilter.php")
    fun getFilmFilter(@Query("category") category: String, @Query("duration") duration: String):
            Call<ArrayList<FilmsApiModel>>

}