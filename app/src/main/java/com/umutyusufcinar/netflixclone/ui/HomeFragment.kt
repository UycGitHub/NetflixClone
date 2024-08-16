package com.umutyusufcinar.netflixclone.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.umutyusufcinar.netflixclone.MainActivity
import com.umutyusufcinar.netflixclone.R
import com.umutyusufcinar.netflixclone.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel>{viewModelFactory}
    override fun onAttach(context: Context){
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        viewModel.getTrending()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}