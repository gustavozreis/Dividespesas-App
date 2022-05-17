package com.gustavozreis.dividespesas.features.splitspend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.SplitSpendFragmentBinding
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel

class SplitSpendFragment : Fragment() {

    companion object {
        fun newInstance() = SplitSpendFragment()
    }

    private lateinit var viewModel: ViewModel

    private var _binding: SplitSpendFragmentBinding? = null
    private val binding get() = _binding!!

    private var testText: TextView? = null
    private var testButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = SplitSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        (viewModel as SpendSharedViewModel).splitSpend()
        setUpBindings()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    private fun setUpBindings() {
        testText = binding.textviewTest
        testButton = binding.btnTest
    }

    private fun setUpObservers() {
       (viewModel as SpendSharedViewModel)
           .userAndSpendsData.observe(this.viewLifecycleOwner) { returnList ->
           val returnTestText: String =
               "${returnList[0]} gastou ${returnList[1]} \n ${returnList[2]} gastou ${returnList[3]}"
           testText?.setText(returnTestText)
       }
    }

    private fun setUpListeners() {
    }

}