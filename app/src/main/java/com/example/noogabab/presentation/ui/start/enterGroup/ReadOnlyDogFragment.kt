package com.example.noogabab.presentation.ui.start.enterGroup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.noogabab.R
import com.example.noogabab.presentation.ui.start.StartViewModel
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.fragment_craete_dog.*

class ReadOnlyDogFragment : Fragment(R.layout.fragment_craete_dog), View.OnClickListener {
    private val viewModel: EnterGroupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_enter_group.setOnClickListener(this)
        observe()
    }

    private fun observe() {
        viewModel.currentBtnState.observe(requireActivity(), Observer {
            btn_enter_group.isEnabled = it
            if (it) btn_enter_group.setBackgroundColor(requireActivity().getColor(R.color.color_aa5900))
            else btn_enter_group.setBackgroundColor(requireActivity().getColor(R.color.color_e7d0b7))
        })
    }

    override fun onClick(p0: View?) {
        Toast.makeText(requireContext(), "기다려", Toast.LENGTH_SHORT).show()
    }
}