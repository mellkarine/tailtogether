package com.example.talestogether

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Paleta pastel moderna
private val PastelColorScheme = lightColorScheme(
    primary = Color(0xFFB39DDB),
    onPrimary = Color.White,
    secondary = Color(0xFF81D4FA),
    onSecondary = Color.Black,
    background = Color(0xFFFDFDFD),
    onBackground = Color.Black,
    surface = Color(0xFFEDE7F6),
    onSurface = Color.Black
)

// Posts do pet
val petPosts = listOf(
    "Hoje brinquei no parque! 🐾",
    "Dormir no sol é vida... ☀️😸",
    "Aprendi um novo truque hoje! 🎾"
)

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = PastelColorScheme.primary) {
                IconButton(onClick = {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = PastelColorScheme.onPrimary)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PastelColorScheme.background)
                .padding(innerPadding)
        ) {
            item {
                // Header degradê
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(PastelColorScheme.primary, PastelColorScheme.secondary)
                            )
                        )
                )

                // Avatar + botão Seguir
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .offset(y = (-40).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Button(
                        onClick = { /* ação seguir */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PastelColorScheme.primary)
                    ) {
                        Text("Seguir", color = PastelColorScheme.onPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Nome e usuário
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text("Luna", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PastelColorScheme.onBackground)
                    Text("@lunapet", fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Dados do pet: idade e raça
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Chip(text = "3 anos", color = PastelColorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Chip(text = "Golden Retriever", color = PastelColorScheme.secondary)
                    Spacer(modifier = Modifier.width(8.dp))
                    OwnerChip(ownerName = "Mell Karine")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Bio engraçadinha
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        "Ama bolinhas 🎾, sonecas ao sol ☀️ e biscoitos de queijo 🧀.",
                        fontSize = 14.sp,
                        color = PastelColorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Postagens",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PastelColorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Feed de postagens
            items(petPosts) { postText ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier
                        .background(PastelColorScheme.surface)
                        .padding(12.dp)
                    ) {
                        Text(postText, color = PastelColorScheme.onSurface)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Button(onClick = { println("Curtido!") }, colors = ButtonDefaults.buttonColors(containerColor = PastelColorScheme.primary)) {
                                Text("💜 Curtir", color = PastelColorScheme.onPrimary)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { println("Comentado!") }, colors = ButtonDefaults.buttonColors(containerColor = PastelColorScheme.secondary)) {
                                Text("💬 Comentar", color = PastelColorScheme.onSecondary)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { println("Repostado!") }, colors = ButtonDefaults.buttonColors(containerColor = PastelColorScheme.primary)) {
                                Text("🔁 Repostar", color = PastelColorScheme.onPrimary)
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

// Composable para chip
@Composable
fun Chip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color.copy(alpha = 0.3f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = color, fontWeight = FontWeight.Medium, fontSize = 12.sp)
    }
}

// Chip para dona do pet com ícone
@Composable
fun OwnerChip(ownerName: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(PastelColorScheme.secondary.copy(alpha = 0.3f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = "Dono(a)", tint = PastelColorScheme.secondary, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(ownerName, color = PastelColorScheme.secondary, fontWeight = FontWeight.Medium, fontSize = 12.sp)
        }
    }
}
