package com.github.beranka.scramblepasswords

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.github.beranka.scramblepasswords.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity() {

    // View Binding
    private lateinit var binding: ActivityDisplayBinding

    // Shared Preferences
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor

    // Initialization of variables for holding data
    private var competitionName = ""
    private var passwords = mutableListOf<Password>()
    private var currentPassword = 0

    // Pass back data when Back is pressed
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setResult(RESULT_OK, Intent().putExtra("currentPassword", currentPassword))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getSharedPreferences("preferences", MODE_PRIVATE)
        prefEditor = sharedPrefs.edit()

        loadData(intent)

        // Don't display anything and avoid crashing if data is nonexistent and this activity somehow started anyway
        if(currentPassword>=passwords.size){
            Toast.makeText(applicationContext, "The index is for some reason too big.", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK, Intent().putExtra("currentPassword", currentPassword))
            finish()
        }
        else {
            displayCurrentPassword()
        }

        // Switch to previous set
        binding.btnPreviousSet.setOnClickListener {
            if(currentPassword!=0){
                currentPassword--
                displayCurrentPassword()
            }
            else {
                Toast.makeText(applicationContext, "This is the first set!", Toast.LENGTH_SHORT).show()
            }
        }

        // Switch to next set
        binding.btnNextSet.setOnClickListener {
            if(passwords.size>currentPassword+1){
                currentPassword++
                displayCurrentPassword()
            }
            else {
                Toast.makeText(applicationContext, "This is the last set!", Toast.LENGTH_SHORT).show()
            }
        }

        // Finish activity by clicking on competition name
        binding.tvCompetitionName.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("currentPassword", currentPassword))
            finish()
        }

        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
    }

    override fun onPause() {
        super.onPause()
        // Save data to SharedPreferences before activity gets destroyed
        saveData()
    }

    private fun loadData(data: Intent) {
        // Load data from intent extras that started the activity
        competitionName = data.getStringExtra("competitionName").toString()
        currentPassword = data.getIntExtra("currentPassword", 0)

        var dataString = data.getStringExtra("data").toString()

        while(dataString.isNotEmpty()) {
            val set = dataString.substringBefore(",")
            dataString = dataString.substringAfter(",")
            val password = dataString.substringBefore(";")
            dataString = dataString.substringAfter(";").trim()

            passwords.add(Password(password = password, set = set))
        }

        return
    }

    private fun saveData() {
        // Save data to Shared Preferences
        prefEditor.putInt("currentPassword", currentPassword)
        prefEditor.apply()
    }


    private fun displayCurrentPassword() {
        // Change text in all the views to display current password and scramble set
        binding.tvCompetitionName.text = competitionName
        binding.tvPassword.text = passwords[currentPassword].password
        binding.tvScrambleSet.text = passwords[currentPassword].set
        return
    }

}