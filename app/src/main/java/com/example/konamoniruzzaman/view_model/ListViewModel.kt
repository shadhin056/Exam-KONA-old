package com.example.konamoniruzzaman.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.shadhin.android_jetpack.view.model.CustomerDatabase
import com.shadhin.android_jetpack.view.model.CutomerModel
import com.shadhin.android_jetpack.view.view_model.BaseViewModel
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {
    val users = MutableLiveData<List<CutomerModel>>()

    fun fetchFromDB() {

        launch {
            val customers = CustomerDatabase(getApplication()).customerDao().getAllUser()
            users.value=customers

           // Toast.makeText(getApplication(), "Fetch From Database", Toast.LENGTH_LONG).show()
        }

    }
    override fun onCleared() {
        super.onCleared()
        //disposable.clear()
    }
}
