package com.nobilis.cryptocurrency

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.nobilis.cryptocurrency.api.ApiFactory
import com.nobilis.cryptocurrency.database.AppDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.CoinPriceInfoDao().getPriceList()

    fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("TEST_OF_LOADING_DATA", it.toString())
                }, {
                    Log.d("TEST_OF_LOADING_DATA", it.message.toString())
                })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}