package com.gustavozreis.dividespesas.features.checkspend

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.CheckSpendFragmentBinding
import com.gustavozreis.dividespesas.data.models.Spend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class CheckSpendFragment : Fragment() {

    companion object {
        fun newInstance() = CheckSpendFragment()
    }

    private lateinit var viewModel: ViewModel

    private var _binding: CheckSpendFragmentBinding? = null
    private val binding get() = _binding!!

    private var testText: TextView? = null
    private var testBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = CheckSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            CheckSpendViewModelFactory(FirebaseSpendHelper(FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]

        testText = binding.testText
        testBtn = binding.testButton

        lifecycleScope.launch { (viewModel as SpendSharedViewModel).getSpendList() }

        (viewModel as SpendSharedViewModel)
            .spendListLiveData.observe(this.viewLifecycleOwner) { spendList ->
            testText?.text = spendList.size.toString()
        }



        testBtn?.setOnClickListener {

            /*lifecycleScope.launch {

                var lista: List<Spend> = (viewModel as CheckSpendViewModel).getSpendList()

                testText?.text = lista.toString()
            }*/

        }

    }

    override fun onResume() {
        super.onResume()

    }


}

