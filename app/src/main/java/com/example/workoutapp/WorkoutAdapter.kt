package com.example.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.ImageButton

class WorkoutAdapter(
    private val workouts: MutableList<Workout>, // Changed to MutableList
    private val onDeleteClickListener: (Int) -> Unit // Callback for delete action
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.tvWorkoutName.text = workout.name
        holder.tvCardioMinutes.text = workout.cardioMinutes
        holder.tvDescription.text = workout.description

        // Initially hide the description and set the "Read More" text
        holder.tvDescription.visibility = View.GONE
        holder.tvReadMore.text = "Read More"

        holder.tvReadMore.setOnClickListener {
            if (holder.tvDescription.visibility == View.VISIBLE) {
                holder.tvDescription.visibility = View.GONE
                holder.tvReadMore.text = "Read More"
            } else {
                holder.tvDescription.visibility = View.VISIBLE
                holder.tvReadMore.text = "Read Less"
            }
        }

        // Set up the delete button click listener
        holder.btnDelete.setOnClickListener {
            onDeleteClickListener(position)
        }
    }

    fun removeItem(position: Int) {
        workouts.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = workouts.size

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWorkoutName: TextView = itemView.findViewById(R.id.tv_workout_name)
        val tvCardioMinutes: TextView = itemView.findViewById(R.id.tv_cardio_minutes)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvReadMore: TextView = itemView.findViewById(R.id.tv_read_more)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
    }
}
