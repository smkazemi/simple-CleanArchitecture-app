package com.basalam.intern.android.features.contentOfList.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import com.basalam.intern.android.databinding.ItemViewPagerContentBinding

class ImagePagerAdapter(val context: Context, val imageUrlList: List<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object` as FrameLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val binding = ItemViewPagerContentBinding.inflate(LayoutInflater.from(context))
        binding.imgUrl = imageUrlList[position]
        binding.executePendingBindings()
        container.addView(binding.root)

        return binding.root

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView((`object` as FrameLayout))
    }
}