package com.example.sportivesandroid.ui.usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Utils.Preferences;

public class UserFragment extends Fragment {

    private UserViewModel notificationsViewModel;
    private Button bt_exit;
    private TextView tv_name, tv_email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        View root = inflater.inflate(R.layout.fragment_usuario, container, false);

        final TextView textView = root.findViewById(R.id.text_notifications);

        notificationsViewModel.getText()
                .observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        bt_exit = root.findViewById(R.id.bt_exit);
        tv_name = root.findViewById(R.id.tv_name_profile);
        tv_email = root.findViewById(R.id.tv_email_profile);

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
    }


}