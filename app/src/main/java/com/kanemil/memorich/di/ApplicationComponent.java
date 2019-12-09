package com.kanemil.memorich.di;

import android.app.Application;

import com.kanemil.memorich.base.BaseApplication;
import com.kanemil.memorich.data.repository.Repository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, DatabaseModule.class,
        ActivityBuildersModule.class, ViewModelFactoryModule.class})
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    Repository getRepository();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
