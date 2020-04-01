package etu.ihm.citycleaner.ui.map.createtrash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTrashViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CreateTrashViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pedro add garbage");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
