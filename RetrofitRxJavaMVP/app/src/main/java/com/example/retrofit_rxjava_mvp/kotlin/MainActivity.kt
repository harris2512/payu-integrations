package com.example.retrofit_rxjava_mvp.kotlin

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_rxjava_mvp.R
import com.example.retrofit_rxjava_mvp.java.models.LoginStatusModel
import com.example.retrofit_rxjava_mvp.java.models.ResponseModel
import com.example.retrofit_rxjava_mvp.java.models.VendorList
import com.example.retrofit_rxjava_mvp.java.ui.main.DataAdapter
import com.example.retrofit_rxjava_mvp.java.utils.RAUtils
import com.example.retrofit_rxjava_mvp.kotlin.main.MainContractor
import com.example.retrofit_rxjava_mvp.kotlin.main.MainPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class MainActivity : AppCompatActivity(), MainContractor.View {

    lateinit var mRecyclerView: RecyclerView
    private var mAdapter: DataAdapter? = null
    private var mAndroidArrayList: ArrayList<ResponseModel>? = null
    lateinit var mPresenter: MainPresenter
    var mAuthToken =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiTW9oYW1tZWQgSGFycmlzIiwiaWQiOjE4LCJlbWFpbCI6ImhhcnJpc0ByZWFkeWFzc2lzdC5pbiIsIm1vYmlsZU5vIjoiOTUwMDM0ODMwNyIsIlJvbGVOYW1lIjoiQWRtaW4iLCJ2ZW5kb3JJZCI6bnVsbCwiaWF0IjoxNjAwNjc1MTk0LCJleHAiOjE2MDMyNjcxOTR9.IlLAGtmeQeoqDW7a19cnr51kWskbGEjDl4fDmY19XNQ"
    private var activeVendorList: List<VendorList>? = null
   // lateinit var img_sampleImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter()
        mPresenter.attachView(this)

        initRecyclerView()

        loadVendorList()

        getImageDownload()

    }

    private fun getImageDownload() {
        //  showPb();
        mPresenter.getImageDownload(mAuthToken, 0)
    }

    private fun loadVendorList() {
        val filterObject = JSONObject()
        val whereDataObject = JSONObject()
        val includeDataArray = JSONArray()
        val includeTeamObject = JSONObject()
        val includeShiftObject = JSONObject()
        try {
            whereDataObject.put("isActive", true)
            includeTeamObject.put("relation", "team")
            includeShiftObject.put("relation", "shift")
            // append it to your JSON array.
            includeDataArray.put(includeTeamObject)
            includeDataArray.put(includeShiftObject)
            filterObject.put("skip", 0)
            filterObject.put("limit", 10)
            filterObject.put("where", whereDataObject)
            filterObject.put("include", includeDataArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val data: MutableMap<String?, String?> = HashMap()
        data["filter"] = filterObject.toString()

        showPb()
        mPresenter.getVendorList(mAuthToken, data)
    }

    private fun initRecyclerView() {
        recycler_view.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recycler_view.setLayoutManager(layoutManager)
    }

    override fun handleResponse(response: List<ResponseModel?>?) {
        if (response != null) {
            mAndroidArrayList = ArrayList(response)
            // mAdapter = new DataAdapter(mAndroidArrayList);
            //    mRecyclerView.setAdapter(mAdapter);
        }
    }

    override fun setVendorList(response: LoginStatusModel?) {
        if (response!!.statusCode == 200) {
            activeVendorList = ArrayList(response.vendorList)
          //  mAdapter = DataAdapter(activeVendorList)
            recycler_view.adapter = mAdapter
        }
    }

    override fun setImageDownload(byteStream: InputStream?) {
        val bm = BitmapFactory.decodeStream(byteStream)
        img_sampleImage.setImageBitmap(bm)
    }

    override fun dismissPb() {
        RAUtils.dismissProgressDialog()
    }

    override fun showPb() {
        RAUtils.showProgressDialog(this, true)
    }

    override fun showToast(message: String?) {
//         Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Snackbar.make(window.decorView.rootView, message!!, Snackbar.LENGTH_LONG).show()
    }

    override fun onFailure(call: Any?, throwable: Any?, callback: Any?, additionalData: Any?) {
        if (throwable is SocketTimeoutException) {
            //  showToast("Socket Time out. Please try again.");
            showRetryDialog(call, callback, resources.getString(R.string.no_internet))
        } else if (throwable is UnknownHostException) {
            // showToast("No Internet");
            showRetryDialog(call, callback, resources.getString(R.string.no_internet))
        } else if (throwable is ConnectException) {
            showRetryDialog(call, callback, resources.getString(R.string.no_internet))
        } else {
            showToast(resources.getString(R.string.api_no_response))
        }
    }

    override fun showRetryDialog(object1: Any?, object3: Any?, message: String?) {
        showPb()
        RAUtils.showRetryDialog(object1, object3, this, message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }

}