package com.example.talestogether

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

// Dados de postagens dos pets
data class PetPost(
    val name: String,
    val breed: String,
    val age: Int,
    val text: String,
    val likes: Int,
    val reposts: Int,
    val timestamp: Long
)

val posts = listOf(
    PetPost("Luna", "Golden Retriever", 3, "Hoje brinquei no parque! 🐾", 12, 3, System.currentTimeMillis() - 600000),
    PetPost("Mimi", "Gatinha Siamesa", 1, "Dormir no sol é vida... ☀️😸", 25, 5, System.currentTimeMillis() - 3600000),
    PetPost("Rex", "Pastor Alemão", 4, "Aprendi um novo truque hoje! 🎾", 30, 7, System.currentTimeMillis() - 7200000),
    PetPost("Thor", "Bulldog", 2, "Hora do banho... 😅", 8, 2, System.currentTimeMillis() - 1800000),
    PetPost("Bella", "Labrador", 5, "Passeio na praia foi incrível! 🌊", 40, 10, System.currentTimeMillis() - 5400000)
)

// Paleta pastel moderna
private val PastelColorScheme = lightColorScheme(
    primary = Color(0xFFB39DDB),        // Roxo pastel
    onPrimary = Color.White,
    secondary = Color(0xFF81D4FA),      // Azul pastel
    onSecondary = Color.Black,
    background = Color(0xFFFDFDFD),     // Fundo claro
    onBackground = Color.Black,
    surface = Color(0xFFEDE7F6),        // Cards
    onSurface = Color.Black
)

// Formata horário
fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

// Card estilo Threads moderno pastel
@Composable
fun PostCard(post: PetPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp)
        ) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("${post.name}, ${post.age} anos", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(post.breed, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(formatTime(post.timestamp), fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(post.text, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Button(onClick = { println("Curtido ${post.name}") }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("💜 ${post.likes}", color = MaterialTheme.colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { println("Comentado ${post.name}") }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Text("💬 Comentar", color = MaterialTheme.colorScheme.onSecondary)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { println("Repostado ${post.name}") }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("🔁 ${post.reposts}", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

// Feed rolável
@Composable
fun Feed(postList: List<PetPost>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(postList) { post ->
            PostCard(post)
        }
    }
}

// Tela principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(openProfile: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TailTogether 🐾") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PastelColorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { openProfile() }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Perfil",
                            tint = PastelColorScheme.onPrimary
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = PastelColorScheme.primary) {
                IconButton(onClick = { /* ação Home */ }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = PastelColorScheme.onPrimary)
                }
            }
        }
    ) { innerPadding ->
        Feed(
            postList = posts,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

// MainActivity
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = PastelColorScheme) {
                FeedScreen(openProfile = {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                })
            }
        }
    }
}
