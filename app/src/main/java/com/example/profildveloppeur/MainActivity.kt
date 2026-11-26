
package com.example.profildveloppeur

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.profildveloppeur.ui.theme.ProfilDéveloppeurTheme
import android.widget.Toast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn


// Couleurs de l'application
private val Bleu = Color(0xFF1D4ED8)
private val Fond = Color(0xFFF1F5F9)

data class ProjetUi(
    val id: Int,
    val titre: String,
    val technologies: String,
    val description: String
)

fun sampleProjets(): List<ProjetUi> {
    return listOf(
        ProjetUi(
            id = 1,
            titre = "Product Explorer",
            technologies = "Kotlin, Jetpack Compose",
            description = "Application Android de présentation d'un catalogue de produits."
        ),
        ProjetUi(
            id = 2,
            titre = "Profil Développeur",
            technologies = "Kotlin, Jetpack Compose",
            description = "Application mobile présentant un profil développeur et ses compétences."
        ),
        ProjetUi(
            id = 3,
            titre = "Développement Odoo",
            technologies = "Python, PostgreSQL",
            description = "Développement et maintenance de modules pour un ERP."
        )
    )
}
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProfilDéveloppeurTheme {

                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    ProfilScreen(
                        nom = "Zakia Kadi",
                        role = "Développeuse junior",
                        description = "Étudiante en informatique, passionnée par le développement logiciel, les applications mobiles et les systèmes d'information.",
                        competences = listOf(
                            "Java",
                            "Kotlin",
                            "Python",
                            "SQL",
                            "HTML / CSS",
                            "Git / GitHub"
                        ),
                        email = "z.kadi@myskolae.fr",
                        onContactClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:z.kadi@myskolae.fr")
                            }

                            try {
                                context.startActivity(intent)
                            } catch (e: android.content.ActivityNotFoundException) {
                                Toast.makeText(
                                    context,
                                    "Aucune application de messagerie disponible",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Avatar(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(
            id = android.R.drawable.ic_menu_myplaces
        ),
        contentDescription = "Avatar du développeur",
        modifier = modifier
            .background(Color.White, CircleShape)
            .padding(12.dp)
    )
}

// Écran principal du profil
@Composable
fun ProfilScreen(
    nom: String,
    role: String,
    description: String,
    competences: List<String>,
    email: String,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier,
    projets: List<ProjetUi> = sampleProjets()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Fond
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProfilHeader(
                    nom = nom,
                    role = role
                )
            }

            item {
                DescriptionSection(
                    description = description
                )
            }

            item {
                CompetencesSection(
                    competences = competences
                )
            }

            item {
                TitreSection(
                    titre = "Projets et expériences"
                )
            }

            items(
                items = projets,
                key = { projet -> projet.id }
            ) { projet ->
                ProjetCard(
                    projet = projet
                )
            }

            item {
                ContactSection(
                    email = email,
                    onContactClick = onContactClick
                )
            }
        }
    }
}


// En-tête
@Composable
fun ProfilHeader(
    nom: String,
    role: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Bleu
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(80.dp)
            ) {
                Avatar(
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(20.dp)
                        .background(Color.Green, CircleShape)
                        .border(3.dp, Color.White, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = nom,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = role,
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}

// A propos
@Composable
fun DescriptionSection(
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            TitreSection(titre = "À propos")

            Text(
                text = description,
                fontSize = 15.sp,
                color = Color.DarkGray
            )
        }
    }
}

// Compétences
@Composable
fun CompetencesSection(
    competences: List<String>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            TitreSection(titre = "Compétences")

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(competences) { competence ->
                    CompetenceCard(
                        competence = competence
                    )
                }
            }
        }
    }
}

// Contact
@Composable
fun ContactSection(
    email: String,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TitreSection(titre = "Contact")

            Text(
                text = email,
                color = Color.DarkGray
            )

            Button(
                onClick = onContactClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Envoyer un email à Zakia Kadi"
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Bleu
                )
            ) {
                Text(text = "Me contacter")
            }
        }
    }
}

// Titre réutilisable pour les sections
@Composable
fun TitreSection(
    titre: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = titre,
        modifier = modifier.padding(bottom = 10.dp),
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold,
        color = Bleu
    )
}

// Première preview : profil personnel
@Preview(
    showBackground = true,
    name = "Profil Zakia"
)
@Composable
fun ProfilPreview() {
    ProfilDéveloppeurTheme {
        ProfilScreen(
            nom = "Zakia Kadi",
            role = "Développeuse junior",
            description = "Étudiante en informatique, passionnée par le développement logiciel et les systèmes d'information.",
            competences = listOf(
                "Java",
                "Kotlin",
                "Python",
                "SQL",
                "Git"
            ),
            email = "z.kadi@myskolae.fr",
            onContactClick = {}
        )
    }
}
// Deuxième preview : autre profil
@Preview(
    showBackground = true,
    name = "Profil Android"
)
@Composable
fun ProfilAndroidPreview() {
    ProfilDéveloppeurTheme {
        ProfilScreen(
            nom = "Alex Martin",
            role = "Développeur Android",
            description = "Passionné par le développement d'applications Android modernes.",
            competences = listOf(
                "Kotlin",
                "Jetpack Compose",
                "Git"
            ),
            email = "alex@gmail.com",
            onContactClick = {}
        )
    }
}
@Composable
fun CompetenceCard(
    competence: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Bleu
        )
    ) {
        Text(
            text = competence,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            color = Color.White
        )
    }
}
@Composable
fun ProjetCard(
    projet: ProjetUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = projet.titre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Bleu
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = projet.technologies,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = projet.description,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}