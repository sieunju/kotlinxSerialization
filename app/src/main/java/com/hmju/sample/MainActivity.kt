package com.hmju.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textfield.TextInputEditText
import com.hmju.sample.qualifiers.GsonApiService
import com.hmju.sample.qualifiers.KotlinxApiService
import com.hmju.sample.qualifiers.MoshiApiService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @GsonApiService
    lateinit var gsonApiService: ApiService

    @Inject
    @MoshiApiService
    lateinit var moshiApiService: ApiService

    @Inject
    @KotlinxApiService
    lateinit var kotlinxApiService: ApiService

    private val etGson : TextInputEditText by lazy { findViewById(R.id.etGson) }
    private val etMoshi : TextInputEditText by lazy { findViewById(R.id.etMoshi) }
    private val etKotlinx : TextInputEditText by lazy { findViewById(R.id.etKotlinx) }
    private val bGson: Button by lazy { findViewById(R.id.bGson) }
    private val bMoshi: Button by lazy { findViewById(R.id.bMoshi) }
    private val bKotlinx: Button by lazy { findViewById(R.id.bKotlinx) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bGson.setOnClickListener {
            fetchSampleGson()
        }

        bMoshi.setOnClickListener {
            fetchSampleMoshi()
        }

        bKotlinx.setOnClickListener {
            fetchSampleKotlinx()
        }
    }

    private fun fetchSampleGson() {
        gsonApiService.fetchGsonSample()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                etGson.setText(it.toString(), TextView.BufferType.NORMAL)
            }, {
                etGson.setText(it.toString(), TextView.BufferType.NORMAL)
            })
    }

    private fun fetchSampleMoshi() {
        moshiApiService.fetchMoshiSample()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                etMoshi.setText(it.toString(), TextView.BufferType.NORMAL)
            }, {
                etMoshi.setText(it.toString(), TextView.BufferType.NORMAL)
            })
    }

    private fun fetchSampleKotlinx() {
        kotlinxApiService.fetchKotlinxSample()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                etKotlinx.setText(it.toString(), TextView.BufferType.NORMAL)
            }, {
                etKotlinx.setText(it.toString(), TextView.BufferType.NORMAL)
            })
    }
}