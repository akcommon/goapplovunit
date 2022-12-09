package com.goapplovunit

import android.app.Activity
import android.content.Context
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


private var mInterstitialAd = arrayOfNulls<InterstitialAd?>(3)
private var applovinInterstitialAd = arrayOfNulls<MaxInterstitialAd?>(3)

fun Context.requestSplash(placement: String, callBack: (status: String) -> Unit) {
    InterstitialAd.load(
        this,
        placement,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd[0] = null
                callBack.invoke(adError.toString())
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd[0] = interstitialAd
            }
        })
}

fun Context.request(placement: String, callBack: (status: String) -> Unit) {
    InterstitialAd.load(
        this,
        placement,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd[1] = null
                callBack.invoke(adError.toString())
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd[1] = interstitialAd
            }
        })
}

fun Context.requestExit(placement: String, callBack: (status: String) -> Unit) {
    InterstitialAd.load(
        this,
        placement,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd[2] = null
                callBack.invoke(adError.toString())
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd[2] = interstitialAd
            }
        })
}


fun Activity.requestApplovinSplash(placement: String, callBack: (status: String) -> Unit) {
    if ((applovinInterstitialAd[0] == null)) {
        applovinInterstitialAd[0] = MaxInterstitialAd(placement, this)
        applovinInterstitialAd[0]?.setListener(
            object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd?) {

                }

                override fun onAdDisplayed(ad: MaxAd?) {

                }

                override fun onAdHidden(ad: MaxAd?) {

                }

                override fun onAdClicked(ad: MaxAd?) {

                }

                override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                    applovinInterstitialAd[0] = null
                    callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

                }
            })
        applovinInterstitialAd[0]?.loadAd()
    }

}

fun Activity.requestApplovin(placement: String, callBack: (status: String) -> Unit) {
    if ((applovinInterstitialAd[1] == null)) {
        applovinInterstitialAd[1] = MaxInterstitialAd(placement, this)
        applovinInterstitialAd[1]?.setListener(
            object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd?) {

                }

                override fun onAdDisplayed(ad: MaxAd?) {

                }

                override fun onAdHidden(ad: MaxAd?) {

                }

                override fun onAdClicked(ad: MaxAd?) {

                }

                override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                    applovinInterstitialAd[1] = null
                    callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

                }
            })
        applovinInterstitialAd[1]?.loadAd()
    }

}

fun Activity.requestApplovinExit(placement: String, callBack: (status: String) -> Unit) {
    if ((applovinInterstitialAd[2] == null)) {
        applovinInterstitialAd[2] = MaxInterstitialAd(placement, this)
        applovinInterstitialAd[2]?.setListener(
            object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd?) {

                }

                override fun onAdDisplayed(ad: MaxAd?) {

                }

                override fun onAdHidden(ad: MaxAd?) {

                }

                override fun onAdClicked(ad: MaxAd?) {

                }

                override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
                    applovinInterstitialAd[2] = null
                    callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

                }
            })
        applovinInterstitialAd[2]?.loadAd()
    }

}


fun isSplashLoaded(): Boolean {
    return mInterstitialAd[0] != null || (applovinInterstitialAd[0] != null && applovinInterstitialAd[0]?.isReady == true)
}

fun isLoaded(): Boolean {
    return mInterstitialAd[1] != null || (applovinInterstitialAd[1] != null && applovinInterstitialAd[1]?.isReady == true)
}

fun isExitLoaded(): Boolean {
    return mInterstitialAd[2] != null || (applovinInterstitialAd[2] != null && applovinInterstitialAd[2]?.isReady == true)
}


fun Activity.showSplash(callBack: () -> Unit) {
    if (applovinInterstitialAd[0] != null && applovinInterstitialAd[0]?.isReady == true) {
        applovinInterstitialAd[0]?.showAd()
        applovinInterstitialAd[0]?.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd?) {

            }

            override fun onAdDisplayed(ad: MaxAd?) {

            }

            override fun onAdHidden(ad: MaxAd?) {
                applovinInterstitialAd[0] = null
                callBack.invoke()
            }

            override fun onAdClicked(ad: MaxAd?) {

            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {

            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

            }
        })
    } else if (mInterstitialAd[0] != null) {
        mInterstitialAd[0]?.show(this)
        mInterstitialAd[0]?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd[0] = null
                callBack.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mInterstitialAd[0] = null
                callBack.invoke()
            }
        }
    } else {
        callBack.invoke()
    }
}

fun Activity.show(callBack: () -> Unit) {
    if (applovinInterstitialAd[1] != null && applovinInterstitialAd[1]?.isReady == true) {
        applovinInterstitialAd[1]?.showAd()
        applovinInterstitialAd[1]?.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd?) {

            }

            override fun onAdDisplayed(ad: MaxAd?) {

            }

            override fun onAdHidden(ad: MaxAd?) {
                applovinInterstitialAd[1] = null
                callBack.invoke()
            }

            override fun onAdClicked(ad: MaxAd?) {

            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {

            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

            }
        })
    } else if (mInterstitialAd[1] != null) {
        mInterstitialAd[1]?.show(this)
        mInterstitialAd[1]?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd[1] = null
                callBack.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mInterstitialAd[1] = null
                callBack.invoke()
            }
        }
    } else {
        callBack.invoke()
    }
}

fun Activity.showExit(callBack: () -> Unit) {
    if (applovinInterstitialAd[2] != null && applovinInterstitialAd[2]?.isReady == true) {
        applovinInterstitialAd[2]?.showAd()
        applovinInterstitialAd[2]?.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd?) {

            }

            override fun onAdDisplayed(ad: MaxAd?) {

            }

            override fun onAdHidden(ad: MaxAd?) {
                applovinInterstitialAd[2] = null
                callBack.invoke()
            }

            override fun onAdClicked(ad: MaxAd?) {

            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {

            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

            }
        })
    } else if (mInterstitialAd[2] != null) {
        mInterstitialAd[2]?.show(this)
        mInterstitialAd[2]?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd[2] = null
                callBack.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mInterstitialAd[2] = null
                callBack.invoke()
            }
        }
    } else {
        callBack.invoke()
    }
}

