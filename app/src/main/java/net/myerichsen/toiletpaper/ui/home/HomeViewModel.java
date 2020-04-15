package net.myerichsen.toiletpaper.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Architecture Components provides ViewModel helper class for the UI controller that is responsible
 * for preparing data for the UI. ViewModel objects are automatically retained during configuration
 * changes so that data they hold is immediately available to the next activity or fragment
 * instance. For example, if you need to display a list of users in your app, make sure to assign
 * responsibility to acquire and keep the list of users to a ViewModel, instead of an activity or
 * fragment, as illustrated by the following sample code:
 */
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}