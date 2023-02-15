package com.nobilis.cryptocurrency

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nobilis.cryptocurrency.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val tvPrice = binding.tvPrice
        val tvMinPrice = binding.tvMinPrice
        val tvMaxPrice = binding.tvMaxPrice
        val tvLastMarket = binding.tvLastMarket
        val tvLastUpdate = binding.tvLastUpdate
        val tvFromSymbol = binding.tvFromSymbol
        val tvToSymbol = binding.tvToSymbol
        val ivLogoCoin = binding.ivLogoCoin
        viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            tvPrice.text = it.price.toString()
            tvMinPrice.text = it.lowDay.toString()
            tvMaxPrice.text = it.highDay.toString()
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.getFormattedTime()
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}