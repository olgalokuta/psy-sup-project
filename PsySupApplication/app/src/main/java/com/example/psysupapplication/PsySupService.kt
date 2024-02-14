package com.example.psysupapplication

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {
    @GET("users")
    suspend fun getAllUsers() : List<User>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ) : User

    @GET("users/name/{nickname}")
    suspend fun getUserByNick(
        @Path("nickname") id: String
    ) : User

    @POST("users")
    suspend fun createUser(
        @Body noIdUser : UserWithoutId
    ) : User

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body noIdUser : UserWithoutId
    ) : User

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ) //: Response<ResponseBody>
}

interface EntryAPI {
    @GET("posts")
    suspend fun getAllEntries() : List<Entry>

    @GET("posts/user/{id}")
    suspend fun getUserEntries(
        @Path("id") id: Int
    ) : List<Entry>

    @GET("posts/public")
    suspend fun getPublicEntries() : List<Entry>

    @POST("posts")
    suspend fun createEntry(
        @Body noIdEntry : EntryWithoutId
    ) : Entry

    @PUT("posts/{id}")
    suspend fun updateEntry(
        @Path("id") id: Int,
        @Body entry : Entry
    ) : Entry
}