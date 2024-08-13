package com.umutyusufcinar.netflixclone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umutyusufcinar.netflixclone.di.MainComponent
import com.umutyusufcinar.netflixclone.di.MoviesApplication

class MainActivity : AppCompatActivity() {
    lateinit var mainComponent: MainComponent
    override fun onCreate(savedInstanceState: Bundle?) {

        mainComponent = (application as MoviesApplication).appComponent.mainComponent().create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}