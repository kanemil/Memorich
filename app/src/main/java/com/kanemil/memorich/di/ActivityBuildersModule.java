package com.kanemil.memorich.di;

import com.kanemil.memorich.di.main.MainFragmentBuildersModule;
import com.kanemil.memorich.di.main.MainModule;
import com.kanemil.memorich.di.main.MainScope;
import com.kanemil.memorich.di.main.MainViewModelsModule;
import com.kanemil.memorich.presentation.view.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = {MainFragmentBuildersModule.class,
            MainViewModelsModule.class, MainModule.class})
    abstract MainActivity contributeMainActivity();
}
