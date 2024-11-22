package com.solarsync.solarapp.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.solarsync.solarapp.R
import com.solarsync.solarapp.Screen
import com.solarsync.solarapp.ui.components.SolarSyncButton

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Distribuir os elementos uniformemente
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espaço para a logo
        Image(
            painter = painterResource(id = R.drawable.main),
            contentDescription = "Logo do SolarSync",
            modifier = Modifier
                .size(400.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Título principal
        Text(
            text = "Seja bem-vindo a SolarSync",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight(700)),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espaço entre o título e o texto descritivo

        // Texto descritivo
        Text(
            text = "Uma plataforma que conecta Clientes e Fornecedores em energia solar em prol da natureza sustentável",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) // Espaçamento lateral para centralizar o texto
        )

        Spacer(modifier = Modifier.height(96.dp))

        // Botão de Login
        SolarSyncButton(
            text = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            onClick = {
                navController.navigate(Screen.Login.route)
            }// Deixa o botão com espaçamento nas laterais
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre o botão de login e os botões de registro

        // Botões de registro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuir os botões lado a lado
        ) {
            SolarSyncButton(
                text = "Registrar Cliente",
                modifier = Modifier.weight(1f), // Cada botão ocupa metade do espaço
                onClick = {
                    navController.navigate(Screen.ClientRegister.route)
                }
            )
            Spacer(modifier = Modifier.width(10.dp)) // Espaço entre os botões
            SolarSyncButton(
                text = "Registrar Fornecedor",
                modifier = Modifier.weight(1f),
                onClick = {
                    navController.navigate(Screen.SupplierRegister.route)
                }
            )
        }
    }
}


