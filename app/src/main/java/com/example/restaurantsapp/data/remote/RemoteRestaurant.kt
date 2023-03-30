package com.example.restaurantsapp.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteRestaurant(
    // serialization to make sure each of the Json key's values match each of our corresponding fields
    // inside the data class and also matching the data type
    @SerializedName("r_id")
    val id: Int,
    @SerializedName("r_title")
    val title: String,
    @SerializedName("r_description")
    val description: String
) {

}