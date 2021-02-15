package com.example.seriados.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.seriados.R
import com.example.seriados.database.AppDatabase
import com.example.seriados.database.EpisodiosUtil
import com.example.seriados.database.SeriesUtil
import com.example.seriados.model.Series
import kotlinx.android.synthetic.main.fragment_form_series.*
import kotlinx.android.synthetic.main.fragment_series.*

class FormSeriesFragment : Fragment() {

    private lateinit var viewModel: FormSeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form_series, container, false)


        val appDatabase =
            AppDatabase.getInstance(requireContext().applicationContext)              // DB

        val seriesDao = appDatabase.seriesDao()
        val formSeriesViewModelFactory = FormSeriesViewModelFactory(seriesDao) //    // DB

        viewModel = ViewModelProvider(
            this, formSeriesViewModelFactory
        ).get(FormSeriesViewModel::class.java)   //

        viewModel.let {
            it.msg.observe(viewLifecycleOwner) { msg ->
                if (!msg.isNullOrBlank()){
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            }
            it.status.observe(viewLifecycleOwner) { status ->
                if (status)
                    findNavController().popBackStack()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SeriesUtil.serieSelecionada != null)
            preencherFormulario(SeriesUtil.serieSelecionada!!)


        buttonAddEpi.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }

        fabSalvar.setOnClickListener {


            val nome = editTextNome.text.toString()
            val categoria = editTextCategoria.text.toString()

            viewModel.salvarSeries(nome,categoria)
        }
    }

    private fun preencherFormulario(series: Series){
        editTextNome.setText(series.nome)
        editTextCategoria.setText(series.categoria)
    }

    private fun limparFormulario() {
        editTextNome.setText("")
        editTextCategoria.setText("")
    }

}