package com.gustavozreis.dividespesas.features.checkspend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.CheckSpendDetailBinding
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel

class CheckSpendDetailFragment : Fragment(R.layout.check_spend_detail) {

    private lateinit var viewModel: ViewModel

    private var _binding: CheckSpendDetailBinding? = null
    private val binding get() = _binding!!

    private var spendType: TextView? = null
    private var spendValue: TextView? = null
    private var spendDate: TextView? = null
    private var spendDescription: TextView? = null
    private var spendId: String? = null
    private var spendUser: String? = null

    val args: CheckSpendDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CheckSpendDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpBindings()
        setUpArguments()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    private fun setUpBindings() {
        spendType = binding.textviewSpendType
        spendValue = binding.textviewSpendValue
        spendDate = binding.textviewSpendDate
        spendDescription = binding.textviewSpendDescription
    }

    private fun setUpArguments() {
        spendType?.text = args.spendType
        spendValue?.text = args.spendValue
        spendDate?.text = args.spendDate
        spendDescription?.text = args.spendDescription
        spendUser = args.spendType
        spendId = args.spendId
    }

}