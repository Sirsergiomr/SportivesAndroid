package com.example.sportivesandroid.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportivesandroid.R;

public class DialogX {
    private Dialog dialog;
    public DialogX(Activity activity, int layout){

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public void setTitle(String title){
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(title);
    }

    public String getStringEditText(int layout){
        EditText et_dialog = dialog.findViewById(layout);
        return et_dialog.getText().toString();
    }

    private void closeButton(){
        ImageView bt_close = dialog.findViewById(R.id.bt_close);

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void closeButton(View.OnClickListener close){
        ImageView bt_close = dialog.findViewById(R.id.bt_close);

        bt_close.setOnClickListener(close);
    }

    private void cancelButton(Button bt_cancel){
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void dialog_confirmacion(String nombre_boton_aceptar, String nombre_boton_cancelar, String titulo, String mensaje, View.OnClickListener aceptar, View.OnClickListener cancelar, View.OnClickListener close){
        TextView title = dialog.findViewById(R.id.tv_title_dialog);
        TextView message = dialog.findViewById(R.id.tv_mensaje_dialog);

        title.setText(titulo);
        message.setText(mensaje);

        Button accept = dialog.findViewById(R.id.bt_accept_dialog);
        Button cancel = dialog.findViewById(R.id.bt_cancel_dialog);

        accept.setText(nombre_boton_aceptar);
        accept.setOnClickListener(aceptar);

        cancel.setText(nombre_boton_cancelar);
        cancel.setOnClickListener(cancelar);
        closeButton(close);
    }
}
