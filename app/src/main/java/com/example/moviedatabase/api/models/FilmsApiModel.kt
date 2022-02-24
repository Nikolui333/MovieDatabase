package com.example.moviedatabase.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FilmsApiModel (
    @SerializedName("id") @Expose
    var id: Int? = null,
    @SerializedName("name") @Expose
    var name: String? = null,
    @SerializedName("category") @Expose
    var category: String? = null,
    @SerializedName("duration") @Expose
    var duration: String? = null
)