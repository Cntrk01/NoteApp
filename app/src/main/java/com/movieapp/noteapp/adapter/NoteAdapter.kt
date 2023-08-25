package com.movieapp.noteapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.noteapp.R
import com.movieapp.noteapp.databinding.ListItemBinding
import com.movieapp.noteapp.model.Note
import kotlin.random.Random

class NoteAdapter(private val listener : NoteClickListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var data = listOf<Note>()

    fun setNoteData(_data:List<Note>){
        this.data=_data
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(var binding :ListItemBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding=ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note=data[position]
        holder.binding.tvNote.text=note.note
        holder.binding.tvDate.text=note.date
        holder.binding.tvTitle.text=note.title

        holder.binding.tvTitle.isSelected=true
        holder.binding.tvDate.isSelected=true
        //bunu yapınca cardview özellikleri çalışıyor evet fakat  holder.binding.listItemCardView.setBackgroundResource(randomColor()) şunu yazmıştım cornerRadiusu etkisiz hale getirdi.
        holder.binding.listItemCardView.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))

        holder.binding.listItemCardView.setOnClickListener {
            listener.setOnClickListener(data[holder.adapterPosition])
        }

//        holder.binding.listItemCardView.setOnLongClickListener {
//
//        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun randomColor() : Int {
        val list=ArrayList<Int>()
        list.add(R.color.notecolor1)
        list.add(R.color.notecolor2)
        list.add(R.color.notecolor3)
        list.add(R.color.notecolor4)
        list.add(R.color.notecolor5)
        list.add(R.color.notecolor6)
        list.add(R.color.notecolor7)

        val seed=System.currentTimeMillis().toInt()
        val randmIndex= Random(seed).nextInt(list.size) //list.size eklemediğim için hata veriyordu !!!!

        return list[randmIndex]
    }

    interface NoteClickListener{
        fun setOnClickListener(note: Note)
    }
}