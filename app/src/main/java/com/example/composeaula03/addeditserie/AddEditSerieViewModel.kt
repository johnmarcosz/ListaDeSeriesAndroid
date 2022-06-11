package com.example.composeaula03.addeditserie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeaula03.data.Serie

class AddEditSerieViewModel : ViewModel() {

    private val _id : MutableLiveData<Int> = MutableLiveData(6)

//    val id: LiveData<Int>
//        get() = _id
//    fun changeId(newId:Int){
//        _id.value = newId
//    }

    val nome : MutableLiveData<String> = MutableLiveData("")
    val temporada : MutableLiveData<String> = MutableLiveData("")
    val episodio : MutableLiveData<String> = MutableLiveData("")
    val tempoPausado : MutableLiveData<String> = MutableLiveData("")

    fun inserirSerie(
        aoInserirSerie: (Serie) -> Unit
    ) {
        val novaSerie = Serie(
            _id.value ?: return,
            nome.value ?: return,
            temporada.value ?: return,
            episodio.value ?: return,
            tempoPausado.value ?: return
        )

        aoInserirSerie(novaSerie)
        var tempId: Int =  _id.value ?: return
        tempId++
        _id.value = tempId

        nome.value = ""
        temporada.value = ""
        episodio.value = ""
        tempoPausado.value = ""

    }

    fun atualizarSerie(
        id: Int,
        aoAtualizarSerie: (Serie) -> Unit
    ){
        val serie = Serie(
            id,
            nome.value ?: return,
            temporada.value ?: return,
            episodio.value?: return,
            tempoPausado.value ?: return
        )
        aoAtualizarSerie(serie)
    }
}