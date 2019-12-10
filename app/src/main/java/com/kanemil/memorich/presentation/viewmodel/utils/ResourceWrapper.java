package com.kanemil.memorich.presentation.viewmodel.utils;

import android.app.Application;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.kanemil.memorich.di.main.MainScope;

import javax.inject.Inject;

@MainScope
public class ResourceWrapper implements IResourceWrapper {

    private final Resources mResources;

    @Inject
    public ResourceWrapper(@NonNull Application application) {
        mResources = application.getApplicationContext().getResources();
    }

    @Override
    public String getString(int resId) {
        return mResources.getString(resId);
    }

    @Override
    public String getString(int resId, Object... formatArgs) {
        return mResources.getString(resId, formatArgs);
    }
}
