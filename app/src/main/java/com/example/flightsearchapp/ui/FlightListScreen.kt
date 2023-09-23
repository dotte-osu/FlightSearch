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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.ui.theme.FlightSearchAppTheme


@Composable
fun FlightListScreen(
    dptAirport: Airport,
    airports: List<Airport>,
    favorites: List<Favorite>,
    onDeleteAction: ((String,String) -> Unit)? = null,
    onAddAction: ((String,String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Flights from ${dptAirport.iataCode}",
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
        ) {
            items(
                items = airports,
                key = { airports -> airports.id }
            ) {
                val foundFavorite = favorites.find { f ->
                    f.departureCode == dptAirport.iataCode &&
                            f.destinationCode == it.iataCode }
                FlightCard(
                    iataCodeDpt = dptAirport.iataCode,
                    nameDpt = dptAirport.name,
                    iataCodeArr = it.iataCode,
                    nameArr = it.name,
                    isFavoriteFromParent = foundFavorite != null,
                    favorite = foundFavorite,
                    onDeleteAction = onDeleteAction,
                    onAddAction = onAddAction,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun FlightCard(
    iataCodeDpt: String,
    nameDpt: String,
    iataCodeArr: String,
    nameArr: String,
    isFavoriteFromParent: Boolean,
    favorite: Favorite?,
    onDeleteAction: ((String,String) -> Unit)? = null,
    onAddAction: ((String,String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(isFavoriteFromParent) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                Log.d("Log", "clicked")
                if (isFavorite) onDeleteAction?.invoke(iataCodeDpt, iataCodeArr)
                else onAddAction?.invoke(iataCodeDpt, iataCodeArr)
                isFavorite = !isFavorite
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier
                    .weight(1f)
            ) {
                Airport("DEPART", iataCodeDpt, nameDpt, Modifier.padding(5.dp))
                Airport("ARRIVE", iataCodeArr, nameArr, Modifier.padding(5.dp))
            }
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "favorite",
                tint = if(isFavorite) Color.Red else Color.Gray
            )
        }
    }
}

@Composable
fun Airport(drection: String, iataCode: String, name: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(text = drection, style = MaterialTheme.typography.bodyMedium, modifier = Modifier)
        Row {
            Text(
                text = iataCode,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(text = name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    FlightSearchAppTheme {
        Surface {
            //FlightCard("dpt", "dpt name", "arr", "arr name", false,{}, Modifier.padding(5.dp))
        }
    }
}