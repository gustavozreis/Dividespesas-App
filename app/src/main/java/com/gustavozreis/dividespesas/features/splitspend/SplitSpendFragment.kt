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

    private var textUser01Line01: TextView? = null
    private var textUser01Line02: TextView? = null
    private var textUser01Line03: TextView? = null
    private var textUser02Line01: TextView? = null
    private var textUser02Line02: TextView? = null
    private var textUser02Line03: TextView? = null

    private var usersAndSpendDataObserver: List<String> = listOf()

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
        textUser01Line01 = binding.textviewUser01Line01
        textUser01Line02 = binding.textviewUser01Line02
        textUser01Line03 = binding.textviewUser01Line03
        textUser02Line01 = binding.textviewUser02Line01
        textUser02Line02 = binding.textviewUser02Line02
        textUser02Line03 = binding.textviewUser02Line03
    }

    private fun setUpObservers() {
       (viewModel as SpendSharedViewModel)
           .usersAndSpendsData.observe(this.viewLifecycleOwner) { returnList ->
               usersAndSpendDataObserver = returnList
               setUpSplitSpends(usersAndSpendDataObserver)
       }
    }

    private fun setUpListeners() {
    }

    private fun setUpSplitSpends(dataList: List<String>) {
        textUser01Line01?.text = getString(R.string.user01line01, dataList[0])
        textUser01Line02?.text = getString(R.string.user01line02, dataList[1])

        textUser02Line01?.text = getString(R.string.user02line01, dataList[2])
        textUser02Line02?.text = getString(R.string.user02line02, dataList[3])
    }

}