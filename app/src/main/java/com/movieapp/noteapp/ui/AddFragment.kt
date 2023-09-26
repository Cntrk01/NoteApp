package com.movieapp.noteapp.ui

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.movieapp.noteapp.R
import com.movieapp.noteapp.adapter.NoteAdapter
import com.movieapp.noteapp.databinding.FragmentAddBinding
import com.movieapp.noteapp.databinding.FragmentHomeBinding
import com.movieapp.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

@AndroidEntryPoint
class AddFragment : Fragment() {
    private var _binding : FragmentAddBinding?=null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nullCheckControl()
        backButton()
    }

    private fun nullCheckControl(){
        val title=binding.titleText.text
        val note=binding.noteText.text

        binding.saveButton.setOnClickListener {
            if (title.isEmpty()){
                Toast.makeText(requireContext(),"Title Is Not Empty",Toast.LENGTH_SHORT).show()
            }else if (note.isEmpty()){
                Toast.makeText(requireContext(),"Note Is Not Empty",Toast.LENGTH_SHORT).show()
            }else{

                val date=convertLongToTime(currentTimeToLong())
                try {
                    viewModel.addNote(com.movieapp.noteapp.model.Note(null,title.toString(),note.toString(),date))
                    Toast.makeText(requireContext(),"Your Note Add Sucessfuly",Toast.LENGTH_SHORT).show()
                }catch (e:Exception){
                    Toast.makeText(requireContext(),"Exception",Toast.LENGTH_SHORT).show()
                }
                val nav=AddFragmentDirections.actionAddFragmentToHomeFragment()
                Navigation.findNavController(requireView()).navigate(nav)
            }
        }

    }
    private fun backButton(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return format.format(date)
    }
    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }
}