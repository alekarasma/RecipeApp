package com.example.recipeapp.presentation.designs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CardLabel(modifier: Modifier = Modifier, label: String, labelValue: String, backgroundColor: Color = MaterialTheme.colorScheme.background) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor), modifier = Modifier
            .padding(0.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = labelValue,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

