package uz.urinboydev.oliytaliminfo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.urinboydev.oliytaliminfo.data.UniversityRepository
import uz.urinboydev.oliytaliminfo.model.University

class UniversityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UniversityRepository(application)
    private val _universities = MutableLiveData<List<University>>()
    val universities: LiveData<List<University>> = _universities

    fun loadUniversities() {
        viewModelScope.launch {
            _universities.value = repository.getUniversities()
        }
    }

    fun filterByRegion(region: String) {
        viewModelScope.launch {
            _universities.value = repository.getUniversitiesByRegion(region)
        }
    }

    fun filterByOwnershipType(type: String) {
        viewModelScope.launch {
            _universities.value = repository.getUniversitiesByOwnershipType(type)
        }
    }

    fun searchUniversities(query: String) {
        viewModelScope.launch {
            _universities.value = repository.getUniversities()
                .filter { university ->
                    university.name.contains(query, ignoreCase = true) ||
                            university.region.contains(query, ignoreCase = true)
                }
        }
    }
}