package com.kanemil.memorich.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.data.repository.DecksProvider;

public class TrainingViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mDeckId;

    public TrainingViewModelFactory(int deckId) {
        mDeckId = deckId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DecksProvider decksProvider = new DecksProvider();
        return (T) new TrainingViewModel(mDeckId, decksProvider);
    }
}
