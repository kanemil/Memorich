package com.kanemil.memorich.di;

import androidx.lifecycle.ViewModelProvider;

import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
