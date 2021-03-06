package com.example.seriados.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seriados.database.EpisodiosDao
import com.example.seriados.database.SeriesDao

class FormSeriesViewModelFactory (
    val seriesDao: SeriesDao,
    val episodiosDao: EpisodiosDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormSeriesViewModel::class.java)) {
            return FormSeriesViewModel(seriesDao, episodiosDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}