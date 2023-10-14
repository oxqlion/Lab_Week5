package com.example.lab_week5.model

data class GameUiState(
    var randomNumber: Int = 0,
    var input: Int = 0,
    var tries: Int = 0,
    var score: Int = 0,
    var isGameOver: Boolean = false
)