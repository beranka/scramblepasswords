package com.github.beranka.scramblepasswords

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.beranka.scramblepasswords.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // View Binding
    private lateinit var binding: ActivityMainBinding

    // Shared preferences
    private lateinit var sharedPref: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor

    // For getting data back from child activities
    private lateinit var uploadIntent: ActivityResultLauncher<Intent>
    private lateinit var currentIntent: ActivityResultLauncher<Intent>

    // For storing data and knowing if I already have some data
    private lateinit var data: Intent
    private var dataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPref = applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        prefEditor = sharedPref.edit()

        loadData()

        // Processing of data returned from the respective child activities
        currentIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                // Get current index from display activity
                data.putExtra("currentPassword", result.data!!.getIntExtra("currentPassword", 0))
            }
        }

        uploadIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                // Get  data from the UploadActivity
                data = result.data!!
                dataLoaded = true
            }
        }

        binding.btnCurrentMainAct.setOnClickListener {
            if(dataLoaded){
                Intent(this, DisplayActivity::class.java).also {
                    // Pass passwords, competition name and index to DisplayActivity
                    it.putExtra("competitionName", data.getStringExtra("competitionName"))
                    it.putExtra("currentPassword", data.getIntExtra("currentPassword", 0))
                    it.putExtra("data", data.getStringExtra("data"))

                    // Launch DisplayActivity
                    currentIntent.launch(it)
                }
            }
            else {
                // Let user know about absence of any passwords
                Toast.makeText(applicationContext, "You don't have any uploaded passwords", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUploadMainAct.setOnClickListener {
            Intent(this, UploadPasswordsActivity::class.java).also {
                // Launch UploadActivity
                uploadIntent.launch(it)
            }
        }

    }

    private fun loadData() {
        // Try loading data from SharedPreferences
        data = Intent().putExtra("competitionName", sharedPref.getString("competitionName", "").toString()).putExtra("currentPassword", sharedPref.getInt("currentPassword", 0)).putExtra("data", sharedPref.getString("data", "").toString())
        if(data.getStringExtra("competitionName").toString().isNotEmpty()) {
            dataLoaded = true
        }
    }

    private fun saveData() {
        // Save data to SharedPreferences
        prefEditor.putString("competitionName", data.getStringExtra("competitionName").toString())
        prefEditor.putInt("currentPassword", data.getIntExtra("currentPassword", 0))
        prefEditor.putString("data", data.getStringExtra("data").toString())
        prefEditor.apply()
    }

    override fun onPause(){
        super.onPause()
        // Save data to SharedPreferences before application closes
        saveData()
    }
}