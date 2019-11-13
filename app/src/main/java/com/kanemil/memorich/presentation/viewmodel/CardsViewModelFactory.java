package com.kanemil.memorich.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.data.repository.DecksProvider;

public class CardsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mDeckId;

    public CardsViewModelFactory(int deckId) {
        mDeckId = deckId;
    }

    // ВОПРОС тут с ветвлением if-else, которое содержит проверку, как в конвертере веалют:
    // if (CurrencyConverterViewModel.class.equals(modelClass))
    // дает ошибку. Без нее работает. Не понял, о чем это.
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DecksProvider decksProvider = new DecksProvider();
        return (T) new CardsViewModel(mDeckId, decksProvider);

    }
}
