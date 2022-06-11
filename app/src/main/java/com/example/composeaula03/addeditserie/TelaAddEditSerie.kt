package com.example.composeaula03.addeditserie

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composeaula03.data.Serie

@Composable
fun TelaAdicionarEditarLista(
    navController: NavController,
    addEditSerieListViewModel: AddEditSerieViewModel,
    aoInserirSerie: (Serie) -> Unit,
    aoAtualizarSerie: (Serie) -> Unit,
    aoRemoverSerie: (Int) -> Unit,
    serie: Serie
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                if (serie.id == -1)
                    addEditSerieListViewModel.inserirSerie(aoInserirSerie)
                else
                    addEditSerieListViewModel.atualizarSerie(
                        serie.id,
                        aoAtualizarSerie
                    )

                navController.navigate("serieslist") {
                    popUpTo("serieslist") {
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Adicionar")
            }
        }
    ) {

    }
    addEditSerieListViewModel.nome.value = serie.nome
    addEditSerieListViewModel.temporada.value = serie.temporada
    addEditSerieListViewModel.episodio.value = serie.episodio
    addEditSerieListViewModel.tempoPausado.value = serie.tempoPausado

    FormAdicionarEditarSerie(
        addEditSerieListViewModel,
        serie.id,
        aoRemoverSerie
    ) {
        navController.navigate("serieslist") {
            popUpTo("serieslist") {
                inclusive = true
            }
        }
    }
}
@Composable
fun FormAdicionarEditarSerie(
    addEditSerieListViewModel: AddEditSerieViewModel,
    id: Int,
    aoRemoverSerie: (Int) -> Unit,
    navigateBack: () -> Unit
){

    var nome = addEditSerieListViewModel.nome.observeAsState()
    var temporada = addEditSerieListViewModel.temporada.observeAsState()
    var episodio = addEditSerieListViewModel.episodio.observeAsState()
    var tempoPausado = addEditSerieListViewModel.tempoPausado.observeAsState()

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text(text = "Nome da série") },
                value = "${nome.value}",
                onValueChange = { novoNome ->
                    addEditSerieListViewModel.nome.value = novoNome
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text(text = "Temporada") },
                value = "${temporada.value}",
                onValueChange = { novaTemporada ->
                    addEditSerieListViewModel.temporada.value = novaTemporada
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text(text = "Episódio") },
                value = "${episodio.value}",
                onValueChange = { novoEpisodio ->
                    addEditSerieListViewModel.episodio.value = novoEpisodio
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Tempo pausado") },
                value = "${tempoPausado.value}",
                onValueChange = { novoTempoPausado ->
                    addEditSerieListViewModel.tempoPausado.value = novoTempoPausado
                }
            )
        }
        if(id != -1 )
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    aoRemoverSerie(id)
                    navigateBack()
                }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Deletar")
            }
    }
}

//@Preview
//@Composable
//fun FormPreviewAdicionarEditarSerie() {
//    val addEditSerieViewModel: AddEditSerieViewModel = viewModel()
//    FormAdicionarEditarSerie(addEditSerieViewModel)
//}

