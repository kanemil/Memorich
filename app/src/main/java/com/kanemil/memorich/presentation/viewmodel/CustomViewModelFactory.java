package com.kanemil.memorich.presentation.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.data.repository.Repository;
import com.kanemil.memorich.presentation.viewmodel.utils.ResourceWrapper;

public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context mApplicationContext;
    private long mDeckId;

    public CustomViewModelFactory(@NonNull Context context, long deckId) {
        mApplicationContext = context.getApplicationContext();
        mDeckId = deckId;
    }

    public CustomViewModelFactory(long deckId) {
        mDeckId = deckId;
    }

    public CustomViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Repository repository = new Repository();
        if (modelClass.isAssignableFrom(CardsViewModel.class)) {
            return (T) new CardsViewModel(mDeckId, repository);
        } else if (modelClass.isAssignableFrom(DecksViewModel.class)) {
            return (T) new DecksViewModel(repository);
        } else if (modelClass.isAssignableFrom(TrainingViewModel.class)) {
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            return (T) new TrainingViewModel(mDeckId, repository, resourceWrapper);
        } else {
            throw new IllegalArgumentException("Requested ViewModel not found");
        }
    }
}
