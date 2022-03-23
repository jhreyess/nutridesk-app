package com.nutrikares.nutrideskapp.ui.pacients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.databinding.FragmentGalleryBinding
import com.nutrikares.nutrideskapp.databinding.FragmentPacientsBinding
import com.nutrikares.nutrideskapp.ui.gallery.GalleryViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PacientsFragment : Fragment() {

    private var _binding: FragmentPacientsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPacientsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPacients

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }


}