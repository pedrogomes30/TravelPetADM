package com.example.travelpetadm.ui.donoanimal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DonoAnimalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DonoAnimalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("IMPLEMENTAR FRAGMENT");
    }

    public LiveData<String> getText() {
        return mText;
    }
}