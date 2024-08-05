package com.umutyusufcinar.netflixclone.di

import com.umutyusufcinar.netflixclone.MainActivity
import com.umutyusufcinar.netflixclone.ui.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create() : MainComponent
    }
    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)

}