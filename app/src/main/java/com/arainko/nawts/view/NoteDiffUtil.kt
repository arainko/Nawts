package com.arainko.nawts.view

import androidx.recyclerview.widget.DiffUtil
import com.arainko.nawts.persistence.Note

object NoteDiffUtil: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.header == newItem.header && oldItem.content == newItem.content && oldItem.id == newItem.id
}