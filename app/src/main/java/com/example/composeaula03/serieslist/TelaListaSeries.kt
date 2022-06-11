package com.example.composeaula03.serieslist

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeaula03.data.Serie

@Composable
fun TelaListaDeSeries(
    navController: NavController,
    listaSerieViewModel: ListaSerieViewModel
    ) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addeditserie")
            }){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Série")
            }
        }
    ) {
        val listaSerie by listaSerieViewModel.listaSerie.observeAsState(listOf())
        val filtro by listaSerieViewModel.filtrarPor.observeAsState("")

        Column() {
            BuscarSerie(filtro, listaSerieViewModel::atualizarFiltro)
            ListaDeSeries(series = listaSerie, navController = navController)
        }
    }
}

@Composable
fun BuscarSerie(
    filtro: String,
    aoMudarFiltro: (String) -> Unit
){

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        label = {
            Row() {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                Text(text = "Buscar")
            }
        },
        value = filtro,
        onValueChange = aoMudarFiltro)

}

@Composable
fun ListaDeSeries(
    series: List<Serie>,
    navController: NavController
){
    LazyColumn(){
        items(series){ serie ->
            EntradaDeSerie(serie = serie){
                navController.navigate("addeditserie?id=${serie.id}")
            }
        }
    }
}

@Composable
fun EntradaDeSerie(
    serie: Serie,
    aoEditar: () -> Unit
){

    var expanded by remember { mutableStateOf(false)}

    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                expanded = !expanded
            }
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column() {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .size(60.dp)
                        .background(com.example.composeaula03.ui.theme.Green),

                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = "${serie.nome[0].uppercase()}",
                        style = MaterialTheme.typography.h3
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = serie.nome,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
                if(expanded){
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(32.dp)
                            .clickable {
                                aoEditar()
                            },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                if (expanded){
                    Text(
                        modifier = Modifier.padding(start = 6.dp, bottom = 6.dp),
                        text = "Temporada: " + serie.temporada,
                        style = MaterialTheme.typography.subtitle2.copy(color = Color.LightGray)
                    )
                    Text(
                        text = "Episódio: " + serie.episodio,
                        style = MaterialTheme.typography.subtitle2.copy(color = Color.LightGray),
                        modifier = Modifier.padding(start = 50.dp)
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {

                if (expanded){
                    Text(
                        modifier = Modifier.padding(start = 6.dp, bottom = 6.dp, end = 6.dp),
                        text = "Pausado em: "
                                + serie.tempoPausado,
                        style = MaterialTheme.typography.subtitle2.copy(color = Color.LightGray)
                    )
                }

            }
            
        }
    }
}

//@Preview
//@Composable
//fun PreviewTelaListaDeSeries() {
//    val viewModel: ListaSerieViewModel = viewModel()
//    TelaListaDeSeries(rememberNavController(), viewModel)
//}
//
//@Preview
//@Composable
//fun PreviewListaDeSeries() {
//    val viewModel: ListaSerieViewModel = viewModel()
//    val listaSerie by viewModel.listaSerie.observeAsState()
//    ListaDeSeries(series = listaSerie ?: listOf())
//}
//
//@Preview
//@Composable
//fun PreviewEntradaDeSerie() {
//    EntradaDeSerie(
//        Serie(
//            0,
//            "Vikings",
//            "1",
//            "9",
//            "00:26:45",
//        )
//    )
//}