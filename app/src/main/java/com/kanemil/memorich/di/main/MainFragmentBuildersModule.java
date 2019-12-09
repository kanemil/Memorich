package com.kanemil.memorich.di.main;

import com.kanemil.memorich.presentation.view.fragments.CardsFragment;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;
import com.kanemil.memorich.presentation.view.fragments.TrainingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract DecksFragment contributeDecksFragment();

    @ContributesAndroidInjector
    abstract CardsFragment contributeCardsFragment();

    @ContributesAndroidInjector
    abstract TrainingFragment contributeTrainingFragment();
}
