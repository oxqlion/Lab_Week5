package com.example.lab_week5.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab_week5.R
import com.example.lab_week5.model.IPKModel
import com.example.lab_week5.viewmodel.IPKViewModel

@Composable
fun IPKMainScreen(IPKState: IPKViewModel = viewModel()) {

    val state by IPKState.uiState.collectAsState()

    var sks by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }

    var matkul by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Courses",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Total SKS : ",
            fontSize = 18.sp
        )
        Text(
            text = "IPK : ",
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = sks,
                onValueChange = { sks = it },
                label = { Text(text = "SKS") },
                modifier = Modifier
                    .width(170.dp)
            )
            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                label = { Text(text = "Score") },
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = matkul,
                onValueChange = { matkul = it },
                label = { Text(text = "Name") },
                modifier = Modifier
                    .width(250.dp)
            )
            Button(
                onClick = { IPKState.addMatkul(sks.toInt(), score.toFloat(), matkul) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 8.dp),
            ) {
                Text(
                    text = "+",
                )
            }
        }
        LazyColumn {
            items(state) { matkul ->
                MatkulCard(model = IPKModel(), state = IPKState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatkulCard(model: IPKModel, state: IPKViewModel) {
    Card(
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(Color(0xfff1f3f4))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = model.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "SKS: ${model.sks}",
                    color = Color.Black
                )
                Text(
                    text = "Score: ${model.score}",
                    color = Color.Black
                )
            }

            Image(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                modifier = Modifier.clickable { state.delMatkul(model) }
            )
        }
    }
}
