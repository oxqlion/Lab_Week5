package com.example.lab_week5.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lab_week5.model.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess: Int by mutableStateOf(0)
        private set

    private var usedNum: MutableSet<Int> = mutableSetOf()

    init {
        resetGame()
    }

    fun resetGame() {
        usedNum.clear()
        val randNum = (1..10).random()
        _uiState.value = GameUiState(randomNumber = randNum)
    }

    fun updateUserGuess(guessedNum: Int) {
        userGuess = guessedNum
    }

    fun checkUserGuess(guess: Int) {
        if (guess == _uiState.value.randomNumber) {
            _uiState.update { currentState ->
                currentState.copy(
                    score = currentState.score + 1,
                    randomNumber = (1..10).random(),
                )
            }
            if (_uiState.value.score == 3) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isGameOver = true
                    )
                }
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(tries = currentState.tries + 1)
            }
            if (_uiState.value.tries == 3) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isGameOver = true
                    )
                }
            }
        }
        updateUserGuess(0)
    }
}