package com.example.composeaula03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composeaula03.addeditserie.AddEditSerieViewModel
import com.example.composeaula03.addeditserie.TelaAdicionarEditarLista
import com.example.composeaula03.serieslist.ListaSerieViewModel
import com.example.composeaula03.serieslist.TelaListaDeSeries
import com.example.composeaula03.ui.theme.ComposeAula03Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listaSerieViewModel: ListaSerieViewModel by viewModels()
        val addEditSerieViewModel: AddEditSerieViewModel by viewModels()


        setContent {
            ComposeAula03Theme {
                // A surface container using the 'background' color from the theme
                MyApp(
                    listaSerieViewModel,
                    addEditSerieViewModel
                )
            }
        }
    }
}

@Composable
fun MyApp(
    listaSerieViewModel: ListaSerieViewModel,
    addEditSerieViewModel: AddEditSerieViewModel
) {

    val navController = rememberNavController()

    Scaffold(){

        NavHost(navController = navController, startDestination = "serieslist"){

            composable("serieslist"){
                TelaListaDeSeries(navController, listaSerieViewModel)
            }

            composable(
                route = "addeditserie?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ){ navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                val serie = listaSerieViewModel.getSerie(id)
                TelaAdicionarEditarLista(
                    navController,
                    addEditSerieViewModel,
                    listaSerieViewModel::inserirSerie,
                    listaSerieViewModel::atualizarSerie,
                    listaSerieViewModel::removerSerie,
                    serie
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeAula03Theme {

    }
}