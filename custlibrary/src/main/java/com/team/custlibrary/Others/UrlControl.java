package com.team.custlibrary.Others;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.team.custlibrary.R;

public class UrlControl {
    Activity mActivity;
    public Dialog dialogLoader;
    private TextView txtLoadertitle;
    public static String STATUS_ERROR = "0";
    public static String STATUS_SUCCESS = "1";
    public UrlControl(Activity activity) {
        mActivity = activity;
        dialogLoader = new Dialog(activity, R.style.UploadDialog);
        dialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLoader.setCancelable(true);
        dialogLoader.setCanceledOnTouchOutside(true);
        dialogLoader.setContentView(R.layout.dialog_loader);
        txtLoadertitle = dialogLoader.findViewById(R.id.txtLoadertitle);
    }

    public void showLoader() {
        txtLoadertitle.setText("Please Wait..");
        if (dialogLoader != null) {
            dialogLoader.show();
        }
    }

    public void showAdLoader() {
        txtLoadertitle.setText("Ad is loading..");
        if (dialogLoader != null) {
            dialogLoader.show();
        }
    }

    public void dismissADLoader() {
        if (dialogLoader != null && dialogLoader.isShowing()) {
            dialogLoader.dismiss();
        }
    }

    public void dismissLoader() {
        if (dialogLoader != null && dialogLoader.isShowing()) {
            dialogLoader.dismiss();
        }
    }

}
