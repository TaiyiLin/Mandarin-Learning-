package com.taiyilin.mandarinlearning.PictureDescription

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taiyilin.mandarinlearning.R

class PictureDescriptionFragment : Fragment() {

    companion object {
        fun newInstance() =
            PictureDescriptionFragment()
    }

    private lateinit var viewModel: PictureDescriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture_description, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PictureDescriptionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}