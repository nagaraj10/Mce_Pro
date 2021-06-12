package com.example.mce_pro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Fragment_Placement:Fragment() {
    var tablayout:TabLayout?=null
    var viewpager:ViewPager?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_placement,container,false)
        tablayout=v.findViewById(R.id.tab_layout)
        viewpager=v.findViewById<ViewPager>(R.id.view_pager)

        var adapter:ViewPagerAdapter=ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(FragmentPlacement_Records(),"Placement Records")
        adapter.addFragment(Fragment_Major(),"Major Recruiters")
        adapter.addFragment(Fragment_placementassistance(),"Placement Assistance")
        viewpager!!.adapter=adapter
        tablayout!!.setupWithViewPager(viewpager)



        return v
    }

    internal class ViewPagerAdapter(supportFragmentManager: FragmentManager?) : FragmentPagerAdapter(supportFragmentManager!!) {
        private val mList: MutableList<Fragment> = ArrayList()
        private val mTitleList: MutableList<String> = ArrayList()
        override fun getItem(i: Int): Fragment {
            return mList[i]
        }

        override fun getCount(): Int {
            return mList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mList.add(fragment)
            mTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitleList[position]
        }
    }
}