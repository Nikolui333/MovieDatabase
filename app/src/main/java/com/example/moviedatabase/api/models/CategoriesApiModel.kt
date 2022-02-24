package com.example.moviedatabase.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoriesApiModel (
    @SerializedName("id") @Expose
    var id: Int? = null,
    @SerializedName("name") @Expose
    var name: String? = null
)