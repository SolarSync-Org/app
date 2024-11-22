package com.solarsync.solarapp.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
    onSignOut: () -> Unit
) {
    val userData by viewModel.userData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val aboutMe by viewModel.aboutMe.collectAsState()

    var isEditingAboutMe by remember { mutableStateOf(false) }
    var editedAboutMe by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            userData?.let { data: Map<String, Any> ->
                // Profile Image
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Info
                Text(
                    text = data["name"] as? String ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // User Type Specific Info
                if (data.containsKey("installationType")) {
                    // Client specific info
                    ProfileInfoItem(
                        icon = Icons.Default.Home,
                        label = "Tipo de Instalação",
                        value = data["installationType"] as? String ?: ""
                    )
                    ProfileInfoItem(
                        icon = Icons.Default.LocationOn,
                        label = "Localização",
                        value = data["city"] as? String ?: ""
                    )
                } else {
                    // Supplier specific info
                    ProfileInfoItem(
                        icon = Icons.Default.Build,
                        label = "Soluções Disponíveis",
                        value = data["solutionType"] as? String ?: ""
                    )
                    ProfileInfoItem(
                        icon = Icons.Default.Place,
                        label = "Área de Atuação",
                        value = data["operationArea"] as? String ?: ""
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // About Me Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sobre Mim",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (!isEditingAboutMe) {
                                Row {
                                    IconButton(onClick = {
                                        isEditingAboutMe = true
                                        editedAboutMe = aboutMe
                                    }) {
                                        Icon(Icons.Default.Edit, "Editar")
                                    }
                                    IconButton(onClick = {
                                        viewModel.deleteAboutMe(
                                            onSuccess = { /* Pode adicionar um Toast aqui se quiser */ },
                                            onError = { /* Handle error */ }
                                        )
                                    }) {
                                        Icon(Icons.Default.Delete, "Excluir")
                                    }
                                }
                            }
                        }

                        if (isEditingAboutMe) {
                            OutlinedTextField(
                                value = editedAboutMe,
                                onValueChange = { editedAboutMe = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                maxLines = 5
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { isEditingAboutMe = false }) {
                                    Text("Cancelar")
                                }
                                Button(
                                    onClick = {
                                        viewModel.updateAboutMe(
                                            editedAboutMe,
                                            onSuccess = { isEditingAboutMe = false },
                                            onError = { /* Handle error */ }
                                        )
                                    },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text("Salvar")
                                }
                            }
                        } else {
                            Text(
                                text = aboutMe.ifEmpty { "Adicione uma descrição sobre você..." },
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (aboutMe.isEmpty()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Logout Button
                Button(
                    onClick = {
                        viewModel.signOut(onComplete ={
                            onSignOut()
                        })
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sair")
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}