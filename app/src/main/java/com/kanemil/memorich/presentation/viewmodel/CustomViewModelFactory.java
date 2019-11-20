package com.kanemil.memorich.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.data.repository.DecksProvider;

public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mDeckId;

    public CustomViewModelFactory(int deckId) {
        mDeckId = deckId;
    }

    public CustomViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DecksProvider decksProvider = new DecksProvider();
        if (modelClass.isAssignableFrom(CardsViewModel.class)) {
            return (T) new CardsViewModel(mDeckId, decksProvider);
        } else if (modelClass.isAssignableFrom(DecksViewModel.class)) {
            return (T) new DecksViewModel(decksProvider);
        } else if (modelClass.isAssignableFrom(TrainingViewModel.class)) {
            return (T) new TrainingViewModel(mDeckId, decksProvider);
        } else {
            throw new IllegalArgumentException("Requested ViewModel not found");
        }
    }
}
