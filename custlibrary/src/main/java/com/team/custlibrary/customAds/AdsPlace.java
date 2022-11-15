package com.team.custlibrary.customAds;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.ads.MaxAdView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.team.custlibrary.Model.CategoryModel;
import com.team.custlibrary.Model.ResponseModel;
import com.team.custlibrary.Others.ApiClient;
import com.team.custlibrary.Others.ApiInterface;
import com.team.custlibrary.Others.UrlControl;
import com.team.custlibrary.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsPlace {

    Activity activity;
    FrameLayout frameBannerBtm;
    RelativeLayout relPopupBtm;
    LottieAnimationView ivLottieViewBtm;
    ImageView imgBannerBtm;
    ResponseModel responseModel1;
    UrlControl urlControl;

    public AdsPlace(Activity activity )
    {
        this.activity=activity;
        GetAdsAsync(activity);
    }

    public void addView(RelativeLayout relativeLayout) {

        View bannerView = activity.getLayoutInflater().inflate(R.layout.bannerads, null);
        frameBannerBtm=bannerView.findViewById(R.id.frameBannerBtm);
        relPopupBtm=bannerView.findViewById(R.id.relPopupBottom);
        ivLottieViewBtm=bannerView.findViewById(R.id.ivLottieViewBtm);
        imgBannerBtm=bannerView.findViewById(R.id.imgBannerBtm);

        if (responseModel1.getButtomeAds() != null) {
            if (responseModel1.getButtomeAds().getType() != null) {
                if (responseModel1.getButtomeAds().getType().matches("1")) {
                    frameBannerBtm.setVisibility(View.GONE);
                    setCustomAds(activity, responseModel1.getButtomeAds(), relPopupBtm, ivLottieViewBtm, imgBannerBtm);
                } else if (responseModel1.getButtomeAds().getType().matches("2")) {
                    frameBannerBtm.setVisibility(View.VISIBLE);
                    showLovinBanner(activity, frameBannerBtm);
                } else if (responseModel1.getButtomeAds().getType().matches("3")) {
                    frameBannerBtm.setVisibility(View.VISIBLE);
                    showGoogleBanner(activity, frameBannerBtm);
                }
            }
        }
        if(bannerView.getParent() != null) {
            ((ViewGroup)bannerView.getParent()).removeView(bannerView); // <- fix
        }
        relativeLayout.addView(bannerView);
    }
    public void GetAdsAsync(Activity activity) {
        this.activity = activity;
        try {
            JSONObject  jObject = new JSONObject();
            urlControl = new UrlControl(activity);
            urlControl.showLoader();
            jObject.put("deviceName", android.os.Build.MODEL);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseModel> call = apiService.getWithdrawData(jObject.toString());
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    onPostExecute(response.body());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    if (!call.isCanceled()) {
                        Toast.makeText(activity, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void onPostExecute(ResponseModel responseModel) {
        try {
            urlControl.dismissLoader();
            if (responseModel.getStatus().equals(UrlControl.STATUS_SUCCESS)) {
                setVideoData(responseModel);
            } else if (responseModel.getStatus().equals(UrlControl.STATUS_ERROR)) {
                Toast.makeText(activity, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (responseModel.getStatus().equals("2")) {
                Toast.makeText(activity, responseModel.getMessage(), Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            urlControl.dismissLoader();
            e.printStackTrace();
        }
    }
    public void setVideoData(ResponseModel responseModel) {

        if (responseModel!=null)
        {
            responseModel1=responseModel;

        }
    }


    public void showGoogleBanner(Activity activity, FrameLayout frameLayout)
    {
        String bannerId="ca-app-pub-3940256099942544/6300978111";
        Log.e("bannerId--)",""+bannerId);
        AdView adView = new AdView(activity);
        adView.setAdUnitId(bannerId);
        frameLayout.removeAllViews();
        frameLayout.addView(adView);
        AdSize adSize = getAdSize(activity, frameLayout);
        adView.setAdSize(adSize);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    public void showLovinBanner(Activity activity,FrameLayout frameLayout)
    {
        String lovinID="TEST_AD";

        MaxAdView adView = new MaxAdView(lovinID, MaxAdFormat.BANNER, activity);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int heightPx = 150;

        adView.setLayoutParams( new ViewGroup.LayoutParams( width, heightPx ) );
        frameLayout.addView(adView);
        adView.loadAd();
    }
    private AdSize getAdSize(Activity activity, View adContainerView) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public void setCustomAds(Activity activity, CategoryModel categoryModel, RelativeLayout relView, LottieAnimationView ltBanner, ImageView ivBanner)
    {Log.e("getBgImage2-1-)", "" + categoryModel.getImage());
        if (categoryModel.getImage().contains(".json")) {
            Log.e("getBgImage2--)", "" + categoryModel.getImage());
            ivBanner.setVisibility(View.GONE);
            ltBanner.setVisibility(View.VISIBLE);
            ltBanner.setAnimationFromUrl(categoryModel.getImage());
            ltBanner.setRepeatCount(LottieDrawable.INFINITE);
        } else {
            ivBanner.setVisibility(View.VISIBLE);
            ltBanner.setVisibility(View.GONE);
            Log.e("getBgImage1--)", "" + categoryModel.getImage());
            Glide.with(activity)
                    .load(categoryModel.getImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
//                                ivBanner.setBackground(activity.getResources().getDrawable(R.drawable.bg_slider));
                            return false;
                        }
                    })
                    .into(ivBanner);


        }
        relView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(categoryModel.getUrl());
                try {

                    if (activity!=null)
                    {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(uri);
                        activity.startActivity(i);
                    }
                }
                catch(ActivityNotFoundException e) {
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        activity.startActivity(goToMarket);
                    } catch (ActivityNotFoundException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });
    }

}
