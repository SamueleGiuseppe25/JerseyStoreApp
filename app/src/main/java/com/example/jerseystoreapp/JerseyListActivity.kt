package com.example.jerseystoreapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*


class JerseyListActivity : AppCompatActivity() {

    private val jerseyNames = arrayOf("Home Jersey", "Away Jersey", "Third Jersey")
    private val jerseyImages = arrayOf(
        R.drawable.jersey_home,
        R.drawable.jersey_away,
        R.drawable.jersey_third
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jersey_list)

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // This closes the current activity and returns to MainActivity
        }

        val textPage: TextView = findViewById(R.id.textPage)
        val listView: ListView = findViewById(R.id.listViewJerseys)

        val adapter = object : BaseAdapter() {
            override fun getCount() = jerseyNames.size
            override fun getItem(position: Int) = jerseyNames[position]
            override fun getItemId(position: Int) = position.toLong()

            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                val view = layoutInflater.inflate(R.layout.list_item_jersey, parent, false)
                val image: ImageView = view.findViewById(R.id.imageJersey)
                val text: TextView = view.findViewById(R.id.textJerseyName)

                text.text = jerseyNames[position]
                image.setImageResource(jerseyImages[position])
                return view
            }
        }

        listView.adapter = adapter
    }
}