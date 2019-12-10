package com.kanemil.memorich.di.main;

import com.kanemil.memorich.presentation.view.dialogs.AddDeckDialogFragment;
import com.kanemil.memorich.presentation.view.dialogs.RenameDeckDialogFragment;
import com.kanemil.memorich.presentation.view.fragments.AddOrEditCardFragment;
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

    @ContributesAndroidInjector
    abstract AddOrEditCardFragment contributeAddOrEditCardFragment();

    @ContributesAndroidInjector
    abstract AddDeckDialogFragment contributeAddDeckDialogFragment();

    @ContributesAndroidInjector
    abstract RenameDeckDialogFragment contributeRenameDeckDialogFragment();
}
