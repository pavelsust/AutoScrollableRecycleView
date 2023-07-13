package com.example.testrecycleview

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(){


    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rvNews)

        linearLayoutManager = CenterLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val dataArray : Array<String?> = resources.getStringArray(R.array.list_array)
        newsAdapter = NewsAdapter(dataArray)

        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
        }


//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                recyclerView.post {
//                    selectMiddleItem()
//                }
//            }
//        })

//        var time = Timer()
//        time.schedule(object : TimerTask(){
//            override fun run() {
//                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < newsAdapter.itemCount -1){
//                    linearLayoutManager.smoothScrollToPosition(recyclerView, RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition()+1)
//                }else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < newsAdapter.itemCount -1){
//                    linearLayoutManager.smoothScrollToPosition(recyclerView, RecyclerView.State(), 0)
//                }else{
//
//                }
//
//                Log.d("APP_STATUS", "Calling")
//            }
//
//        }, 3000)



//         val runnable = object : Runnable {
//            var count = 0
//            override fun run() {
//                if (count == rvNews.adapter?.itemCount) count = 0
//                if (count < rvNews.adapter?.itemCount?:-1) {
//                    rvNews.smoothScrollToPosition(++count)
//                    handler.postDelayed(this, speedScroll.toLong())
//                }
//            }
//        }

//        mainHandler.post(object : Runnable {
//            var count = 1
//            override fun run() {
//                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < newsAdapter.itemCount -1){
//                    linearLayoutManager.smoothScrollToPosition(recyclerView, RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition()+1)
//                }else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < newsAdapter.itemCount -1){
//                    linearLayoutManager.smoothScrollToPosition(recyclerView, RecyclerView.State(), 0)
//                }
//                count++
//                Log.d("APP_STATUS", "Code $count")
//                mainHandler.postDelayed(this, 2000)
//            }
//        })

    }


    class CenterSmoothScroller(context: Context?) :
        LinearSmoothScroller(context) {
        override fun calculateDtToFit(
            viewStart: Int,
            viewEnd: Int,
            boxStart: Int,
            boxEnd: Int,
            snapPreference: Int
        ): Int {
            return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
        }
    }

//    inner class CenterLayoutManager : LinearLayoutManager {
//        constructor(context: Context) : super(context)
//        constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
//        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
//
//        override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
//            val centerSmoothScroller = CenterSmoothScroller(recyclerView.context)
//            centerSmoothScroller.targetPosition = position
//            startSmoothScroll(centerSmoothScroller)
//
//        }
//
//        private inner class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
//            override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int = (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)
//        }
//    }

    class CenterLayoutManager : LinearLayoutManager {
        constructor(context: Context) : super(context)
        constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(
            context,
            orientation,
            reverseLayout
        )

        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
            context,
            attrs,
            defStyleAttr,
            defStyleRes
        )

        override fun smoothScrollToPosition(
            recyclerView: RecyclerView,
            state: RecyclerView.State,
            position: Int
        ) {
            val centerSmoothScroller = CenterSmoothScroller(recyclerView.context)
            centerSmoothScroller.targetPosition = position
            startSmoothScroll(centerSmoothScroller)
        }

        private class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
            override fun calculateDtToFit(
                viewStart: Int,
                viewEnd: Int,
                boxStart: Int,
                boxEnd: Int,
                snapPreference: Int
            ): Int = (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2)

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }

        companion object {
            // This number controls the speed of smooth scroll
            private const val MILLISECONDS_PER_INCH = 150f
        }
    }




    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, speedScroll.toLong())
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }



    private val llManager: LinearLayoutManager = object : LinearLayoutManager(this , HORIZONTAL,false) {
        override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int) {
            val smoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(this@MainActivity) {
                    private val SPEED = 4000f // Change this value (default=25f)
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return SPEED
                    }
                }
            smoothScroller.targetPosition = position
            startSmoothScroll(smoothScroller)
        }
    }


    private val handler = Handler()
    private val speedScroll = 0
    private val runnable = object : Runnable {
        var count = 0
        override fun run() {
            if (count == recyclerView.adapter?.itemCount) count = 0
            if (count < recyclerView.adapter?.itemCount?:-1) {
                recyclerView.smoothScrollToPosition(++count)
                handler.postDelayed(this, 2000)
            }
        }
    }

}