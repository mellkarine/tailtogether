package com.example.myapplication

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
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
    PetPost("Luna", "Golden Retriever", 3, "Hoje brinquei no parque!", 12, 3, System.currentTimeMillis() - 600000),
    PetPost("Mimi", "Gatinha Siamesa", 1, "Dormir no sol é vida...", 25, 5, System.currentTimeMillis() - 3600000),
    PetPost("Rex", "Pastor Alemão", 4, "Aprendi um novo truque hoje!", 30, 7, System.currentTimeMillis() - 7200000),
    PetPost("Thor", "Bulldog", 2, "Hora do banho...", 8, 2, System.currentTimeMillis() - 1800000),
    PetPost("Bella", "Labrador", 5, "Passeio na praia foi incrível!", 40, 10, System.currentTimeMillis() - 5400000)
)

// Paleta com roxo pastel
private val PastelColorScheme = lightColorScheme(
    primary = Color(0xFFB39DDB),
    onPrimary = Color.White,
    background = Color(0xFFFDFDFD),
    onBackground = Color.Black,
    surface = Color(0xFFEDE7F6),
    onSurface = Color.Black
)

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun PostCard(post: PetPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
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
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("${post.name}, ${post.age} anos", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(post.breed, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
                Spacer(Modifier.weight(1f))
                Text(formatTime(post.timestamp), fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(Modifier.height(8.dp))

            Text(post.text, color = MaterialTheme.colorScheme.onSurface)

            Spacer(Modifier.height(12.dp))

            Row {
                Button(
                    onClick = { println("Curtido ${post.name}") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = "Curtir", tint = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(4.dp))
                    Text("${post.likes}", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { println("Comentado ${post.name}") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.MailOutline, contentDescription = "Comentar", tint = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(4.dp))
                    Text("Comentar", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { println("Repostado ${post.name}") },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Repostar", tint = MaterialTheme.colorScheme.onPrimary)
                    Spacer(Modifier.width(4.dp))
                    Text("${post.reposts}", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

@Composable
fun Feed(postList: List<PetPost>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(postList) { PostCard(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(openProfile: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TailTogether") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PastelColorScheme.primary),
                actions = {
                    IconButton(onClick = openProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = PastelColorScheme.onPrimary)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = PastelColorScheme.primary) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = PastelColorScheme.onPrimary)
                }
            }
        }
    ) { padding ->
        Feed(posts, Modifier.fillMaxSize().padding(padding))
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = PastelColorScheme) {
                FeedScreen(openProfile = {
                    startActivity(Intent(this, Profile::class.java))
                })
            }
        }
    }
}
