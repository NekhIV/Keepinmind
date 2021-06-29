package com.nehaev.keepinmind.ui.themes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nehaev.keepinmind.MindActivity
import com.nehaev.keepinmind.R
import com.nehaev.keepinmind.ui.MindViewModel

class ThemesFragment : Fragment(R.layout.fragment_themes) {

    lateinit var viewModel: MindViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        viewModel = (activity as MindActivity).viewModel
    }
}