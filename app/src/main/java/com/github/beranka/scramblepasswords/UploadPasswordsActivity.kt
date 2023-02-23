package com.github.beranka.scramblepasswords

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.beranka.scramblepasswords.databinding.ActivityUploadPasswordsBinding

class UploadPasswordsActivity : AppCompatActivity() {

    // View Binding
    private lateinit var binding: ActivityUploadPasswordsBinding

    // Shared Preferences
    private lateinit var sharedPref: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityUploadPasswordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref=getSharedPreferences("pref1", Context.MODE_PRIVATE)
        prefEditor=sharedPref.edit()


        binding.btnSubmit.setOnClickListener {
            // Get name and passwords + scramble sets from the Edit Texts
            val submittedName = binding.etCompetitionName.text.toString()
            val submittedText = binding.etPasswords.text.toString()

            // If data is valid, pass it to parent and finish view
            val data = parseInput(submittedText, submittedName)
            if(data.getBooleanExtra("successful", false)){
                Toast.makeText(applicationContext, "Passwords were successfully uploaded.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK, data)
                finish()
            }
            else {
                // Otherwise inform the user that it's not
                Toast.makeText(applicationContext, "The name is empty or the password data is invalid.", Toast.LENGTH_SHORT).show()
            }

        }

        // Finish activity (without uploading anything) by clicking the text on top
        binding.tvUploadPasswords.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    private fun parseInput(passwordInput: String, nameInput: String): Intent {
        // Probably too much spaghetti to look at, but this is my function to parse the data
        val result = Intent()
        var data = ""
        val lines=passwordInput.lines()

        // We want the competition to have a name, otherwise the input won't be accepted.
        if(nameInput.trim().isEmpty()){
            return result
        }

        // For each line (one pair of password and set) eliminate trailing whitespaces and append it to the resulting data string
        lines.forEach {
            // It has to contain exactly one ':' as delimiter between scramble set and password, other lines are skipped
            if (it.length - it.replace(":", "").length==1){
                if(it.substringBefore(":").trim().isNotEmpty() and it.substringAfter(":").trim().isNotEmpty()) {
                    // We don't want whitespace only passwords/names of sets as well, so those are skipped
                    data = data + it.substringBefore(":").trim()+","+it.substringAfter(":").trim()+";"
                }
            }
        }

        // If we were left with only
        if(data.isEmpty()){
            return result
        }


        result.putExtra("competitionName", nameInput)
        result.putExtra("data", data)
        result.putExtra("current", 0)
        result.putExtra("successful", true)

        return result
    }

}
