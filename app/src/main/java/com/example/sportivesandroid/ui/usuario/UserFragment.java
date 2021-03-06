package com.example.sportivesandroid.ui.usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sportivesandroid.Adapters.Adapter_servicios_contratados;
import com.example.sportivesandroid.Models.Tarjeta;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.DialogX;
import com.example.sportivesandroid.Utils.Functions;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * User profile, nick name, password, card, hired services  and log out
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0*/
public class UserFragment extends Fragment {
    private UserViewModel notificationsViewModel;
    private Button bt_exit, bt_change_password;
    private TextView tv_name, tv_email, tv_tarjeta;
    EditText et_pass_actual,et_pass_nueva,et_repass_nueva;
    private ImageView btActualPassword, btNewPassword, btReNewPassord,bt_add_card, bt_change_name;
    private ArrayList<Tarjeta> tarjetas;
    private final static int WEB_VIEW_REQUEST = 123;
    Adapter_servicios_contratados adapter;
    RecyclerView recyclerView;
    JSONArray lista, transac;;
    Dialog dialogps;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        View root = inflater.inflate(R.layout.fragment_usuario, container, false);
        Sportives.setCurrentActivity(getActivity());
        notificationsViewModel.getText()
                .observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //Lo que ocurre cuando cambias a este fragmento
            }
        });

        bt_exit = root.findViewById(R.id.bt_exit);
        tv_name = root.findViewById(R.id.tv_name_profile);
        tv_email = root.findViewById(R.id.tv_email_profile);
        bt_change_password = root.findViewById(R.id.bt_cambiar_password);
        et_pass_actual  = root.findViewById(R.id.et_check_password);
        et_pass_nueva = root.findViewById(R.id.et_new_password_dialog);
        et_repass_nueva = root.findViewById(R.id.et_renew_password_dialog);
        tv_tarjeta = root.findViewById(R.id.tv_tarjeta);
        bt_add_card = root.findViewById(R.id.bt_add_card);
        recyclerView = root.findViewById(R.id.recycler_servicios);
        bt_change_name = root.findViewById(R.id.iv_change_name);
        litaservicios();
        return root;
    }
    /**
     *It makes a list with a call to the server and sends this list to configure in its corresponding adapter.
     *@see Adapter_servicios_contratados
     *
     * */
    public void litaservicios(){
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .get_contratados(ApiUtils.getBasicAuthentication());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        lista =   json.getJSONArray(Tags.LISTA);
                        transac = json.getJSONArray("transac");
                        System.out.println("Lista servicios = "+lista+"---------------------");
                        System.out.println("Lista transac = "+transac+"---------------------");
                        lista_contrartados();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    /**
     * Set the list on the adapter and the adapter on the recycler
     * @see Adapter_servicios_contratados
     * */
    public void lista_contrartados(){
        adapter = new Adapter_servicios_contratados(getContext(), lista, getActivity(),transac,getParentFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Preferences.getEmailUser() != null && !Preferences.getEmailUser().equals("")){
            tv_name.setText(Preferences.getNameUser());
            tv_email.setText(Preferences.getEmailUser());
        }

        getTarjetas();

        setCard();

        bt_exit.setOnClickListener(v -> {
            Preferences.clearUserPreferences();
            getActivity().recreate();
        });
        bt_change_password.setOnClickListener(v -> {
            DialogChangePassword(getContext());
        });
        bt_add_card.setOnClickListener(v -> {
            if(Preferences.getString(Tags.TARJETA) != null && !Preferences.getString(Tags.TARJETA).equals("")){
                dialogCreateCard();
            }else{
                Functions.crearTarjeta(getActivity(), WEB_VIEW_REQUEST);
                Functions.refreshFragment(getParentFragmentManager());
                getTarjetas();
            }
        });
        bt_change_name.setOnClickListener(v -> {
            dialog_cambiar_nombre(getContext());
        });
    }
    /**
     * Change the appearance of the card button using preferences to see whether or not you have a card
     * */
    public void setCard(){
        if(Preferences.getString(Tags.TARJETA) != null && !Preferences.getString(Tags.TARJETA).equals("")){
            bt_add_card.setImageResource(R.drawable.cardtem2);
        }else{
            bt_add_card.setImageResource(R.drawable.cardtem1);
            tv_tarjeta.setText(R.string.aqui_card);
        }
    }
    /**
     * When you change the password and need to check make the content of the edit text visible
     *
     * */
    public void showPassword(View view){
        switch (view.getId()){
            case R.id.btVisibleConfirmPassword:

                if(et_pass_actual.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    btActualPassword.setImageResource(R.drawable.ic_remove_red_eye);
                    et_pass_actual.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    btActualPassword.setImageResource(R.drawable.ic_visibility_off);
                    et_pass_actual.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.btVisiblePassword:

                if(et_pass_nueva.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    btNewPassword.setImageResource(R.drawable.ic_remove_red_eye);
                    et_pass_nueva.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    btNewPassword.setImageResource(R.drawable.ic_visibility_off);
                    et_pass_nueva.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.btVisibleNewPassword:
                if(et_repass_nueva.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    btReNewPassord.setImageResource(R.drawable.ic_remove_red_eye);
                    et_repass_nueva.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    btReNewPassord.setImageResource(R.drawable.ic_visibility_off);
                    et_repass_nueva.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        tarjetas = new ArrayList<>();
    }
    /**
     * When you close a dialog box, you need to close the keyboard.
     *
     * */
    public void closeSoftKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     *
     *Opens a dialog to change the password and verify all parameters
     * @param context to set the context dialog
     *
     */
    public void DialogChangePassword(Context context) {
        dialogps = new Dialog(context);
        dialogps.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_change_password, null, false);
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialogps.setContentView(view);

        btActualPassword = view.findViewById(R.id.btVisibleConfirmPassword);

        btNewPassword = view.findViewById(R.id.btVisiblePassword);

        btReNewPassord = view.findViewById(R.id.btVisibleNewPassword);

        et_pass_actual = view.findViewById(R.id.et_check_password);

        et_pass_nueva = view.findViewById(R.id.et_new_password_dialog);

        et_repass_nueva = view.findViewById(R.id.et_renew_password_dialog);

        btActualPassword.setOnClickListener(v -> {
            showPassword(v);
        });

        btNewPassword.setOnClickListener(v -> {
            showPassword(v);
        });

        btReNewPassord.setOnClickListener(v -> {
            showPassword(v);
        });
        ImageView bt_close = view.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSoftKeyBoard(view);
                dialogps.dismiss();
            }
        });

        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(v -> {
            closeSoftKeyBoard(view);
            dialogps.dismiss();
        });

        Button bt_accept = view.findViewById(R.id.bt_accept);

        bt_accept.setOnClickListener(v -> {
            String actual , nueva, renueva;
            actual = et_pass_actual.getText().toString();
            nueva = et_pass_nueva.getText().toString();
            renueva  = et_repass_nueva.getText().toString();
            if(actual.isEmpty()||nueva.isEmpty()||renueva.isEmpty()) {
                Toast.makeText(getActivity(), R.string.faltancampos, Toast.LENGTH_LONG).show();
            }else{
                if (nueva.equals(renueva)) {
                    if(actual.equals(nueva)){
                        Toast.makeText(getActivity(), R.string.equalserrorpassword, Toast.LENGTH_LONG).show();
                    }else{
                        change_password(nueva,actual);
                    }
                } else {
                    Toast.makeText(getActivity(),R.string.password_not_match, Toast.LENGTH_LONG).show();
                }
            }
        });

        final Window window = dialogps.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialogps.setCancelable(false);
        dialogps.show();
    }
    /**
     * you change the password with a call to the server
     *
     * @param  newPassword to change the old password
     * @param  oldPassword to change by newPassword
     * */
    public void change_password(String newPassword, String oldPassword) {
        JSONObject data = new JSONObject();
        try {
            data.put( Tags.NUEVA, newPassword);
            data.put(Tags.ANTIGUA, oldPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .cambiarPass(ApiUtils.getAuthenticationWhith(data));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        Toast.makeText(getActivity(),R.string.successful_change_password,Toast.LENGTH_LONG).show();
                        dialogps.dismiss();
                    }else{
                        Toast.makeText(getActivity(),R.string.error_password_message,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("OnFaliure from change_password");
            }
        });
    }

    /**
     * AlertDialog to warn the user that their card will be deleted from the system
     *
     * @see Functions eraser_cards to deleted the old card
     * @see Functions crearTarjeta to create a new  card
     *
     * */
    private void dialogCreateCard(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¡¡Cuidado!!");
        builder.setMessage("Si vas a cambiar la tarjeta, la anterior será quitada del sistema.");

        builder.setPositiveButton("Añadir nueva tarjeta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Functions.eraser_cards(getParentFragmentManager());
                Functions.crearTarjeta(getActivity(), WEB_VIEW_REQUEST);
                getTarjetas();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * This method made a call to the server to get a list of cards (in this case only one)
     *
     * and setCard to see if the user have or not a card.
     *
     *
     * */
    public void getTarjetas(){
        new Thread() {
            @Override
            public void run() {
                Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                        .get_tarjetas(ApiUtils.getBasicAuthentication());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println("Comprobar tarjertas");
                        try {
                            JSONObject json = new JSONObject(response.body());
                            String result;
                            result = json.getString(Tags.RESULT);
                            if (result.contains(Tags.OK)) {

                                JSONArray array = json.getJSONArray(Tags.LISTA);
                                System.out.println("Comprobar entrada de get_tarjetas "+  array.getJSONObject(array.length()-1).toString());
                                Log.v("PerfilActivity", array.getJSONObject(array.length()-1).toString());
                                Tarjeta t = new Tarjeta(array.getJSONObject(array.length()-1));

                                Preferences.setString(Tags.TARJETA,t.getEnd_digits());

                                tv_tarjeta.setText("**** **** **** "+t.getEnd_digits());

                                Log.v("PerfilActivity", "total pistas " + tarjetas.size());

                                setCard();
                            }else if(result.contains(Tags.NO_VISA)){
                                //Toast.makeText(Sportives.getContext(), R.string.no_visa,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {}
                });
            }
        }.start();
    }
    /**
     *This method made a call to the server to change the user name
     * @param nombre new name
     * */
    public void cambia_nombre(String nombre){
        JSONObject data = new JSONObject();
        try{
            data.put(Tags.NOMBRE, nombre);
        }catch (JSONException e){
            e.printStackTrace();
        }
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .cambiar_nombre(ApiUtils.getAuthenticationWhith(data));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("Comprobar tarjertas");
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result;
                    result = json.getString(Tags.RESULT);
                    if(result.contains(Tags.OK)) {
                        String nb = json.getString(Tags.NOMBRE);
                        Preferences.setNameUser(nb);
                        tv_name.setText(nb);
                    }else{
                        DialogX xd = new DialogX(getActivity(),R.layout.dialog_message_title);
                        xd.dialog_confirmacion("Aceptar", "", "¡Alerta!",json.get(Tags.MESSAGE).toString(),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        xd.dismiss();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        xd.dismiss();
                                    }
                                });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }
    /**
     *AlertDialog to change the user name
     * @param  context to set the context dialog
     * */
    public void dialog_cambiar_nombre(Context context){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_change_name, null, false);
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        Button bt_accept = view.findViewById(R.id.bt_accept_dialog);
        EditText ed_nombre = view.findViewById(R.id.ed_change_name);

        if(Preferences.getNameUser() != null){
            ed_nombre.setText(Preferences.getNameUser());
        }

        bt_accept.setOnClickListener(v -> {
            String nombre;
            nombre = ed_nombre.getText().toString();
            if(nombre.isEmpty()){
                Toast.makeText(getContext(),"Tienes que poner algo, si quieres cambiar tu nombre.",Toast.LENGTH_LONG).show();
            }else{
                cambia_nombre(nombre);
                closeSoftKeyBoard(view);
                dialog.dismiss();
            }
        });

        ImageView bt_close = view.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSoftKeyBoard(view);
                dialog.dismiss();
            }
        });

        Button bt_cancel = view.findViewById(R.id.bt_cancel_dialog);
        bt_cancel.setOnClickListener(v -> {
            closeSoftKeyBoard(view);
            dialog.dismiss();
        });

        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.show();
    }
}