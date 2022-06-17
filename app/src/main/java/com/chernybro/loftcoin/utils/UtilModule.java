package com.chernybro.loftcoin.utils;

import com.chernybro.loftcoin.utils.image_loader.ImageLoader;
import com.chernybro.loftcoin.utils.image_loader.PicassoImageLoader;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UtilModule {

    @Binds
    abstract ImageLoader imageLoader(PicassoImageLoader impl);

}
