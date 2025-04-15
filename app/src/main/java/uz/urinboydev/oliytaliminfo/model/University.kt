package uz.urinboydev.oliytaliminfo.model

import com.google.gson.annotations.SerializedName

data class University(
    val id: Int,
    @SerializedName("OTMnomi")
    val name: String,
    @SerializedName("Viloyati")
    val region: String,
    @SerializedName("Mulkchilikshakli")
    val ownershipType: String,
    @SerializedName("api")
    val websiteUrl: String
)