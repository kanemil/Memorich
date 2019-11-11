package com.kanemil.memorich.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.data.repository.DecksProvider;

public class CardsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mCardId;

    public CardsViewModelFactory(int cardId) {
        mCardId = cardId;
    }

    // ВОПРОС тут с ветвлением if-else, которое содержит проверку, как в конвертере веалют:
    // if (CurrencyConverterViewModel.class.equals(modelClass))
    // дает ошибку. Без нее работает. Не понял, о чем это.
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        DecksProvider decksProvider = new DecksProvider();
        return (T) new CardsViewModel(mCardId, decksProvider);

    }
}
