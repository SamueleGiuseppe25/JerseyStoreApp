package com.example.jerseystoreapp

import android.os.Bundle
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI Elements
        val spinnerSize: Spinner = findViewById(R.id.spinnerSize)
        val radioGroupType: RadioGroup = findViewById(R.id.radioGroupType)
        val seekBarQuantity: SeekBar = findViewById(R.id.seekBarQuantity)
        val textQuantity: TextView = findViewById(R.id.textQuantity)
        val btnPurchase: Button = findViewById(R.id.btnPurchase)

        // Jersey sizes and their prices
        val jerseyPrices = mapOf("Small" to 20, "Medium" to 25, "Large" to 30)
        val sizes = arrayOf("Small", "Medium", "Large")

        // Setting up the spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sizes)
        spinnerSize.adapter = adapter




        // Default quantity display
        seekBarQuantity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val quantity = if (progress == 0) 1 else progress
                textQuantity.text = "Quantity: $quantity"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Purchase Button Logic
        btnPurchase.setOnClickListener {
            val selectedSize = spinnerSize.selectedItem.toString()
            val selectedRadioId = radioGroupType.checkedRadioButtonId
            val quantity = if (seekBarQuantity.progress == 0) 1 else seekBarQuantity.progress

            // Validate selection
            if (selectedRadioId == -1) {
                Toast.makeText(this, "Please select a jersey type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedType = findViewById<RadioButton>(selectedRadioId).text.toString()
            val pricePerUnit = jerseyPrices[selectedSize] ?: 0
            val totalCost = pricePerUnit * quantity

            // Display summary in a Toast message
            val summary = "You purchased $quantity x $selectedType Jersey (Size: $selectedSize)\nTotal Cost: €$totalCost"

            AlertDialog.Builder(this)
                .setTitle("Order Summary")
                .setMessage(summary)
                .setPositiveButton("OK", null)
                .show()

        }
    }
}
