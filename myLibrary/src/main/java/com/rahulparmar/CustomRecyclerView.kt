package com.rahulparmar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customrecyclerviewapp.R
import com.customrecyclerviewapp.databinding.LayoutRecyclerViewWithSwipToRefreshBinding

class CustomRecyclerView : ConstraintLayout {

    constructor(context: Context) : super(context) {
        setupUI(context,null,0)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupUI(context,attrs,0)
    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupUI(context,attrs,defStyleAttr)
    }
    private lateinit var binding : LayoutRecyclerViewWithSwipToRefreshBinding

    var swipeRefreshEnabled : Boolean = true

        set(value){
            
            field =value
            binding.swipeRefresh.isEnabled=field
        }

    private var showProgress : Boolean = false
        set(value){
            
            field =value
            binding.progressBar.apply {
                if (field) visible() else gone()
            }
        }

    private var showSwipeRefresh : Boolean = false
        set(value) {
        field=value
        binding.swipeRefresh.apply {
            isRefreshing=field
        }
    }

    private var showShimmer : Boolean = false
        set(value) {
            
            field=value
            binding.recyclerView.apply {
                if (field) showShimmer() else hideShimmer()
            }
        }

    private var showEmpty : Boolean = false
        set(value) {
            
            field=value
            binding.layoutNoData.apply {
                if (field) visible() else gone()
            }
        }

    private var showError : Boolean = false
        set(value) {
            
            field=value
            binding.txtViewError.apply {
                if (field) visible() else gone()
            }
        }

    var recyclerView : RecyclerView? = null

    var listener : RecyclerViewEventListener?=null
    set(value) {
        
        field=value
    }


    var adapter : RecyclerView.Adapter<*>? = null
        set(value) {
            
        field=value
        binding.recyclerView.adapter=field
    }

    var linearLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        set(value) {
            
        field=value
        binding.recyclerView.layoutManager=field
    }

    var shimmerLayout : Int = DEF_SHIMMER_LAYOUT
        set(value) {
            
        field=value
        binding.recyclerView.shimmerLayout=field
    }

    var shimmerItems : Int = DEF_SHIMMER_ITEMS
        set(value) {
            
        field=value
        binding.recyclerView.shimmerItemCount=field
    }

    var refreshButtomText : String = DEF_REFRESH_BUTTON_TEXT
        set(value) {
            
        field=value
        binding.btnRetry.text=field
    }

    var errorText : String = DEF_ERROR_TEXT
        set(value) {
            
            field=value
            binding.txtViewError.text=field
        }

    var emptyText : String = DEF_EMPTY_TEXT
        set(value) {
            
            field=value
            binding.layoutNoData.text=field
        }




    private fun setupUI(context: Context, attrs: AttributeSet?, defStyleAttr: Int){
//        inflate(context,R.layout.layout_recycler_view_with_swip_to_refresh,this)
        binding = LayoutRecyclerViewWithSwipToRefreshBinding.inflate(
            LayoutInflater.from(context),this
        )

        var typedarray=context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView)
        swipeRefreshEnabled=typedarray.getBoolean(R.styleable.CustomRecyclerView_swipe_refresh,true)

        when(typedarray.getInt(R.styleable.CustomRecyclerView_layout_manager, VERTICAL_LINEAR_LAYOUT)){
            HORIZONTAL_LINEAR_LAYOUT -> {
                linearLayoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            }
            VERTICAL_LINEAR_LAYOUT -> {
                linearLayoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }
        }

        shimmerLayout=typedarray.getResourceId(R.styleable.CustomRecyclerView_shimmer_layout, DEF_SHIMMER_LAYOUT)
        shimmerItems=typedarray.getInt(R.styleable.CustomRecyclerView_shimmer_items, DEF_SHIMMER_ITEMS)

        refreshButtomText= typedarray.getString(R.styleable.CustomRecyclerView_refresh_button_text) ?: DEF_REFRESH_BUTTON_TEXT
        emptyText= typedarray.getString(R.styleable.CustomRecyclerView_empty_text) ?: DEF_EMPTY_TEXT
        errorText= typedarray.getString(R.styleable.CustomRecyclerView_error_text) ?: DEF_ERROR_TEXT

        recyclerView=binding.recyclerView

        binding.btnRetry.setOnClickListener {
            listener?.onRefreshButtonClicked()
        }

        binding.swipeRefresh.apply {
            setOnRefreshListener {
                if (isRefreshing) listener?.onSwipeRefresh()
            }
        }
        typedarray.recycle()

    }

    private companion object{
        private const val HORIZONTAL_LINEAR_LAYOUT=1
        private const val VERTICAL_LINEAR_LAYOUT=2

        private val DEF_SHIMMER_LAYOUT= R.layout.def_shimmer_layout
        private const val DEF_SHIMMER_ITEMS=10
        private const val DEF_REFRESH_BUTTON_TEXT="Refresh"
        private const val DEF_ERROR_TEXT="Something went wrong"
        private const val DEF_EMPTY_TEXT="No data found"


    }
    interface RecyclerViewEventListener{
        fun onRefreshButtonClicked()
        fun onSwipeRefresh()
    }




    fun hideSwipeRefresh(){
        showSwipeRefresh=false
    }

    private fun showRefreshButton(){
        binding.txtViewError.visible()
        binding.btnRetry.visible()
    }


    private fun hideRefreshButton(){
        binding.txtViewError.gone()
        binding.btnRetry.gone()
    }

    private fun showEmptyText(){
        hideAll()
        invisibleRecyclerView()
        binding.layoutNoData.visible()
    }


    private fun hideEmptyText(){
        binding.layoutNoData.gone()
    }

    fun showErrorText(){
        hideAll()
        binding.txtViewError.text= DEF_ERROR_TEXT
        binding.txtViewError.visible()
        showRefreshButton()
    }

    fun showErrorText(text : String){
        hideAll()
        binding.txtViewError.text=text
        binding.txtViewError.visible()
        showRefreshButton()
    }




    fun startShimmer(){
        showOnlyRecyclerView()
        showSwipeRefresh=false
        showShimmer=true
    }


    fun stopShimmer(){
        showOnlyRecyclerView()
        showSwipeRefresh=false
        showShimmer=false
    }

    fun validateRecyclerViewData(listsize : Int){
        hideAll()
        if (listsize == 0){
            showEmptyText()
        }
        else{
            showOnlyRecyclerView()
        }
    }




    private fun hideAll(){
        showEmpty=false
        showError=false
        showProgress=false
        hideRefreshButton()
        hideRecyclerView()
        hideSwipeRefresh()
    }

     fun startProgress(){
        hideAll()
        showProgress=true
    }

     fun stopProgress(){
        hideAll()
        showOnlyRecyclerView()
    }

    private fun showRecyclerView(){
        binding.swipeRefresh.visible()
        recyclerView?.visible()
    }
    private fun hideRecyclerView(){
        binding.swipeRefresh.gone()
        recyclerView?.gone()
    }
    private fun invisibleRecyclerView(){
        binding.swipeRefresh.visible()
        recyclerView?.invisible()
    }

    private fun showOnlyRecyclerView(){
        showEmpty=false
        showError=false
        showProgress=false
        hideRefreshButton()
        showRecyclerView()
    }






}