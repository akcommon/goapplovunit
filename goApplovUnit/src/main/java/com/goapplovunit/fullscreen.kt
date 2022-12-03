package com.goapplovunit

import android.app.Activity
import android.content.Context
import android.util.Log
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


private var mInterstitialAd = arrayOfNulls<InterstitialAd?>(2)
private var applovineInterstitialAd = arrayOfNulls<MaxInterstitialAd?>(2)

fun Context.request(placement: String, isExitAds: Boolean, callBack: (status: String) -> Unit) {
    InterstitialAd.load(
        this,
        placement,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                if (isExitAds)
                    mInterstitialAd[1] = null
                else
                    mInterstitialAd[0] = null
                callBack.invoke(adError.toString())
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                if (isExitAds)
                    mInterstitialAd[1] = interstitialAd
                else
                    mInterstitialAd[0] = interstitialAd
            }
        })
}


fun Activity.requestApplovine(id: String, isExitAds: Boolean, callBack: (status: String) -> Unit) {
    if ((if (isExitAds) applovineInterstitialAd[1] == null else applovineInterstitialAd[0] == null)) {
        if (isExitAds) applovineInterstitialAd[1] = MaxInterstitialAd(id, this)
        else applovineInterstitialAd[0] = MaxInterstitialAd(id, this)
        if (isExitAds) applovineInterstitialAd[1]?.setListener(
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
                    applovineInterstitialAd[1] = null
                    callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

                }
            })
        else applovineInterstitialAd[0]?.setListener(
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
                    applovineInterstitialAd[0] = null
                    callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
                }

                override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

                }
            })

        if (isExitAds) applovineInterstitialAd[1]?.loadAd() else applovineInterstitialAd[0]?.loadAd()
    }

}


fun isExitLoaded(): Boolean {
    return mInterstitialAd[1] != null || (applovineInterstitialAd[1] != null && applovineInterstitialAd[1]?.isReady == true)
}

fun isLoaded(): Boolean {
    return mInterstitialAd[0] != null || (applovineInterstitialAd[0] != null && applovineInterstitialAd[0]?.isReady == true)
}


fun Activity.showExit(callBack: () -> Unit) {
    if (applovineInterstitialAd[1] != null && applovineInterstitialAd[1]?.isReady == true) {
        applovineInterstitialAd[1]?.showAd()
        applovineInterstitialAd[1]?.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd?) {

            }

            override fun onAdDisplayed(ad: MaxAd?) {

            }

            override fun onAdHidden(ad: MaxAd?) {
                applovineInterstitialAd[1] = null
                callBack.invoke()
            }

            override fun onAdClicked(ad: MaxAd?) {

            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {

            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

            }
        })
    } else if (isLoaded()) {
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

fun Activity.show(placementKey: String, callBack: () -> Unit) {
    if (applovineInterstitialAd[0] != null && applovineInterstitialAd[0]?.isReady == true) {
        applovineInterstitialAd[0]?.showAd()
        applovineInterstitialAd[0]?.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd?) {

            }

            override fun onAdDisplayed(ad: MaxAd?) {

            }

            override fun onAdHidden(ad: MaxAd?) {
                applovineInterstitialAd[0] = null
                callBack.invoke()
            }

            override fun onAdClicked(ad: MaxAd?) {

            }

            override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {

            }

            override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {

            }
        })
    } else if (isLoaded()) {
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

