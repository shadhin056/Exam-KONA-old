package com.example.konamoniruzzaman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.konamoniruzzaman.view_model.ListViewModel
import com.shadhin.android_jetpack.view.model.CutomerModel

class ViewCustomer : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private var recyclerView: RecyclerView? = null
    private var mAdapter: RequestAdapter? = null
    private lateinit var pDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_customer)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)

        recyclerView = findViewById(R.id.rv_users) as RecyclerView
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)


        //data will show after 1 second
        pDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            pDialog.dismiss()
            viewModel.fetchFromDB()
        }, 1000)

        //response when data fetch successfully
        obserViewModel();

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.setLayoutManager(mLayoutManager)
        recyclerView!!.setItemAnimator(DefaultItemAnimator())
    }

    private fun obserViewModel() {


        viewModel.users.observe(this, androidx.lifecycle.Observer { users ->
            users.let {
                Toast.makeText(getApplication(), "Fetch From Database "+users, Toast.LENGTH_SHORT).show()
                mAdapter = RequestAdapter(users)
                recyclerView!!.setAdapter(mAdapter)
                mAdapter?.notifyDataSetChanged();
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent: Intent = Intent(
                    this,
                    MainActivity::class.java
                )
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
    //display customers list
    inner class RequestAdapter(private val requestCustomersList: List<CutomerModel>) :
        RecyclerView.Adapter<RequestAdapter.MyViewHolder>(), View.OnClickListener {

        override fun onClick(v: View) {

        }

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
              var txtName: TextView
              var txtDOB: TextView
              var txtPhone: TextView
              var txtPostalCode: TextView
              var txtPostOffice: TextView
              var txtThana: TextView
              var txtDistrict: TextView

            init {
                txtName = view.findViewById(R.id.txtName) as TextView
                txtDOB = view.findViewById(R.id.txtDOB) as TextView
                txtPhone = view.findViewById(R.id.txtPhone) as TextView
                txtPostalCode = view.findViewById(R.id.txtPostalCode) as TextView
                txtPostOffice = view.findViewById(R.id.txtPostOffice) as TextView
                txtThana = view.findViewById(R.id.txtThana) as TextView
                txtDistrict = view.findViewById(R.id.txtDistrict) as TextView


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_layout_users, parent, false)

            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val requestMoneyModel = requestCustomersList[position]
            holder.txtName.setText(requestMoneyModel.name)
            holder.txtPhone.setText(requestMoneyModel.number)
            holder.txtDOB.setText(requestMoneyModel.dob)
            holder.txtPostalCode.setText(requestMoneyModel.postalCode)
            holder.txtPostOffice.setText(requestMoneyModel.postOffice)
            holder.txtThana.setText(requestMoneyModel.thana)
            holder.txtDistrict.setText(requestMoneyModel.district)
        }

        override fun getItemCount(): Int {
            return requestCustomersList.size
        }
    }
}