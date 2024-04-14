package com.maksk993.cryptoportfolio.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maksk993.cryptoportfolio.presentation.models.FindFragmentById;

public class MainViewModel extends ViewModel {
    protected MutableLiveData<FindFragmentById> m_shouldNewFragmentBeOpened = new MutableLiveData<>();

    public void openFragment(int id){
        for (FindFragmentById fragmentId : FindFragmentById.values()){
            if (fragmentId.getInteger() == id){
                m_shouldNewFragmentBeOpened.setValue(fragmentId);
            }
        }
    }

    public LiveData<FindFragmentById> shouldNewFragmentBeOpened(){
        return m_shouldNewFragmentBeOpened;
    }
}
