package com.gustavozreis.dividespesas.base.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.base.MainViewPageAdapter
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModel
import com.gustavozreis.dividespesas.base.viewmodels.BaseViewModelFactory
import com.gustavozreis.dividespesas.data.users.UserInstance
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserHelper
import com.gustavozreis.dividespesas.data.users.firebase.FirebaseUserServiceImpl
import com.gustavozreis.dividespesas.features.addspend.AddSpendFragment
import com.gustavozreis.dividespesas.features.checkspend.CheckSpendFragment
import com.gustavozreis.dividespesas.features.splitspend.SplitSpendFragment
import kotlinx.coroutines.delay

class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var viewModel: ViewModel

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpViewPager()

    }

    private fun setUpViewPager() {
        val viewPageAdapter = MainViewPageAdapter(childFragmentManager)
        viewPageAdapter.apply {
            this.add(AddSpendFragment(), "Adicionar")
            this.add(CheckSpendFragment(), "Despesas")
            this.add(SplitSpendFragment(), "Divisão")
        }

        viewPager = requireView().findViewById(R.id.main_view_pager)
        viewPager.adapter = viewPageAdapter
        viewPager.currentItem = 1

        tabLayout = requireView().findViewById(R.id.main_tab_layout)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            BaseViewModelFactory(
                FirebaseUserHelper(
                    FirebaseUserServiceImpl())))[BaseViewModel::class.java]
    }

}