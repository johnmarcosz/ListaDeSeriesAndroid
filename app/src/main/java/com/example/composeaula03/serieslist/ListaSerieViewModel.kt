package com.example.composeaula03.serieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Serie

class ListaSerieViewModel: ViewModel() {

    private val _listaSerie: MutableLiveData<List<Serie>> = MutableLiveData(
        listOf(
            Serie(
                0,
                "Vikings",
                "1",
                "9",
                "00:49:01",
            ),
            Serie(
                1,
                "Game of Thrones",
                "5",
                "2",
                "00:34:22",
            ),
            Serie(
                2,
                "La Casa de Papel",
                "4",
                "1",
                "01:01:52",
            ),
            Serie(
                3,
                "Black Mirror",
                "3",
                "3",
                "00:20:17",
            ),
            Serie(
                0,
                "Round 6",
                "1",
                "7",
                "00:05:35",
            )
        )
    )

    private val _filtrarPor: MutableLiveData<String> = MutableLiveData("")

    val filtrarPor: LiveData<String>
        get() = _filtrarPor

    val listaSerie: LiveData<List<Serie>>
        get() {
            return if(_filtrarPor.value == "")
                _listaSerie
            else {
                val lista: List<Serie> = _listaSerie.value?.filter { serie ->
                    serie.nome.contains(_filtrarPor.value ?: "")
                } ?: listOf()
                MutableLiveData(lista)
            }
        }

    fun atualizarFiltro(novoFiltro: String){
        _filtrarPor.value = novoFiltro
    }

    fun inserirSerie(serie: Serie){
        val lista: MutableList<Serie> = _listaSerie.value?.toMutableList() ?: return
        lista.add(serie)
        _listaSerie.value = lista
    }

    fun atualizarSerie(serieAtualizada: Serie) {
        var position = -1
        _listaSerie.value?.forEachIndexed{ index, serie ->
            if(serieAtualizada.id == serie.id)
                position = index
        }

        val lista: MutableList<Serie> = _listaSerie.value?.toMutableList() ?: return
        lista.removeAt(position)
        lista.add(position, serieAtualizada)
        _listaSerie.value = lista
    }

    fun removerSerie(id: Int) {
        var position = -1
        _listaSerie.value?.forEachIndexed{ index, serie ->
            if(id == serie.id)
                position = index
        }

        val lista: MutableList<Serie> = _listaSerie.value?.toMutableList() ?: return
        lista.removeAt(position)
        _listaSerie.value = lista
    }

    fun getSerie(id: Int): Serie {
        _listaSerie.value?.forEach { serie ->
            if(id == serie.id)
                return serie
        }
        return Serie(
            -1,
            "",
            "",
            "",
            ""
        )
    }
}