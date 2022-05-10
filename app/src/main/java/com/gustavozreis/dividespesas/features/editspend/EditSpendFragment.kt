package com.gustavozreis.dividespesas.features.editspend

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.data.users.UserInstance
import com.gustavozreis.dividespesas.databinding.EditSpendFragmentBinding
import com.gustavozreis.dividespesas.features.utils.DatePicker
import com.gustavozreis.dividespesas.features.utils.DecimalDigitsInputFilter
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel
import java.text.DecimalFormat

class EditSpendFragment : Fragment(R.layout.edit_spend_fragment) {

    private lateinit var viewModel: ViewModel

    private var _binding: EditSpendFragmentBinding? = null
    private val binding get() = _binding!!

    private var typeInput: Spinner? = null
    private var valueInput: EditText? = null
    private var valueArgs: String? = null
    private var dateInput: EditText? = null
    private var descriptionInput: EditText? = null

    private var spendUser: String? = null
    private var spendId: String? = null
    private var spendIndex: Int? = null

    private var btnSave: Button? = null
    private var btnCancel: Button? = null

    private var spendTypeSelected: String? = null

    private val args: EditSpendFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBinding()
        setUpArguments()
        setUpListeners()
        setUpSpinners()

    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    private fun setUpBinding() {
        typeInput = binding.spinnerSpendType
        valueInput = binding.edittextValueInput
        setUpSpendValueBindingAndDecimalFormat()
        dateInput = binding.edittextDateInput
        descriptionInput = binding.edittextDescriptionInput
        btnSave = binding.buttonSave
        btnCancel = binding.buttonCancel
    }

    private fun setUpArguments() {
        //typeInput?.setText(args.spendType)
        valueInput?.setText(args.spendValue)
        dateInput?.setText(args.spendDate)
        descriptionInput?.setText(args.spendDescription)
        spendUser = args.spendType
        spendId = args.spendId
        spendIndex = args.spendIndex
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUpListeners() {

        //set up date dialog selector
        dateInput?.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == ACTION_DOWN) {
                val datePicker = DatePicker()
                datePicker.datePicker(this.requireContext(), dateInput!!)
            }
            true
        }

        btnCancel?.setOnClickListener {
            activity?.onBackPressed()
        }

        btnSave?.setOnClickListener {
            updateSpend()
            findNavController().navigate(R.id.action_editSpendFragment_to_homeFragment)
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
            spendTypeSpinner.setSelection(spendIndex!!)
        }
        spendTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spendTypeSelected = spendTypeSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun setUpSpendValueBindingAndDecimalFormat() {
        valueInput = binding.edittextValueInput
        valueInput?.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(2))
        valueInput?.setText(valueArgs)
        valueInput?.setOnFocusChangeListener { view, b ->

            if (!b) {
                val decimalFormat = DecimalFormat("0.00")
                if (valueInput!!.text.toString() == "") {
                    valueInput!!.setText("0,00")
                } else {
                    val valueInputString = valueInput!!.text.toString()
                    val valueInputStringInDouble = valueInputString.replace(',', '.', true)
                    val valueInputStringFormated =
                        decimalFormat.format(valueInputStringInDouble.toDouble())
                    val finalStringWithDot = valueInputStringFormated.toString()
                    val finalStringWithComma = finalStringWithDot.replace('.', ',', true)
                    valueInput!!.setText(finalStringWithComma)
                }
            }
        }

    }

    private fun updateSpend() {
        if (valueInput!!.text.isEmpty()) {
            Toast.makeText(this.requireContext(),
                "Adicione o valor da despesa para salvar.",
                Toast.LENGTH_LONG).show()
        } else if (dateInput!!.text.isEmpty()) {
            Toast.makeText(this.requireContext(),
                "Adicione a data da despesa para salvar.",
                Toast.LENGTH_LONG).show()
        } else if (descriptionInput!!.text.isEmpty()) {
            descriptionInput!!.setText("")

            val spendValueWithComma = valueInput?.text.toString()
            val spendValueWithDot = spendValueWithComma.replace(",", ".")
            val spendValueDouble = spendValueWithDot.toDouble()

            val editedSpend = Spend (
                dateInput?.text.toString(),
                descriptionInput?.text.toString(),
                spendId!!,
                spendTypeSelected!!,
                UserInstance.currentUser!!.userFirstName,
                spendValueDouble,
                spendIndex!!
            )

            (viewModel as SpendSharedViewModel).updateSpend(
                spendId!!,
                editedSpend
            )

            Toast.makeText(this.requireContext(),
                "Despesa alterada.",
                Toast.LENGTH_LONG).show()

        } else {
            val spendValueWithComma = valueInput?.text.toString()
            val spendValueWithDot = spendValueWithComma.replace(",", ".")
            val spendValueDouble = spendValueWithDot.toDouble()

            val editedSpend = Spend (
                dateInput?.text.toString(),
                descriptionInput?.text.toString(),
                spendId!!,
                spendTypeSelected!!,
                UserInstance.currentUser!!.userFirstName,
                spendValueDouble,
                spendIndex!!
            )

            (viewModel as SpendSharedViewModel).updateSpend(
                spendId!!,
                editedSpend
            )

            Toast.makeText(this.requireContext(),
                "Despesa alterada.",
                Toast.LENGTH_LONG).show()
        }
    }

}