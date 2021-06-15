package com.example.testviewpagertwo

import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testviewpagertwo.databinding.ActivityMainBinding
import com.example.testviewpagertwo.databinding.MyItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    val cities = listOf("Tokyo","Jakarta","Manila","Dehli","Lagos","Moscow")

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding){
            setContentView(root)

            viewpager.adapter = MyAdapter()
            viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL

            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Toast.makeText(this@MainActivity,position.toString(),Toast.LENGTH_SHORT).show()
                }
            })

            viewpager.setPageTransformer{page, position ->
                val viewPager = page.parent.parent as ViewPager2
                val offset = position*-(2*120)
                if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else{
                    page.translationY = offset
                }
            }


            TabLayoutMediator(tabLayout,viewpager){tab,position ->
                tab.text = cities[position]
            }.attach()

        }

    }


    inner class MyViewHolder(val binding: MyItemBinding): RecyclerView.ViewHolder(binding.root)

    inner class MyAdapter: RecyclerView.Adapter<MyViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(MyItemBinding.inflate(layoutInflater,parent,false))
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.binding.title.text = cities[position]
        }

        override fun getItemCount() = cities.size

    }
}