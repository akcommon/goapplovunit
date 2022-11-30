package com.goapplovunit

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.LinearLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.google.android.gms.ads.*


fun Activity.requestBanner(
    placement: String,
    callBack: (layout: LinearLayout?, status: String) -> Unit
) {
    val adView = AdView(this)
    adView.setAdSize(getAdSize())
    adView.adUnitId = placement
    adView.loadAd(
        AdRequest.Builder()
            .build()
    )
    adView.adListener = object : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            val layout = LinearLayout(this@requestBanner)
            layout.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(adView)
            callBack.invoke(layout, ON_AD_LOADED)
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            super.onAdFailedToLoad(loadAdError)
            callBack.invoke(null, loadAdError.toString())
        }
    }

}

fun Activity.getAdSize(): AdSize {
    // Determine the screen width (less decorations) to use for the ad width.
    val display = windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)
    val widthPixels = outMetrics.widthPixels.toFloat()
    val density = outMetrics.density
    val adWidth = (widthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
}

fun Activity.requestAppLovinBanner(
    placement: String,
    callBack: (layout: LinearLayout?, status: String) -> Unit
) {
    val adView = MaxAdView(placement, this)
    adView.setListener(object : MaxAdViewAdListener {
        override fun onAdLoaded(ad: MaxAd?) {
            val layout = LinearLayout(this@requestAppLovinBanner)
            layout.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            layout.orientation = LinearLayout.VERTICAL
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val heightPx = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._45sdp)
            adView.layoutParams = LinearLayout.LayoutParams(width, heightPx)
            layout.addView(adView)
            callBack.invoke(layout, ON_AD_LOADED)
        }

        override fun onAdDisplayed(ad: MaxAd?) {

        }

        override fun onAdHidden(ad: MaxAd?) {

        }

        override fun onAdClicked(ad: MaxAd?) {

        }

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
            callBack.invoke(
                null,
                "error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}"
            )
        }

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
            callBack.invoke(
                null,
                "error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}"
            )
        }

        override fun onAdExpanded(ad: MaxAd?) {

        }

        override fun onAdCollapsed(ad: MaxAd?) {

        }
    })
    adView.loadAd()
}
