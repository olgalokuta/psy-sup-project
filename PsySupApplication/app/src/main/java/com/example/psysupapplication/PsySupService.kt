package com.example.psysupapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
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

    @PUT("users")
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

interface PostAPI {
    @GET("posts")
    suspend fun getAllPosts() : List<Post>

    @GET("posts/user/{id}")
    suspend fun getUserPosts(
        @Path("id") id: Int
    ) : List<Post>

    @POST("posts")
    suspend fun createPost(
        @Body noIdPost : PostWithoutId
    ) : Post
}