package com.kanemil.memorich.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.data.repository.DecksRepository;

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
        DecksRepository decksRepository = new DecksRepository();
        if (modelClass.isAssignableFrom(CardsViewModel.class)) {
            return (T) new CardsViewModel(mDeckId, decksRepository);
        } else if (modelClass.isAssignableFrom(DecksViewModel.class)) {
            return (T) new DecksViewModel(decksRepository);
        } else if (modelClass.isAssignableFrom(TrainingViewModel.class)) {
            return (T) new TrainingViewModel(mDeckId, decksRepository);
        } else {
            throw new IllegalArgumentException("Requested ViewModel not found");
        }
    }
}
