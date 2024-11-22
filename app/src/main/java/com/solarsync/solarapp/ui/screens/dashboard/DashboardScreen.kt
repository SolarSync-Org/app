//package com.solarsync.solarapp.ui.screens.dashboard
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.google.firebase.auth.FirebaseAuth
//import com.solarsync.solarapp.ui.screens.profile.ProfileScreen
//
//@Composable
//fun DashboardScreen(
//    viewModel: DashboardViewModel = viewModel()
//) {
//    var selectedTabIndex by remember { mutableStateOf(0) }
//    val tabs = listOf("Dashboard", "Perfil")
//
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                tabs.forEachIndexed { index, title ->
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                imageVector = when (index) {
//                                    0 -> Icons.Default.Dashboard
//                                    else -> Icons.Default.Person
//                                },
//                                contentDescription = title
//                            )
//                        },
//                        label = { Text(title) },
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index }
//                    )
//                }
//            }
//        }
//    ) { padding ->
//        when (selectedTabIndex) {
//            0 -> DashboardContent(
//                modifier = Modifier.padding(padding)
//            )
//            1 -> ProfileScreen(
//                modifier = Modifier.padding(padding)
//            )
//        }
//    }
//}
//
//@Composable
//fun DashboardContent(modifier: Modifier = Modifier) {
//    val userType = FirebaseAuth.getInstance().currentUser?.let {
//        // Check user type from Firestore
//    }
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Dashboard",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 24.dp)
//        )
//
//        // Different content for client and provider
//        when (userType) {
//            "client" -> ClientDashboard()
//            "provider" -> SupplierDashboard()
//        }
//    }
//}
//
//@Composable
//fun ClientDashboard(clientData: ClientData?) {
//    clientData?.let { data ->
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Instalação Desejada",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = data.installationType,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "Consumo de Energia",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = "Consumo mensal: ${data.energyConsumption} kWh",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//                Text(
//                    text = "Valor mensal: R$ ${data.monthlyBill}",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "Localização",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = "${data.address}, ${data.city}",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun SupplierDashboard(providerData: SupplierData?) {
//    providerData?.let { data ->
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Solução Disponível",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = data.solutionType,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "Imóveis Preferenciais",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = data.preferredProperties.joinToString(", "),
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "Área de Atuação",
//                    style = MaterialTheme.typography.titleMedium
//                )
//                Text(
//                    text = data.operationArea,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        }
//    }
//}