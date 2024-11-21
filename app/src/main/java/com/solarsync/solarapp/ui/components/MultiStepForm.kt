package com.solarsync.solarapp.ui.components


import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultiStepForm(
    steps: List<@Composable () -> Unit>, // Lista de etapas
    onFinish: () -> Unit // Callback ao terminar o formulário
) {
    var currentStep by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra de progresso
        LinearProgressIndicator(
            progress = (currentStep + 1) / steps.size.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Conteúdo da etapa atual
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            steps[currentStep]()
        }

        // Botões de navegação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStep > 0) {
                Button(onClick = { currentStep-- }) {
                    Text("Voltar")
                }
            }
            Button(onClick = {
                if (currentStep < steps.size - 1) {
                    currentStep++
                } else {
                    onFinish()
                }
            }) {
                Text(if (currentStep == steps.size - 1) "Finalizar" else "Próximo")
            }
        }
    }
}


