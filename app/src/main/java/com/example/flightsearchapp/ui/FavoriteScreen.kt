package com.example.flightsearchapp.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.Favorite



@Composable
fun FavoriteListScreen(
    favorites: List<Favorite>,
    onDeleteAction: ((String,String) -> Unit)? = null,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
        ) {
            items(
                items = favorites,
                key = { favorite -> favorite.id }
            ) {
                FavoriteCard(
                    it.departureCode,
                    it.destinationCode,
                    onDeleteAction,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun FavoriteCard(
    iataCodeDpt: String,
    iataCodeArr: String,
    onDeleteAction: ((String,String) -> Unit)? = null,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onDeleteAction?.invoke(iataCodeDpt, iataCodeArr)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Column(
                modifier
                    .weight(1f)
            ) {
                Airport("DEPART", iataCodeDpt, Modifier.padding(5.dp))
                Airport("ARRIVE", iataCodeArr, Modifier.padding(5.dp))
            }
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "favorite",
                tint = Color.Red
            )
        }
    }
}

@Composable
fun Airport(direction: String, iataCode: String, modifier: Modifier = Modifier){
    Column(modifier) {
        Text(text = direction, style = MaterialTheme.typography.bodyMedium, modifier = Modifier)
        Text(
            text = iataCode,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}


