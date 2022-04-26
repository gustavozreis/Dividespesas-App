package com.gustavozreis.dividespesas.features.addspend

import android.app.DatePickerDialog
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.AddSpendFragmentBinding
import com.gustavozreis.dividespesas.features.utils.DecimalDigitsInputFilter
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import com.gustavozreis.dividespesas.features.utils.DatePicker

class AddSpendFragment : Fragment() {

    companion object {
        fun newInstance() = AddSpendFragment()
    }

    private lateinit var viewModel: ViewModel

    private var _binding: AddSpendFragmentBinding? = null
    private val binding get() = _binding!!

    var spendTypeSelected: String? = null
    private var spendValueInput: EditText? = null
    private var spendDateInput: TextView? = null
    private var spendDescriptionInput: EditText? = null

    private var buttonAdd: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = AddSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBinding()
        setUpSpinners()
        setUpListeners()

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    private fun setUpBinding() {
        setUpSpendValueBindingAndDecimalFormat()
        spendDateInput = binding.edittextDateInput
        spendDescriptionInput = binding.edittextDescriptionInput
        buttonAdd = binding.buttonAdd

    }

    private fun setUpSpendValueBindingAndDecimalFormat() {
        spendValueInput = binding.edittextValueInput
        spendValueInput?.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(2))
        spendValueInput?.setOnFocusChangeListener { view, b ->

            if (!b) {
                val decimalFormat = DecimalFormat("0.00")
                if (spendValueInput!!.text.toString() == "") {
                    spendValueInput!!.setText("0,00")
                } else {
                    val valueInputString = spendValueInput!!.text.toString()
                    val valueInputStringInDouble = valueInputString.replace(',', '.', true)
                    val valueInputStringFormated =
                        decimalFormat.format(valueInputStringInDouble.toDouble())
                    val finalStringWithDot = valueInputStringFormated.toString()
                    val finalStringWithComma = finalStringWithDot.replace('.', ',', true)
                    spendValueInput!!.setText(finalStringWithComma)
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUpListeners() {
        buttonAdd?.setOnClickListener { addSpend() }

        spendDateInput?.setOnClickListener {

            val datePicker = DatePicker()
            datePicker.datePicker(this.requireContext(), spendDateInput!!)
        }
    }

    private fun addSpend() {
        if (spendValueInput!!.text.isEmpty()) {
            Toast.makeText(this.requireContext(),
                "Adicione o valor da despesa para salvar.",
                Toast.LENGTH_LONG).show()
        } else if (spendDateInput!!.text.isEmpty()) {
            Toast.makeText(this.requireContext(),
                "Adicione a data da despesa para salvar.",
                Toast.LENGTH_LONG).show()
        } else if (spendDescriptionInput!!.text.isEmpty()) {
            spendDescriptionInput!!.setText("")

            lifecycleScope.launch {

                val spendValueWithComma = spendValueInput?.text.toString()
                val spendValueWithDot = spendValueWithComma.replace(",", ".")
                val spendValueDouble = spendValueWithDot.toDouble()

                (viewModel as SpendSharedViewModel).addNewSpend(
                    spendTypeSelected!!,
                    spendValueDouble,
                    spendDateInput?.text.toString(),
                    spendDescriptionInput?.text.toString()
                )

                spendValueInput!!.setText("")
                spendDescriptionInput!!.setText("")
            }

            Toast.makeText(this.requireContext(),
                "Despesa adicionada.",
                Toast.LENGTH_LONG).show()

        } else {
            lifecycleScope.launch {

                val spendValueWithComma = spendValueInput?.text.toString()
                val spendValueWithDot = spendValueWithComma.replace(",", ".")
                val spendValueDouble = spendValueWithDot.toDouble()

                (viewModel as SpendSharedViewModel).addNewSpend(
                    spendTypeSelected!!,
                    spendValueDouble,
                    spendDateInput?.text.toString(),
                    spendDescriptionInput?.text.toString()
                )

                spendValueInput!!.setText("")
                spendDescriptionInput!!.setText("")
            }

            Toast.makeText(this.requireContext(),
                "Despesa adicionada.",
                Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpSpinners() {
        //set up spend type spinner
        val spendTypeSpinner: Spinner = binding.spinnerSpendType
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.spendtype_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
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