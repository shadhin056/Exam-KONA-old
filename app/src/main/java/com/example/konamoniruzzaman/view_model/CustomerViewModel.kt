package com.shadhin.android_jetpack.view.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.shadhin.android_jetpack.view.model.CustomerDatabase
import com.shadhin.android_jetpack.view.model.CutomerModel
import kotlinx.coroutines.launch


class CustomerViewModel(application: Application) : BaseViewModel(application) {

    val customer = MutableLiveData<String>()


    override fun onCleared() {
        super.onCleared()
        // disposable.clear()
    }

    fun storeCutomerLocally(user: CutomerModel) {
        launch {
            val cus = CustomerDatabase(getApplication()).customerDao()
            val result = cus.insertAll(user)
            customer.value="Customer information added successfully "+result
            Toast.makeText(getApplication(), "Added "+result, Toast.LENGTH_LONG).show()
        }
    }
}
