package com.example.lab_week5.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lab_week5.model.IPKModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IPKViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<List<IPKModel>>(emptyList())
    val uiState: StateFlow<List<IPKModel>> = _uiState.asStateFlow()

    fun addMatkul(sks: Int, score: Float, name: String) {
        val newIPK = IPKModel(
            sks = sks,
            score= score,
            name = name
        )

        _uiState.value += newIPK
    }

    fun delMatkul(matkul: IPKModel){
        _uiState.value -= matkul
    }

}