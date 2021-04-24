package com.example.sportivesandroid.ui.usuario;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import com.example.sportivesandroid.MainActivity;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserRequests;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private UserViewModel notificationsViewModel;
    private Button bt_exit, bt_change_password;
    private TextView tv_name, tv_email;
    EditText et_pass_actual,et_pass_nueva,et_repass_nueva;
    private ImageView btActualPassword, btNewPassword, btReNewPassord, bt_card;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        View root = inflater.inflate(R.layout.fragment_usuario, container, false);


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


        if(Preferences.getEmailUser() != null && !Preferences.getEmailUser().equals("")){
            tv_name.setText(Preferences.getNameUser());
            tv_email.setText(Preferences.getEmailUser());
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt_exit.setOnClickListener(v -> {
            Preferences.clearUserPreferences();
            getActivity().recreate();
        });
        bt_change_password.setOnClickListener(v -> {
            DialogChangePassword(getContext());
        });
    }
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

    public void closeSoftKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void DialogChangePassword(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_change_password, null, false);
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);

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
                dialog.dismiss();
            }
        });

        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(v -> {
            closeSoftKeyBoard(view);
            dialog.dismiss();
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

        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.show();
    }
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


}