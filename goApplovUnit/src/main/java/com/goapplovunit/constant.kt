package com.goapplovunit

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.applovin.sdk.AppLovinSdk
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var idNative = "/6499/example/native"
var idBanner = "/6499/example/banner"
var idFullScreen = "/6499/example/interstitial"
var idAppOpen = "/6499/example/app-open"

const val ON_AD_LOADED="loaded"
const val ON_AD_FAIL="fail"

fun Context.initApplovin() {
    AppLovinSdk.getInstance(this).mediationProvider = "max"
    AppLovinSdk.getInstance(this).initializeSdk {}
}

fun Context.initialize() {
    MobileAds.initialize(this)
}


fun testDeviceIds(ids: List<String>) {
    val builder = RequestConfiguration.Builder().setTestDeviceIds(ids).build()
    MobileAds.setRequestConfiguration(builder)
}

