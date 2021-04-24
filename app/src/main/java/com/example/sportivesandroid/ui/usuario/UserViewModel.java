package com.example.sportivesandroid.ui.usuario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("Ajustes de Ususario");
    }

    public LiveData<String> getText() {
        return mText;
    }
}