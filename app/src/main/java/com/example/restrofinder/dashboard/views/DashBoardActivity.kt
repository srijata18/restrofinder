package com.example.restrofinder.dashboard.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restrofinder.R
import com.example.restrofinder.dashboard.dataModel.Venues
import com.example.restrofinder.dashboard.viewModel.DashboardViewModel
import com.example.restrofinder.databinding.ActivityMainBinding
import com.example.restrofinder.utils.AppInitializer
import com.example.restrofinder.utils.CustomAlertDialog
import com.example.restrofinder.utils.FoodyThoughts
import com.example.restrofinder.view.base.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/*
* Launching activity, which is resonsible for displaying the list of restaurants based on the
* user requirements. Search functionality is also added which can enhance the users need for  a
* particular restaurant.
* */
class DashBoardActivity : BaseActivity<ActivityMainBinding, DashboardViewModel>(),
    ItemSelected {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main

    @Inject
    override lateinit var viewModel: DashboardViewModel
        internal set

    private var venueList = arrayListOf<Venues>()
    private var activityMainBinding: ActivityMainBinding? = null
    private var allAdapter: ScrollAdapter?=null
    var isUserAction = false
    val locationList = AppInitializer.locationList
    val locationMap = AppInitializer.locationMap
    var location = AppInitializer.location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = viewDataBinding
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        addDefaultLocation()
        setAdapter()
        setLiveListeners()
        loadData()
        setListeners()
    }

    private fun addDefaultLocation(){
        locationMap["AMERICAN"]="4bf58dd8d48988d14e941735"
        locationMap["AFGHAN"]="503288ae91d4c4b30a586d67"
        locationMap["CHINESE"]="52af3a903cf9994f4e043bee"
        location = "4bf58dd8d48988d14e941735"
    }

    private fun setListeners(){
        et_search?.imeOptions = EditorInfo.IME_ACTION_DONE
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.helpline), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        srl_dashboard?.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                loadData()
            }
        })
        tv_what_to_eat?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (tv_what_to_eat.right - tv_what_to_eat.compoundDrawables[2].bounds.width())) {
                    Toast.makeText(this,getString(R.string.notification),Toast.LENGTH_SHORT).show()
                    return@OnTouchListener true
                }
            }
            true
        })
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().length >=2) {
                    filterUI()
                    filter(editable.toString())
                }else{
                    normalUI()
                }
            }
        })
    }
    private fun filterUI(){
        srl_dashboard?.isEnabled = false
        tv_updateLocation?.visibility = View.GONE
        tv_nearby?.visibility = View.GONE
        loc_spinner?.visibility = View.GONE
    }

    private fun normalUI(){
        srl_dashboard?.isEnabled = true
        tv_updateLocation?.visibility = View.VISIBLE
        tv_nearby?.visibility = View.VISIBLE
        loc_spinner?.visibility = View.VISIBLE
        updateAdapterData()
    }

    private fun filter(text: String) {
        val filterdNames: ArrayList<Venues> = ArrayList()
        for (s in venueList) {
            if (s.name?.toLowerCase(Locale.ROOT)?.contains(text.toLowerCase(Locale.ROOT))==true) {
                filterdNames.add(s)
            }
        }
        allAdapter?.updateAdapter(filterdNames)
    }

    private fun setAdapter(){
        val mLayoutManager = GridLayoutManager(this,2)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        allAdapter = ScrollAdapter(venueList, this, this)
        activityMainBinding?.apply {
            rv_allrestro_list?.layoutManager = mLayoutManager
            rv_allrestro_list?.adapter = allAdapter
            rv_allrestro_list?.isNestedScrollingEnabled = false
        }
        allAdapter?.notifyDataSetChanged()

        val adapter = CustomSpinnerAdapter(this,locationList)
        loc_spinner?.adapter = adapter
        loc_spinner?.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if( isUserAction ) {
                    if (locationMap.containsKey(locationList[position])){
                        location = locationMap.get(locationList[position]) ?: "4bf58dd8d48988d14e941735"
                        AppInitializer.location = location
                        loadData()
                    }else{
                        location = "4bf58dd8d48988d14e941735"
                        AppInitializer.location = location
                        loadData()
                    }

                }
                isUserAction = true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun loadData(){
        if (checkForInternet()) {
            hideEmptyScreen()
            showHideProgress(true)
            viewModel.fetchDetails()
        }else{
            Snackbar.make(srl_dashboard, getString(R.string.nointernet), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            showHideProgress(false)
            if (venueList.isEmpty())
            showEmptyScreen()
        }
    }
    private fun setLiveListeners(){
        viewModel.receivedResponse.observe(this, Observer {
            Log.i("res",it.response?.venues?.get(0)?.name?:"")
            val list = arrayListOf<Venues>()
            it?.response?.venues?.let { it1 -> list.addAll(it1) }
            venueList = list
            updateAdapterData()
            allAdapter?.notifyDataSetChanged()
            showHideProgress(false)
        })
        viewModel.errorModel.observe(this, Observer {
            it?.message?.let { it1 -> CustomAlertDialog.displayAlert(this, it1,getString(R.string.ok)) }
            if (venueList.isEmpty())
            showEmptyScreen()
            showHideProgress(false)
        })
    }

    fun clicked(view : View?){
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        et_search?.requestFocus()
    }

    private fun showHideProgress(toShow : Boolean){
        if(toShow){
            if (srl_dashboard?.isRefreshing==false) {
                foody_msg?.text = FoodyThoughts.displayFoodThoughts()
                pb_progress?.visibility = View.VISIBLE
                foody_msg?.visibility = View.VISIBLE
            }
        }else{
            foody_msg?.visibility = View.GONE
            pb_progress?.visibility = View.GONE
            srl_dashboard?.isRefreshing = false
        }
    }

    override fun onItemSelected(isThumbsDown : Boolean, variantName : Venues?) {
        if (et_search?.text?.isNullOrBlank()==true) {
            if (isThumbsDown) {
                CustomAlertDialog.displayAlert(this,
                    "${variantName?.name} is removed from search list",
                    getString(R.string.ok))
                venueList.remove(variantName)
                updateAdapterData()
            }else
                Toast.makeText(this,getString(R.string.to_be_added),Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEmptyScreen(){
        layout_emptystate?.visibility = View.VISIBLE
        tv_updateLocation?.visibility = View.GONE
        tv_what_to_eat?.visibility = View.GONE
        et_search?.visibility = View.GONE
        rv_allrestro_list?.visibility = View.GONE
        tv_nearby?.visibility = View.GONE
        loc_spinner?.visibility = View.GONE
    }

    private fun hideEmptyScreen(){
        layout_emptystate?.visibility = View.GONE
        tv_updateLocation?.visibility = View.VISIBLE
        tv_what_to_eat?.visibility = View.VISIBLE
        et_search?.visibility = View.VISIBLE
        rv_allrestro_list?.visibility = View.VISIBLE
        tv_nearby?.visibility = View.VISIBLE
        loc_spinner?.visibility = View.VISIBLE
    }

    fun updateAdapterData(){
        allAdapter?.updateAdapter(venueList)
        if (venueList.isNotEmpty()){
            rv_allrestro_list?.visibility = View.VISIBLE
            foody_msg?.visibility = View.GONE
        }
        else{
            showNoData()
        }
    }
    private fun showNoData() {
        foody_msg?.visibility = View.VISIBLE
        foody_msg?.text = getString(R.string.emptyList)
        rv_allrestro_list?.visibility = View.GONE
    }
}
