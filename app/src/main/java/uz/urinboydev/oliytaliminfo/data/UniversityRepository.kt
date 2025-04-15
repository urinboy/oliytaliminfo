package uz.urinboydev.oliytaliminfo.data

import android.content.Context
import uz.urinboydev.oliytaliminfo.model.University
import uz.urinboydev.oliytaliminfo.utils.JsonUtils

class UniversityRepository(private val context: Context) {

    fun getUniversities(): List<University> {
        return JsonUtils.getUniversitiesFromJson(context)?.universities ?: emptyList()
    }

    fun getUniversityById(id: Int): University? {
        return getUniversities().find { it.id == id }
    }

    fun getUniversitiesByRegion(region: String): List<University> {
        return getUniversities().filter { it.region == region }
    }

    fun getUniversitiesByOwnershipType(type: String): List<University> {
        return getUniversities().filter { it.ownershipType == type }
    }
}