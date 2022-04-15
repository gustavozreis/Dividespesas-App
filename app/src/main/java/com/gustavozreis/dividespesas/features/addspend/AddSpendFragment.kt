package com.gustavozreis.dividespesas.features.addspend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.databinding.AddSpendFragmentBinding

class AddSpendFragment : Fragment() {

    companion object {
        fun newInstance() = AddSpendFragment()
    }

    var _binding: AddSpendFragmentBinding? = null
    val binding get() = _binding!!

    var spendTypeSelected: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = AddSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpinners()
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