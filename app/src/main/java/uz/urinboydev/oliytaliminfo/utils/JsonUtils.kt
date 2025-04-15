package uz.urinboydev.oliytaliminfo.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.urinboydev.oliytaliminfo.model.UniversityResponse
import java.io.IOException

object JsonUtils {
    fun getUniversitiesFromJson(context: Context): UniversityResponse? {
        val jsonString = try {
            context.assets.open("universities.json").bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            null
        }

        return jsonString?.let {
            Gson().fromJson(it, UniversityResponse::class.java)
        }
    }
}