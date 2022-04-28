package com.gustavozreis.dividespesas.base.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.base.MainViewPageAdapter
import com.gustavozreis.dividespesas.features.addspend.AddSpendFragment
import com.gustavozreis.dividespesas.features.checkspend.CheckSpendFragment
import com.gustavozreis.dividespesas.features.splitspend.SplitSpendFragment

class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}