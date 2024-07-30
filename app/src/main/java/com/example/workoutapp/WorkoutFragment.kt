package com.example.workoutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast

class WorkoutFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkoutAdapter
    private val workoutList = mutableListOf(
        Workout("Push-ups", "Do 20 push-ups", "10 mins"),
        Workout("Squats", "Do 30 squats", "15 mins")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)

        // Initialize Spinner data
        val cardioMinutesSpinner: Spinner = view.findViewById(R.id.spinner_cardio_mins)
        val cardioMinutesOptions = arrayOf("Select time", "5 mins", "10 mins", "15 mins", "20 mins")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cardioMinutesOptions)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cardioMinutesSpinner.adapter = adapterSpinner

        // Initialize the adapter with delete functionality
        adapter = WorkoutAdapter(workoutList) { position ->
            // Handle delete button click
            adapter.removeItem(position)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val saveButton: Button = view.findViewById(R.id.btn_save)
        saveButton.setOnClickListener {
            saveWorkout()
        }

        return view
    }

    private fun saveWorkout() {
        val workoutNameEditText: EditText = requireView().findViewById(R.id.et_workout_name)
        val descriptionEditText: EditText = requireView().findViewById(R.id.et_description)
        val cardioMinutesSpinner: Spinner = requireView().findViewById(R.id.spinner_cardio_mins)

        val name = workoutNameEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val cardioMinutes = cardioMinutesSpinner.selectedItem.toString()

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val newWorkout = Workout(name, description, cardioMinutes)
        workoutList.add(newWorkout)
        adapter.notifyDataSetChanged()

        workoutNameEditText.text.clear()
        descriptionEditText.text.clear()
        cardioMinutesSpinner.setSelection(0)

        Toast.makeText(requireContext(), "Workout saved", Toast.LENGTH_SHORT).show()
    }
}
