package com.gustavozreis.dividespesas.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainViewPageAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager){

    private val fragmentList: MutableList<Fragment> = mutableListOf()
    private val titleList: MutableList<String> = mutableListOf()

    fun add(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}