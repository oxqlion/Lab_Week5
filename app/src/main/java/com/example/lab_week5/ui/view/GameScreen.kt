package com.example.lab_week5.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab_week5.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = 12.dp
    var guess by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(mediumPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Guess The Number",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(mediumPadding),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .clip(shapes.medium)
                            .background(colorScheme.surfaceTint)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .align(alignment = Alignment.End),
                        text = "Number of guesses: ${gameUiState.tries}",
                        style = typography.titleMedium,
                        color = colorScheme.onPrimary
                    )
                    Text(
                        text = gameUiState.randomNumber.toString(),
                        style = typography.displayMedium
                    )
                    Text(
                        text = "From 1 to 10 guess the number",
                        textAlign = TextAlign.Center,
                        style = typography.titleMedium
                    )
                    Text(
                        text = "Score : ${gameUiState.score}",
                        textAlign = TextAlign.Center,
                        style = typography.titleMedium
                    )
                    OutlinedTextField(
                        value = guess,
                        onValueChange = { guess = it },
                        singleLine = true,
                        shape = shapes.large,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedLabelColor = colorScheme.primary,
                            unfocusedLabelColor = colorScheme.primary,
                            disabledLabelColor = colorScheme.primary,
                        ),
                        label = { Text(text = "Enter your number") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { gameViewModel.checkUserGuess(guess.toInt()) }
                        )
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth().padding(mediumPadding),
                onClick = { gameViewModel.checkUserGuess(guess.toInt()) }
            ) {
                Text(
                    text = "Submit",
                    fontSize = 16.sp
                )
            }
        }

        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier,
    state: GameViewModel = viewModel()
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = "Welp!") },
        text = { Text(text = "You scored ${state.uiState.value.score}") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Play Again")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GameScreenPreview() {
    GameScreen()
}