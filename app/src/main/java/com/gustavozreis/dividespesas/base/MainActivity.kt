package com.gustavozreis.dividespesas.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.gustavozreis.dividespesas.R
import com.gustavozreis.dividespesas.data.firebase.FirebaseSpendHelper
import com.gustavozreis.dividespesas.data.repository.SpendRepository
import com.gustavozreis.dividespesas.features.addspend.AddSpendFragment
import com.gustavozreis.dividespesas.features.checkspend.CheckSpendFragment
import com.gustavozreis.dividespesas.features.splitspend.SplitSpendFragment

class MainActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager()

    }

    private fun setUpViewPager() {
        val viewPageAdapter = MainViewPageAdapter(supportFragmentManager)
        viewPageAdapter.apply {
            this.add(AddSpendFragment(), "Adicionar")
            this.add(CheckSpendFragment(), "Despesas")
            this.add(SplitSpendFragment(), "Divisão")
        }

        viewPager = findViewById(R.id.main_view_pager)
        viewPager?.adapter = viewPageAdapter

        tabLayout = findViewById(R.id.main_tab_layout)
        tabLayout?.setupWithViewPager(viewPager)
    }
}