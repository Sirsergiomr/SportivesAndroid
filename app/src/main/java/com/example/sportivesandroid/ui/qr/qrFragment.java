package com.example.sportivesandroid.ui.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportivesandroid.Adapters.Adapter_Actividades;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Qr Fragment really is the output of information generated by the server
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 * */
public class qrFragment extends Fragment {

    private qrviewmodel dashboardViewModel;

    private JSONArray lista = new JSONArray();
    private Adapter_Actividades adapter;
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(qrviewmodel.class);
        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.recycler_view_actividades);

        milist();
        Sportives.setCurrentActivity(getActivity());
        return root;
    }
    /**
     * List with the trainings of every day
     * */
    public void milist(){
        Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                .get_actividades(ApiUtils.getBasicAuthentication());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    String result = json.getString(Tags.RESULT);
                    if (result.contains(Tags.OK)) {
                        lista =   json.getJSONArray(Tags.LISTA);
                        System.out.println("Lista = "+lista+"---------------------");
                        lista_actividades();
                    }else{
                        Toast.makeText(getContext(),"Tenemos un problema",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) { }
        });
    }
    /**
     *Init adapter and set adapter on recyclerview
     * @see Adapter_Actividades
     * */
    public void lista_actividades(){
        adapter = new Adapter_Actividades(getContext(), lista,qrFragment.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    /**
     * Refresh the list
     * */
    @Override
    public void onResume(){
        super.onResume();
        milist();
    }
}