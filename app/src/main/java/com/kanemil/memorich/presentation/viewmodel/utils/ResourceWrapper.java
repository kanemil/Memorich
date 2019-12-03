package com.kanemil.memorich.presentation.viewmodel.utils;

import android.content.res.Resources;

import androidx.annotation.NonNull;

public class ResourceWrapper implements IResourceWrapper {

    private final Resources mResources;

    public ResourceWrapper(@NonNull Resources resources) {
        mResources = resources;
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
