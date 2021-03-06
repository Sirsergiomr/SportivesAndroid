package com.example.sportivesandroid.ui.home;

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
import com.example.sportivesandroid.Adapters.Adapter_Anuncios;
import com.example.sportivesandroid.R;
import com.example.sportivesandroid.Requests.ApiUtils;
import com.example.sportivesandroid.Requests.RetrofitClient;
import com.example.sportivesandroid.Requests.UserServices;
import com.example.sportivesandroid.Sportives;
import com.example.sportivesandroid.Utils.Preferences;
import com.example.sportivesandroid.Utils.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * This class contains the service catalog
 *
 * @author Sergio Muñoz Ruiz
 * @version 2021.0606
 * @since 30.0
 * */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private JSONArray lista = new JSONArray();
    private Adapter_Anuncios adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_catalogo, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                textView.setTextColor(getResources().getColor(R.color.black));
            }
        });

        anuncios();
        Sportives.setCurrentActivity(getActivity());
        recyclerView = root.findViewById(R.id.recycler_anuncios);

        return root;
    }
    /**
     *
     * This method made a call to the server to get a list of advertisements
     * and sends this list to configure in its corresponding adapter.
     *
     * */
    public void anuncios(){
        if(!Preferences.getID().equals("-1")){
            Call<String> call = RetrofitClient.getClient().create(UserServices.class)
                    .get_anuncios(ApiUtils.getBasicAuthentication());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        JSONObject json = new JSONObject(response.body());
                        String result = json.getString(Tags.RESULT);
                        if (result.contains(Tags.OK)) {
                            lista =   json.getJSONArray(Tags.LISTA);
                            System.out.println("Lista anuncios = "+lista+"---------------------");
                            lista_anuncios();
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
    }
    /**
     * Set the list on the adapter and the adapter on the recycler
     * @see Adapter_Anuncios
     * */
    public void lista_anuncios(){
        adapter = new Adapter_Anuncios(getContext(), lista,getParentFragmentManager(),getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}