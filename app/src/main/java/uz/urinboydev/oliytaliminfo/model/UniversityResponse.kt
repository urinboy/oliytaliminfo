package uz.urinboydev.oliytaliminfo.model

import com.google.gson.annotations.SerializedName

data class UniversityResponse(
    @SerializedName("oliy_talim_muassasalari")
    val universities: List<University>
)