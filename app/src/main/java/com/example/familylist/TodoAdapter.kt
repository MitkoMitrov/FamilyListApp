package com.example.familylist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false,
            )
        )
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(todoTitle: TextView, isChecked: Boolean){
        if(isChecked){
            todoTitle.paintFlags = todoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            todoTitle.paintFlags = todoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var curTodo = todos[position]
        holder.itemView.apply {
            todoTitle.text = curTodo.title
            itemDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(todoTitle, curTodo.isChecked)
            itemDone.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(todoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}