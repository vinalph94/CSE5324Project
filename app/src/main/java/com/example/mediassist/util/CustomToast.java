package com.example.mediassist.util;


import static com.example.mediassist.util.ToastStatus.DELETE;
import static com.example.mediassist.util.ToastStatus.FAILURE;
import static com.example.mediassist.util.ToastStatus.SUCCESS;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.mediassist.R;


/**
 * Custom Toast class extends Toast class to customize it
 *
 * @author R
 */
public class CustomToast extends Toast {

    public CustomToast(Context context) {
        super(context);
    }

    public CustomToast(Context context, Activity activity, String message,
                       ToastStatus status) {
        super(context);


        LayoutInflater inflater = activity.getLayoutInflater();


        View view = inflater.inflate(R.layout.custom_toast, null);

        LinearLayout customToastLayout = (LinearLayout) view.findViewById(R.id.customToastLayout);
        ImageView img = (ImageView) view.findViewById(R.id.imgCustomToast);


        TextView txt = (TextView) view.findViewById(R.id.txtCustomToast);


        if (status.equals(SUCCESS)) {
            img.setImageResource(R.drawable.success);
            customToastLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.success));
        } else if (status.equals(DELETE)) {
            img.setImageResource(R.drawable.delete);
            customToastLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary_error_color));
        } else if (status.equals(FAILURE)) {
            img.setImageResource(R.drawable.error);
            customToastLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.secondary_error_color));
        }


        txt.setText(message);

        setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);

        setDuration(Toast.LENGTH_LONG);

        setView(view);
    }

}