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
/**
 * DialogX activity, this class is useful to create different customs dialogs
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0*/
public class DialogX {
    private Dialog dialog;
    /**
     * @param  activity where you want to put a custom dialog
     * @param  layout of this custom dialog
     *
     * */
    public DialogX(Activity activity, int layout){

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }
    /**
     * To show the dialog
     * */
    public void show(){
        dialog.show();
    }
    /**
     * To close the dialog
     * */
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
    /**
     * To close the dialog by a small button, because this button be in all dialogs
     * @param close to custom the onclick of this button
     * */
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

    /**
     * To make a custom dialog in title, message, button name and button behavior
     * @param nombre_boton_aceptar name of right button
     * @param nombre_boton_cancelar name of left button
     * @param titulo set dialog title
     * @param mensaje set dialog message
     * @param close to custom the onclick of this button
     * */
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
