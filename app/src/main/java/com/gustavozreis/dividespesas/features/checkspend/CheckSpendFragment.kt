package com.gustavozreis.dividespesas.features.checkspend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.spends.firebase.FirebaseSpendServiceImpl
import com.gustavozreis.dividespesas.databinding.CheckSpendFragmentBinding
import com.gustavozreis.dividespesas.data.spends.models.Spend
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedModelFactory
import com.gustavozreis.dividespesas.features.viewmodel.SpendSharedViewModel

class CheckSpendFragment : Fragment() {

    companion object {
        fun newInstance() = CheckSpendFragment()
    }

    private lateinit var viewModel: ViewModel

    private var _binding: CheckSpendFragmentBinding? = null
    private val binding get() = _binding!!

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = CheckSpendFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()

        recyclerView = binding.recyclerviewCheckspendMain


        (viewModel as SpendSharedViewModel)
            .spendListLiveData.observe(this.viewLifecycleOwner) { spendListViewModel ->
                val spendList: MutableList<Spend> = mutableListOf()
                for (spend in spendListViewModel) {
                    spendList.add(spend)
                }

                val rvAdapter = CheckSpendListAdapter(this.context, spendList, findNavController())
                recyclerView?.adapter = rvAdapter
            }



    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            SpendSharedModelFactory(
                FirebaseSpendHelper(
                    FirebaseSpendServiceImpl())))[SpendSharedViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

    }


}

