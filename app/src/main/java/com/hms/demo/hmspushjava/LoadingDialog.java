package com.hms.demo.hmspushjava;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {
    public static AlertDialog createDialog(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.loading_layout,null);
        TextView dialogMessage=view.findViewById(R.id.dialogMessage);
        dialogMessage.setText(R.string.load_dialog_message);
        return new AlertDialog.Builder(context)
                .setTitle(R.string.load_dialog_title)
                .setView(view)
                .setCancelable(false)
                .create();
    }
}
