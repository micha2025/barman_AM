package com.example.barman

import android.content.ClipData.Item
import android.os.CountDownTimer
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun ListPage() {
    val drinks = getFakeDrinks()

    // State to manage the visibility of details for each drink
    val expandedDrinkId = remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        LazyColumn {
            itemsIndexed(drinks) { index: Int, item: Drink ->
                DrinkItem(
                    item = item,
                    isExpanded = expandedDrinkId.value == item.Id,
                    onInfoClick = {
                        expandedDrinkId.value = if (expandedDrinkId.value == item.Id) null else item.Id
                    }
                )
            }
        }
    }
}

@Composable
fun DrinkItem(item: Drink, isExpanded: Boolean, onInfoClick: () -> Unit) {
    var timeLeft by remember { mutableStateOf(item.time) }  // Start timer with drink's time
    var timerRunning by remember { mutableStateOf(false) }

    // Timer logic using LaunchedEffect
    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft -= 1
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = item.name)
            }

            IconButton(onClick = {
                onInfoClick()
                // Start timer when info button is clicked
                if (!timerRunning) {
                    timerRunning = true
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_info_24),
                    contentDescription = "Info",
                    tint = Color.White
                )
            }
        }

        // Display additional details only when isExpanded is true
        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Text(text = "Description: ${item.drinkDescription}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Preparation Time: ${item.time} minutes", style = MaterialTheme.typography.bodyLarge)

                // Display the countdown timer when expanded
                if (timerRunning) {
                    Text(text = "Time Left: $timeLeft seconds", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}







