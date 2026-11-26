package com.example.profildveloppeur

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        color = MaterialTheme.colorScheme.background
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
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primary
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
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = role,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
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
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            TitreSection(titre = "À propos")

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
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
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
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
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TitreSection(titre = "Contact")

            OutlinedTextField(
                value = email,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(text = "Adresse e-mail")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onContactClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            id = android.R.drawable.ic_dialog_email
                        ),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "Me contacter")
                }
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
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(
    showBackground = true,
    name = "Profil - Theme clair",
    heightDp = 1200
)
@Composable
fun ProfilLightPreview() {
    ProfilDéveloppeurTheme(darkTheme = false) {
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
            onContactClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Profil - Theme sombre",
    heightDp = 1200
)
@Composable
fun ProfilDarkPreview() {
    ProfilDéveloppeurTheme(darkTheme = true) {
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
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = competence,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
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
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = projet.technologies,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = projet.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}