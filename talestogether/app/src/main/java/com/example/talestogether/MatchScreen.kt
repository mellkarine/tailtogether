package com.example.talestogether

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- MODELO ----------
data class MatchPet(
    val name: String,
    val breed: String,
    val age: Int
)

val matchPets = listOf(
    MatchPet("Luna", "Golden Retriever", 3),
    MatchPet("Mimi", "Gatinha Siamesa", 1),
    MatchPet("Rex", "Pastor Alemão", 4),
    MatchPet("Thor", "Bulldog", 2),
    MatchPet("Bella", "Labrador", 5)
)

// ---------- PALETA DE CORES ----------
val PastelColorScheme = lightColorScheme(
    primary = Color(0xFF90CAF9),       // Azul claro
    onPrimary = Color.White,
    secondary = Color(0xFFF48FB1),     // Rosa
    onSecondary = Color.White,
    background = Color(0xFFF1F8E9),    // Verde bem claro
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

// ---------- ACTIVITY PRINCIPAL DA TELA DE MATCH ----------
class MatchScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = PastelColorScheme) {
                MatchScreenContent(
                    onNavigateHome = { startActivity(Intent(this, MainActivity::class.java)) },
                    onNavigateProfile = { startActivity(Intent(this, ProfileActivity::class.java)) }
                )
            }
        }
    }
}

// ---------- COMPOSABLE DA TELA ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreenContent(
    onNavigateHome: () -> Unit,
    onNavigateProfile: () -> Unit
) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Matchs de Pets") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PastelColorScheme.primary),
                actions = {
                    IconButton(onClick = onNavigateProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = PastelColorScheme.onPrimary)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = PastelColorScheme.primary) {
                // Ícone Home
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = PastelColorScheme.onPrimary)
                }
                // Ícone Match ao lado
                IconButton(onClick = {
                    Toast.makeText(context, "Você já está na tela de Match!", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Favorite, contentDescription = "Match", tint = PastelColorScheme.onPrimary)
                }
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(PastelColorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Campo de pesquisa
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Buscar pets...") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        Toast.makeText(context, "Buscando: $searchText", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Lista filtrada
                val filteredPets = matchPets.filter { it.name.contains(searchText, ignoreCase = true) }

                LazyColumn {
                    items(filteredPets) { pet ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(PastelColorScheme.primary)
                                )

                                Spacer(Modifier.width(12.dp))

                                Column(Modifier.weight(1f)) {
                                    Text("${pet.name}, ${pet.age} anos", fontSize = 18.sp, color = PastelColorScheme.onSurface)
                                    Text(pet.breed, fontSize = 14.sp, color = Color.Gray)
                                }

                                Button(
                                    onClick = {
                                        Toast.makeText(context, "Você deu match com ${pet.name}!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = PastelColorScheme.primary)
                                ) {
                                    Icon(Icons.Default.Favorite, contentDescription = "Match", tint = PastelColorScheme.onPrimary)
                                    Spacer(Modifier.width(4.dp))
                                    Text("Match", color = PastelColorScheme.onPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ---------- ACTIVITY DE PERFIL (PARA NÃO DAR CRASH) ----------
class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = PastelColorScheme) {
                Surface(modifier = Modifier.fillMaxSize(), color = PastelColorScheme.background) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text("Tela de Perfil", fontSize = 24.sp, color = PastelColorScheme.onBackground)
                    }
                }
            }
        }
    }
}
