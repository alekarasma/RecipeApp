package com.example.recipeapp.di

import android.app.Application
import android.content.Context
import com.example.recipeapp.data.RecipeRepositoryImpl
import com.example.recipeapp.domain.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Named("BaseImageUrl")
    fun provideBaseImageUrl(): String = "https://coles.com.au"

    @Provides
    fun provideRecipeRepository(context: Context, @Named("BaseImageUrl") baseImageUrl: String): RecipeRepository {
        return RecipeRepositoryImpl(context, baseImageUrl)
    }

}