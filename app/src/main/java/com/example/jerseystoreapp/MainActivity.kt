package com.example.jerseystoreapp

import android.os.Bundle
import android.util.Log
import android.widget.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

// Transaction data class
data class Transaction(
    val size: String,
    val type: String,
    val quantity: Int,
    val totalCost: Int
)

class MainActivity : AppCompatActivity() {

    // List to store all transactions in the session
    private val transactions = mutableListOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNextPage: Button = findViewById(R.id.btnNextPage)
        btnNextPage.setOnClickListener {
            val intent = Intent(this, JerseyListActivity::class.java)
            startActivity(intent)
        }


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
            val selectedRadioId = radioGroupType.checkedRadioButtonId
            if (selectedRadioId == -1) {
                Toast.makeText(this, "Please select a jersey type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
            btnPurchase.startAnimation(bounce)

            // Delay the dialog so animation can finish first
            btnPurchase.postDelayed({
                val selectedSize = spinnerSize.selectedItem.toString()
                val selectedType = findViewById<RadioButton>(selectedRadioId).text.toString()
                val quantity = if (seekBarQuantity.progress == 0) 1 else seekBarQuantity.progress
                val pricePerUnit = jerseyPrices[selectedSize] ?: 0
                val totalCost = pricePerUnit * quantity

                // Save transaction to the session list
                val transaction = Transaction(selectedSize, selectedType, quantity, totalCost)
                transactions.add(transaction)

                // 🔍 Log the current transaction count for debugging
                Log.d("TransactionLog", "Total transactions this session: ${transactions.size}")

                // Show order summary dialog
                val summary = "You purchased $quantity x $selectedType Jersey (Size: $selectedSize)\nTotal Cost: €$totalCost"

                AlertDialog.Builder(this)
                    .setTitle("Order Summary")
                    .setMessage(summary)
                    .setPositiveButton("OK", null)
                    .show()
            }, 350) // 350ms delay for the bounce animation
        }
    }
}
