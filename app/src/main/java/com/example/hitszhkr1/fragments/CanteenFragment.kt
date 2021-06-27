package com.example.hitszhkr1.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hitszhkr1.CanteenItem
import com.example.hitszhkr1.R
import com.example.hitszhkr1.adapters.CanteenItemAdapter
import com.example.hitszhkr1.popup.SortPopupWindow
import kotlinx.android.synthetic.main.fragment_canteen.*
import java.util.ArrayList

class CanteenFragment : Fragment() {

    private val itemList = ArrayList<CanteenItem>()//itemList为列表

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_canteen,container,false)
    }

    @SuppressLint("InflateParams")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //初始化UI样式
        sButton1.setBackgroundColor(Color.TRANSPARENT)
        sButton2.setBackgroundColor(Color.TRANSPARENT)
        sButton3.setBackgroundColor(Color.TRANSPARENT)
        sButton1.text = "全部地点"
        sButton2.text = "全部口味"
        sButton3.text = "推荐排序"

        //初始化卡片列表
        initList()
        val layoutManager = LinearLayoutManager(context)
        fRecycler.layoutManager = layoutManager
        val adapter = context?.let { CanteenItemAdapter(it, itemList) }
        fRecycler.adapter = adapter

        //初始化弹出窗口
        val popWindowView1 = LayoutInflater.from(context).inflate(R.layout.popup_sort_location,null)
        val popWindowView2 = LayoutInflater.from(context).inflate(R.layout.popup_sort_taste,null)
        val popWindowView3 = LayoutInflater.from(context).inflate(R.layout.popup_sort_method,null)
        val popWindow1 = context?.let { SortPopupWindow(
            popWindowView1,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ) }
        val popWindow2 = context?.let { SortPopupWindow(
            popWindowView2,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ) }
        val popWindow3 = context?.let { SortPopupWindow(
            popWindowView3,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ) }
        if (popWindow1 != null) {
            popWindow1.isFocusable = true
        }
        if (popWindow2 != null) {
            popWindow2.isFocusable = true
        }
        if (popWindow3 != null) {
            popWindow3.isFocusable = true
        }

        //地址筛选
        val lSortContent = popWindow1?.contentView
        val lSortButton0 = lSortContent?.findViewById<Button>(R.id.sl0)
        val lSortButton1 = lSortContent?.findViewById<Button>(R.id.sl1)
        val lSortButton2 = lSortContent?.findViewById<Button>(R.id.sl2)
        val lSortButton3 = lSortContent?.findViewById<Button>(R.id.sl3)

        sButton1.setOnClickListener {
            popWindow1?.showAsDropDown(sButton1)
        }

        lSortButton0?.setBackgroundColor(Color.TRANSPARENT)
        lSortButton1?.setBackgroundColor(Color.TRANSPARENT)
        lSortButton2?.setBackgroundColor(Color.TRANSPARENT)
        lSortButton3?.setBackgroundColor(Color.TRANSPARENT)

        lSortButton0?.setOnClickListener {
            //全部地点
            sButton1.text = "全部地点"
            sButton2.text = "全部口味"
            sButton3.text = "推荐排序"
            fRecycler.adapter = adapter
            popWindow1.dismiss()
        }
        lSortButton1?.setOnClickListener {
            //仅看荔园
            sButton1.text = "仅看荔园"
            sButton2.text = "全部口味"
            sButton3.text = "推荐排序"
            fRecycler.adapter = context?.let { it -> CanteenItemAdapter(it, itemList.filter { it.location == "荔园" }) }
            popWindow1.dismiss()
        }
        lSortButton2?.setOnClickListener {
            //仅看荷园
            sButton1.text = "仅看荷园"
            sButton2.text = "全部口味"
            sButton3.text = "推荐排序"
            fRecycler.adapter = context?.let { it -> CanteenItemAdapter(it, itemList.filter { it.location == "荷园" }) }
            popWindow1.dismiss()
        }
        lSortButton3?.setOnClickListener {
            //仅看燕园
            sButton1.text = "仅看燕园"
            sButton2.text = "全部口味"
            sButton3.text = "推荐排序"
            fRecycler.adapter = context?.let { it -> CanteenItemAdapter(it, itemList.filter { it.location == "燕园" }) }
            popWindow1.dismiss()
        }

        //口味筛选
        val tSortContent = popWindow2?.contentView
        val tSortButton0 = tSortContent?.findViewById<Button>(R.id.st0)
        val tSortButton1 = tSortContent?.findViewById<Button>(R.id.st1)
        val tSortButton2 = tSortContent?.findViewById<Button>(R.id.st2)

        sButton2.setOnClickListener {
            popWindow2?.showAsDropDown(sButton2)
        }

        tSortButton0?.setBackgroundColor(Color.TRANSPARENT)
        tSortButton1?.setBackgroundColor(Color.TRANSPARENT)
        tSortButton2?.setBackgroundColor(Color.TRANSPARENT)

        tSortButton0?.setOnClickListener {
            //全部口味
            sButton1.text = "全部地点"
            sButton2.text = "全部口味"
            sButton3.text = "推荐排序"
            fRecycler.adapter = adapter
            popWindow2.dismiss()
        }
        tSortButton1?.setOnClickListener {
            //拒绝辛辣
            sButton1.text = "全部地点"
            sButton2.text = "拒绝辛辣"
            sButton3.text = "推荐排序"
            fRecycler.adapter = context?.let { it -> CanteenItemAdapter(it, itemList.filter { it.spice == 0 }) }
            popWindow2.dismiss()
        }
        tSortButton2?.setOnClickListener {
            //拒绝甜食
            sButton1.text = "全部地点"
            sButton2.text = "拒绝甜食"
            sButton3.text = "推荐排序"
            fRecycler.adapter = context?.let { it -> CanteenItemAdapter(it, itemList.filter { it.sweet == 0 }) }
            popWindow2.dismiss()
        }

        //排序方式
        val mSortContent = popWindow3?.contentView
        val mSortButton0 = mSortContent?.findViewById<Button>(R.id.sm1)
        val mSortButton1 = mSortContent?.findViewById<Button>(R.id.sm2)
        val mSortButton2 = mSortContent?.findViewById<Button>(R.id.sm3)

        sButton3.setOnClickListener {
            popWindow3?.showAsDropDown(sButton3)
        }

        mSortButton0?.setBackgroundColor(Color.TRANSPARENT)
        mSortButton1?.setBackgroundColor(Color.TRANSPARENT)
        mSortButton2?.setBackgroundColor(Color.TRANSPARENT)

        mSortButton0?.setOnClickListener {
            //推荐排序
            sButton1.text = "全部地点"
            sButton2.text = "全部口味"
            sButton3.text = "推荐排序"
            fRecycler.adapter = adapter
            popWindow3.dismiss()
        }

        mSortButton1?.setOnClickListener {
            //人气排序，待实现
            sButton1.text = "全部地点"
            sButton2.text = "全部口味"
            sButton3.text = "人气优先"
            fRecycler.adapter = adapter
            popWindow3.dismiss()
        }

        mSortButton2?.setOnClickListener {
            //评分排序
            sButton1.text = "全部地点"
            sButton2.text = "全部口味"
            sButton3.text = "评分优先"
            fRecycler.adapter = context?.let { it1 -> CanteenItemAdapter(it1,itemList.sortedByDescending { canteenItem ->  canteenItem.mark}) }
            popWindow3.dismiss()
        }
    }

    private fun initList(){
        itemList.add(CanteenItem("野菇鸡肉串",R.drawable.pic_temp_1,1,"荔园",1,0,1))
        itemList.add(CanteenItem("甜甜花酿鸡",R.drawable.pic_temp_2,2,"荷园",2,0,1))
        itemList.add(CanteenItem("蒙德土豆饼",R.drawable.pic_temp_3,3,"荷园",3,1,1))
        itemList.add(CanteenItem("提瓦特煎蛋",R.drawable.pic_temp_4,4,"燕园",1,1,0))
        itemList.add(CanteenItem("北地烟熏鸡",R.drawable.pic_temp_5,5,"荔园",2,1,0))
    }
}