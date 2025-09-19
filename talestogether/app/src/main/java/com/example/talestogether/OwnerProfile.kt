package com.example.talestogether

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talestogether.ui.theme.PastelColorScheme
import com.example.talestogether.ui.theme.TalestogetherTheme
import androidx.compose.ui.graphics.vector.ImageVector

// Dados do dono
data class OwnerPost(
    val text: String,
    val likes: Int,
    val comments: Int,
    val reposts: Int
)

val ownerPosts = listOf(
    OwnerPost("Passeio no parque com Luna e Rex!", 15, 3, 1),
    OwnerPost("Novos brinquedos comprados hoje!", 10, 2, 0),
    OwnerPost("Sessão de fotos divertida com meus pets!", 20, 5, 2)
)

class OwnerProfile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalestogetherTheme {
                OwnerProfileScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerProfileScreen() {
    val context = LocalContext.current
    var newPost by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PastelColorScheme.primary)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = PastelColorScheme.primary) {
                IconButton(onClick = {
                    Toast.makeText(context, "Home clicked", Toast.LENGTH_SHORT).show()
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
                // Header com foto centralizada
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(PastelColorScheme.primary, PastelColorScheme.secondary)
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .align(Alignment.BottomCenter)
                            .offset(y = 50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // Nome e bio
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Mell Karine", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PastelColorScheme.onBackground)
                    Text("@mellkarine", fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Amante de pets, café e programação. Sempre compartilhando momentos com meus bichinhos.",
                        fontSize = 14.sp,
                        color = PastelColorScheme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Input de novo post
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newPost,
                        onValueChange = { newPost = it },
                        placeholder = { Text("O que você quer compartilhar?") },
                        modifier = Modifier.weight(1f) // Opcional, pode usar fillMaxWidth() também
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        Toast.makeText(context, "Post publicado: $newPost", Toast.LENGTH_SHORT).show()
                        newPost = ""
                    }) {
                        Text("Postar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Feed de postagens
            items(ownerPosts) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(PastelColorScheme.surface)
                            .padding(12.dp)
                    ) {
                        Text(post.text, color = PastelColorScheme.onSurface)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Botões lado a lado sem weight
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InteractionButton(icon = Icons.Default.Favorite, label = post.likes.toString())
                            InteractionButton(icon = Icons.Default.MailOutline, label = post.comments.toString())
                            InteractionButton(icon = Icons.Default.Share, label = post.reposts.toString())
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun InteractionButton(icon: ImageVector, label: String) {
    val context = LocalContext.current
    Button(
        onClick = { Toast.makeText(context, "$label clicado!", Toast.LENGTH_SHORT).show() },
        colors = ButtonDefaults.buttonColors(containerColor = PastelColorScheme.primary)
    ) {
        Icon(icon, contentDescription = label, tint = PastelColorScheme.onPrimary)
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, color = PastelColorScheme.onPrimary)
    }
}
