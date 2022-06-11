package com.example.composeaula03.data

import androidx.lifecycle.LiveData

data class Serie(

    val id: Int,
    val nome: String,
    val temporada: String,
    val episodio: String,
    val tempoPausado: String,
    )