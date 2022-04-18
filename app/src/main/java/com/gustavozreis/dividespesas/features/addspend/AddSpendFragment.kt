package com.gustavozreis.dividespesas.features.addspend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.AddSpendFragmentBinding
import com.gustavozreis.dividespesas.features.checkspend.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.checkspend.SpendSharedViewModel
import kotlinx.coroutines.launch

class AddSpendFragment : Fragment() {

    companion object {
        fun newInstance() = AddSpendFragment()
    }

    private lateinit var viewModel: ViewModel

    private var _binding: AddSpendFragmentBinding? = null
    private val binding get() = _binding!!

    var spendTypeSelected: String? = null
    private var spendValueInput: EditText? = null
    private var spendDateInput: EditText? = null
    private var spendDescriptionInput: EditText? = null

    private var buttonAdd: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = AddSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBinding()
        setUpSpinners()
        setUpListeners()

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    private fun addSpend () {
        if (spendValueInput!!.text.isEmpty()) {
            Toast.makeText(this.requireContext(), "Adicione o valor da despesa para salvar.", Toast.LENGTH_LONG).show()
        } else if (spendDateInput!!.text.isEmpty()) {
            Toast.makeText(this.requireContext(), "Adicione a data da despesa para salvar.", Toast.LENGTH_LONG).show()
        } else if (spendDescriptionInput!!.text.isEmpty()) {
            spendDescriptionInput!!.setText("")
        } else {
                lifecycleScope.launch {
                    (viewModel as SpendSharedViewModel).addNewSpend(
                        spendDateInput?.text.toString(),
                        spendValueInput?.text.toString().toDouble(),
                        spendDateInput?.text.toString(),
                        spendDescriptionInput?.text.toString()
                        )
                }
        }
    }

    private fun setUpBinding() {
        spendValueInput = binding.edittextValueInput
        spendDateInput = binding.edittextDateInput
        spendDescriptionInput = binding.edittextDescriptionInput
        buttonAdd = binding.buttonAdd

    }

    private fun setUpListeners() {
        buttonAdd?.setOnClickListener { addSpend() }
    }

    private fun setUpSpinners() {
        //set up spend type spinner
        val spendTypeSpinner: Spinner = binding.spinnerSpendType
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.spendtype_array,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spendTypeSpinner.adapter = adapter
        }
        spendTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spendTypeSelected = spendTypeSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


}