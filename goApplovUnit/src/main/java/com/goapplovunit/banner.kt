package com.goapplovunit

import android.annotation.SuppressLint
import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAdView


fun Activity.requestNativeBanner(
    placement: String,
    layout: LinearLayout?,
    btnColor: Int,
    btnTxtColor: Int,
    callBack: (status: String) -> Unit
) {
    AdLoader.Builder(
        this,
        placement
    )
        .forNativeAd { nativeAd ->
            if (this != null && !this.isFinishing) {
                @SuppressLint("InflateParams") val adView =
                    this.layoutInflater
                        .inflate(
                            R.layout.ad_unified,
                            null
                        ) as NativeAdView

                if (layout != null) {
                    populateUnifiedNativeAdView(nativeAd, adView)
                    layout.removeAllViews()

                    val btn = adView.findViewById<AppCompatButton>(R.id.ad_call_to_action)
                    btn.setBackgroundResource(btnColor)
                    btn.setTextColor(btnTxtColor)
                    layout.addView(adView)
                    adView.bringToFront()
                    layout.invalidate()
                }
            }
        }
        .withAdListener(object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                callBack.invoke(ON_AD_LOADED)
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                callBack.invoke(loadAdError.toString())
            }

        })
        .build()
        .loadAd(AdRequest.Builder().build())
}


fun Activity.requestBanner(placement: String, layout: LinearLayout?, callBack: (status: String) -> Unit) {
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
            if (layout != null) {
                layout?.removeAllViews()
                layout?.addView(adView)
                callBack.invoke(ON_AD_LOADED)
            } else {
                callBack.invoke(ON_AD_FAIL)
            }
        }

        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            super.onAdFailedToLoad(loadAdError)
            if (layout != null) {
                layout?.removeAllViews()
            }
            callBack.invoke(loadAdError.toString())
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


fun populateUnifiedNativeAdView(
    nativeAd: com.google.android.gms.ads.nativead.NativeAd,
    adView: NativeAdView
) {
    val vc = nativeAd.mediaContent
    vc?.videoController?.videoLifecycleCallbacks =
        object : VideoController.VideoLifecycleCallbacks() {
        }

    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.priceView = adView.findViewById(R.id.ad_price)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)
    adView.storeView = adView.findViewById(R.id.ad_store)

    (adView.headlineView as TextView).text = nativeAd.headline
    (adView.bodyView as TextView).text = nativeAd.body
    (adView.callToActionView as AppCompatButton).text = nativeAd.callToAction

    if (nativeAd.icon == null) {
        adView.iconView?.visibility = View.GONE
    } else {
        (adView.iconView as ImageView).setImageDrawable(
            nativeAd.icon?.drawable
        )
        adView.iconView?.visibility = View.VISIBLE
    }
    if (nativeAd.price == null) {
        adView.priceView?.visibility = View.GONE
    } else {
        adView.priceView?.visibility = View.VISIBLE
        (adView.priceView as TextView).text = nativeAd.price
    }
    if (nativeAd.store == null) {
        adView.storeView?.visibility = View.GONE
    } else {
        adView.storeView?.visibility = View.VISIBLE
        (adView.storeView as TextView).text = nativeAd.store
    }
    if (nativeAd.starRating == null) {
        adView.starRatingView?.visibility = View.GONE
    } else {
        (adView.starRatingView as RatingBar).rating = nativeAd.starRating?.toFloat() ?: .0f
        adView.starRatingView?.visibility = View.VISIBLE
    }

    adView.setNativeAd(nativeAd)
}

fun Activity.requestAppLovinBanner(
    placement: String,
    layout: LinearLayout?,
    callBack: (status: String) -> Unit
) {
    val adView = MaxAdView(placement, this)
    adView.setListener(object : MaxAdViewAdListener {
        override fun onAdLoaded(ad: MaxAd?) {
            try {
                layout?.removeAllViews()
            } catch (e: Exception) {
            }
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val heightPx = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._45sdp)
            adView.layoutParams = LinearLayout.LayoutParams(width, heightPx)
            layout?.addView(adView)
            callBack.invoke(ON_AD_LOADED)
        }

        override fun onAdDisplayed(ad: MaxAd?) {

        }

        override fun onAdHidden(ad: MaxAd?) {

        }

        override fun onAdClicked(ad: MaxAd?) {

        }

        override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
            callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
        }

        override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
            callBack.invoke("error code =${error?.code} message=${error?.message} mediatedNetworkErrorCode=${error?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${error?.mediatedNetworkErrorMessage}")
        }

        override fun onAdExpanded(ad: MaxAd?) {

        }

        override fun onAdCollapsed(ad: MaxAd?) {

        }
    })
    adView.loadAd()
}
