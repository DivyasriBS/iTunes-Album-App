package com.divyasri.itunes.ui.custom;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PopupMenuCustomLayout {
    private PopupWindow popupWindow;
    private View popupView;

    public PopupMenuCustomLayout(Context context, int rLayoutId, PopupMenuCustomOnClickListener onClickListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(rLayoutId, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(10);
        }

        LinearLayout linearLayout = (LinearLayout) popupView;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            v.setOnClickListener(v1 -> {
                onClickListener.onClick(v1.getId());
                popupWindow.dismiss();
            });
        }
    }

    public void setAnimationStyle(int animationStyle) {
        popupWindow.setAnimationStyle(animationStyle);
    }

    public void show() {
        popupWindow.showAtLocation(popupView, Gravity.RIGHT, 30, -600);
    }

    public void show(View anchorView, int gravity, int offsetX, int offsetY) {
        popupWindow.showAsDropDown(anchorView, 20, -2 * (anchorView.getHeight()));
    }

    public interface PopupMenuCustomOnClickListener {
        void onClick(int menuItemId);
    }

}