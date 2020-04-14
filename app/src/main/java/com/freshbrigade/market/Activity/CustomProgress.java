package com.freshbrigade.market.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freshbrigade.market.R;

import java.util.Objects;

public class CustomProgress {

    Dialog alertDialog;
    Activity activity;
    TextView tv_title;

    public CustomProgress(Activity activity) {
        this.activity = activity;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void show(String title)
    {

        alertDialog = new Dialog(activity);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.setContentView(R.layout.layout_progress);
        tv_title=alertDialog.findViewById(R.id.dialogText);
        if(!title.isEmpty())
        {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }

        Window window = alertDialog.getWindow();
        Objects.requireNonNull(window).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        try {
            alertDialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();

        }

    }


    public  void  hide()
    {
        if(alertDialog!=null) {
            alertDialog.dismiss();
            alertDialog=null;
        }
    }

}
