package com.example.talestogether

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Pet(
    val name: String,
    val age: Int,
    val breed: String
)

val pets = listOf(
    Pet("Luna", 3, "Golden Retriever"),
    Pet("Mimi", 1, "Gatinha Siamesa"),
    Pet("Rex", 4, "Pastor Alemão")
)

@Composable
fun PetCard(pet: Pet, onMatchClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "${pet.name}, ${pet.age} anos",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = pet.breed,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onMatchClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dar Match 🐾")
            }
        }
    }
}

@Composable
fun PetFeed(petList: List<Pet>) {
    LazyColumn {
        items(petList) { pet ->
            PetCard(pet = pet, onMatchClick = {
                println("Match com ${pet.name} 🐶🐱")
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("PetMatch 🐾") }
                        )
                    }
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        PetFeed(pets)
                    }
                }
            }
        }
    }
}
