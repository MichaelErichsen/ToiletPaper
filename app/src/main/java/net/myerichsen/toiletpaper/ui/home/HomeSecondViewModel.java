package net.myerichsen.toiletpaper.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeSecondViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeSecondViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the second home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}