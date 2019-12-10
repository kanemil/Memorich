package com.kanemil.memorich.di.main;

import android.app.Application;

import com.kanemil.memorich.presentation.viewmodel.utils.ResourceWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @MainScope
    @Provides
    ResourceWrapper provideResourceWrapper(Application application) {
        return new ResourceWrapper(application);
    }
}
