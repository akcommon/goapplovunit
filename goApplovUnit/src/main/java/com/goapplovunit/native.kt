package com.goapplovunit

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.VideoController.VideoLifecycleCallbacks
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView


fun Activity.requestNative(
    vararg color: Int,
    placement: String,
    callBack: (layout: LinearLayout?, status: String) -> Unit
) {
    if (color.size == 4) {
        var layout: LinearLayout? = null

        AdLoader.Builder(
            this,
            placement
        )
            .forNativeAd { nativeAd ->

                layout = LinearLayout(this)
                layout?.layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                layout?.orientation = LinearLayout.VERTICAL

                val adView = layoutInflater
                    .inflate(R.layout.ad_unified_large, null) as NativeAdView

                val btn = adView.findViewById<AppCompatButton>(R.id.ad_call_to_action)
                val adHeadline = adView.findViewById<AppCompatTextView>(R.id.ad_headline)
                val adBody = adView.findViewById<AppCompatTextView>(R.id.ad_body)

                btn.setBackgroundResource(color[0])
                btn.setTextColor(ContextCompat.getColor(this, color[1]))

                adHeadline.setTextColor(ContextCompat.getColor(this, color[2]))

                adBody.setTextColor(ContextCompat.getColor(this, color[3]))

                populateUnifiedNativeAdViewLarge(nativeAd, adView)
                layout?.removeAllViews()
                layout?.addView(adView)
                adView.bringToFront()
                layout?.invalidate()
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    callBack.invoke(null, loadAdError.toString())
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    callBack.invoke(layout, ON_AD_LOADED)
                }
            })
            .build()
            .loadAd(AdRequest.Builder().build())
    } else {
        callBack.invoke(null, "Color array must be size 4")
    }
}

private fun populateUnifiedNativeAdViewLarge(nativeAd: NativeAd, adView: NativeAdView) {
    val vc = nativeAd.mediaContent
    vc!!.videoController.videoLifecycleCallbacks = object : VideoLifecycleCallbacks() {
        override fun onVideoEnd() {
            super.onVideoEnd()
        }
    }
    val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
    adView.mediaView = mediaView
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.priceView = adView.findViewById(R.id.ad_price)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)
    adView.storeView = adView.findViewById(R.id.ad_store)
    adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

    // Some assets are guaranteed to be in every UnifiedNativeAd.
    (adView.headlineView as AppCompatTextView?)!!.text = nativeAd.headline
    (adView.bodyView as AppCompatTextView?)!!.text = nativeAd.body
    (adView.callToActionView as AppCompatButton?)!!.text = nativeAd.callToAction

    // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
    // check before trying to display them.
    if (nativeAd.icon == null) {
        adView.iconView!!.visibility = View.GONE
    } else {
        (adView.iconView as ImageView?)!!.setImageDrawable(
            nativeAd.icon!!.drawable
        )
        adView.iconView!!.visibility = View.VISIBLE
    }
    if (nativeAd.price == null) {
        adView.priceView!!.visibility = View.GONE
    } else {
        adView.priceView!!.visibility = View.VISIBLE
        (adView.priceView as AppCompatTextView?)!!.text = nativeAd.price
    }
    if (nativeAd.store == null) {
        adView.storeView!!.visibility = View.GONE
    } else {
        adView.storeView!!.visibility = View.VISIBLE
        (adView.storeView as AppCompatTextView?)!!.text = nativeAd.store
    }
    if (nativeAd.starRating == null) {
        adView.starRatingView!!.visibility = View.GONE
    } else {
        (adView.starRatingView as RatingBar?)
            ?.setRating(nativeAd.starRating!!.toFloat())
        adView.starRatingView!!.visibility = View.VISIBLE
    }
    if (nativeAd.advertiser == null) {
        adView.advertiserView!!.visibility = View.GONE
    } else {
        (adView.advertiserView as AppCompatTextView?)!!.text = nativeAd.advertiser
        adView.advertiserView!!.visibility = View.VISIBLE
    }
    adView.setNativeAd(nativeAd)
}


fun Activity.requestNativeApplovin(
    id: String,
    callBack: (layout: LinearLayout?, status: String) -> Unit
) {
    val nativeAdLoader = MaxNativeAdLoader(id, this)
    var loadedNativeAd: MaxAd? = null
    nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
        override fun onNativeAdLoaded(p0: MaxNativeAdView?, p1: MaxAd?) {
            super.onNativeAdLoaded(p0, p1)
            val layout = LinearLayout(this@requestNativeApplovin)
            layout.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            layout.orientation = LinearLayout.VERTICAL
            if (loadedNativeAd != null) {
                nativeAdLoader.destroy(loadedNativeAd)
            }
            loadedNativeAd = p1
            /*if (layout.childCount > 0) try {
                layout.removeAllViews()
            } catch (e: Exception) {
                Log.e("Exception", "Exception", e)
            }*/
            layout.addView(p0)
            callBack.invoke(layout, ON_AD_LOADED)
        }

        override fun onNativeAdLoadFailed(p0: String?, p1: MaxError?) {
            super.onNativeAdLoadFailed(p0, p1)
            callBack.invoke(
                null,
                "error code =${p1?.code} message=${p1?.message} mediatedNetworkErrorCode=${p1?.mediatedNetworkErrorCode}  mediatedNetworkErrorMessage=${p1?.mediatedNetworkErrorMessage}"
            )
        }

        override fun onNativeAdClicked(p0: MaxAd?) {
            super.onNativeAdClicked(p0)
        }

        override fun onNativeAdExpired(p0: MaxAd?) {
            super.onNativeAdExpired(p0)
        }
    })
    nativeAdLoader.loadAd()
}
