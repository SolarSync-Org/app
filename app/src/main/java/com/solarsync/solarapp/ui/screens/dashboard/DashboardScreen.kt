package com.solarsync.solarapp.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solarsync.solarapp.ui.screens.profile.ProfileViewModel

@Composable
fun DashboardScreen(
    viewModel: ProfileViewModel = viewModel() // Reusando o ProfileViewModel para verificar o tipo de usuário
) {
    val userData by viewModel.userData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Verifica o tipo de usuário baseado nos dados
        if (userData?.containsKey("installationType") == true) {
            ClientDashboard()
        } else {
            SupplierDashboard()
        }
    }
}

@Composable
private fun ClientDashboard() {
    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Fornecedores Recomendados",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista estática de fornecedores
                    RecommendationItem(
                        name = "Solar Tech Solutions",
                        description = "Especialista em instalações residenciais",
                        rating = 4.8f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "EcoSolar Energia",
                        description = "Melhor custo-benefício da região",
                        rating = 4.5f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "Green Power Systems",
                        description = "Líder em instalações industriais",
                        rating = 4.7f
                    )
                    RecommendationItem(
                        name = "FIAP Energy",
                        description = "Maior rede de instalção de energia solar da vizinhança",
                        rating = 5.0f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "BlueSense",
                        description = "Busca por soluções sustentáveis para residências",
                        rating = 4.4f
                    )
                }
            }
        }
    }
}

@Composable
private fun SupplierDashboard() {
    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Clientes Potenciais",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista estática de clientes
                    RecommendationItem(
                        name = "Condomínio Solar Gardens",
                        description = "Busca instalação para 50 unidades",
                        rating = 4.9f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "Indústria MetalSol",
                        description = "Projeto para galpão industrial",
                        rating = 4.6f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "Residencial Park View",
                        description = "Interesse em energia solar para áreas comuns",
                        rating = 4.4f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "Raphinha",
                        description = "Busca por soluções sustentáveis para residências",
                        rating = 4.4f
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RecommendationItem(
                        name = "Bruno Guimarães",
                        description = "Busca por soluções sustentáveis para o seu galpão",
                        rating = 4.4f
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendationItem(
    name: String,
    description: String,
    rating: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = if (rating >= 4.7f) Icons.Default.Star else Icons.Default.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )

        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "Avaliação: $rating",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}