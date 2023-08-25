package com.movieapp.noteapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.movieapp.noteapp.adapter.NoteAdapter
import com.movieapp.noteapp.databinding.FragmentHomeBinding
import com.movieapp.noteapp.model.Note
import com.movieapp.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),NoteAdapter.NoteClickListener {
    private var _binding : FragmentHomeBinding ?=null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialFun()
        observeData()
        searchData()
    }

    private fun initialFun(){
        adapter= NoteAdapter(this@HomeFragment)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager= StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)

        binding.floatingActionButton.setOnClickListener {
            val nav=HomeFragmentDirections.actionHomeFragmentToAddFragment()
            Navigation.findNavController(requireView()).navigate(nav)
        }
    }

    private fun observeData(){
        viewModel.getNote().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                binding.error.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.GONE
            }else{
                adapter.setNoteData(it as ArrayList<Note>)
                binding.recyclerView.visibility=View.VISIBLE
                binding.error.visibility=View.GONE

            }
        })
        viewModel.errorNote.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.error.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.GONE
            }else{
                binding.error.visibility=View.GONE
                binding.recyclerView.visibility=View.VISIBLE
            }
        })
        viewModel.emptyNote.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.empty.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.GONE
            }else{
                binding.empty.visibility=View.GONE
                binding.recyclerView.visibility=View.VISIBLE
            }
        })

    }
    private fun searchData(){
        val search=binding.searchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.recyclerView.scrollToPosition(0)
                viewModel.searchNote(query).observe(viewLifecycleOwner, Observer {
                    it.let {
                        adapter.setNoteData(it as ArrayList<Note>)
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.recyclerView.scrollToPosition(0)
                if (newText !=null){
                    viewModel.searchNote(newText).observe(viewLifecycleOwner, Observer {
                            adapter.setNoteData(it)
                    })
                }
                return true
            }
        })
    }

    override fun setOnClickListener(note: Note) {
        val nav=HomeFragmentDirections.actionHomeFragmentToDetailFragment(note)
        Navigation.findNavController(requireView()).navigate(nav)
    }



}