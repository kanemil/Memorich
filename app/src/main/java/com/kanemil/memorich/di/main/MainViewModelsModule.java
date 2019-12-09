package com.kanemil.memorich.di.main;

import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.di.ViewModelKey;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModel;
import com.kanemil.memorich.presentation.viewmodel.DecksViewModel;
import com.kanemil.memorich.presentation.viewmodel.TrainingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DecksViewModel.class)
    public abstract ViewModel bindDecksViewModel(DecksViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CardsViewModel.class)
    public abstract ViewModel bindCardsViewModel(CardsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TrainingViewModel.class)
    public abstract ViewModel bindTrainingViewModel(TrainingViewModel viewModel);
}
