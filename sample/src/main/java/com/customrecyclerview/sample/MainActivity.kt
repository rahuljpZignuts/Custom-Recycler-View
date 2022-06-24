package com.customrecyclerview.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.customrecyclerview.sample.databinding.ActivityMainBinding
import com.customrecyclerviewapp.CustomRecyclerView

import java.util.*

class MainActivity : AppCompatActivity(),CustomRecyclerView.RecyclerViewEventListener {

        private lateinit var binding : ActivityMainBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding= ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.recyclerView.adapter= SampleAdapter()
            binding.recyclerView.listener=this

            binding.recyclerView.startShimmer()

            Handler().postDelayed(Runnable {
                binding.recyclerView.apply {
                    stopShimmer()
                    validateRecyclerViewData(1)
                }
            },2000)

        }

        override fun onRefreshButtonClicked() {
            binding.recyclerView.startShimmer()
            Handler().postDelayed(Runnable {
                binding.recyclerView.apply {
                    stopShimmer()
                    showErrorText("invalid api format")
                }
            },2000)

        }

        override fun onSwipeRefresh() {
            binding.recyclerView.hideSwipeRefresh()
            binding.recyclerView.startShimmer()
            Handler().postDelayed(Runnable {
                binding.recyclerView.apply {
                    stopShimmer()
                    validateRecyclerViewData(Random().nextInt(1))
                }
            },2000)
        }
}