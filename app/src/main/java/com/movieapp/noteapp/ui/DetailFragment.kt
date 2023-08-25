package com.movieapp.noteapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.movieapp.noteapp.R
import com.movieapp.noteapp.databinding.FragmentDetailBinding
import com.movieapp.noteapp.databinding.FragmentHomeBinding
import com.movieapp.noteapp.model.Note
import com.movieapp.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailFragment : Fragment() {

    //var ile tanımlanmaz val olcak.Çünkü navArgs degerleri immutable(değiştirilemez) değerlerdir. var kabul etmez
    private val args : DetailFragmentArgs by navArgs()
    private var _binding : FragmentDetailBinding?=null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()
    var time2=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialFun()
        initalButton()
    }

    private fun initialFun(){
        //Editable olduğu için böyle yapınca hata almıyorum.Fakat = ile yapınca tip hatası veriyor
        binding.noteText.setText(args.detail.note)
        binding.titleText.setText(args.detail.title)
    }

    private fun initalButton(){
       backButton()
       saveButton()
       deleteButton()
    }
    private fun backButton(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun saveButton(){
        binding.saveButton.setOnClickListener {

            val title=binding.titleText.text.toString()
            val note=binding.noteText.text.toString()
            time2=convertLongToTime(currentTimeToLong())

            viewModel.updateNote(Note(args.detail.id,title,note,time2))
            Toast.makeText(requireContext(),"Successfully Updated",Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
    private fun deleteButton(){
        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete Message")
            builder.setMessage("Do you want to delete the note")
            builder.setPositiveButton("YES") { dialog, which ->
                viewModel.deleteNote(args.detail)
                Toast.makeText(requireContext(), "Remove Note Is Sucessfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            builder.setNegativeButton("NO") { dialog, which ->
                Toast.makeText(requireContext(), "Note Not Remove", Toast.LENGTH_SHORT).show()
            }

            builder.show()

        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return format.format(date)
    }

    private fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }
}