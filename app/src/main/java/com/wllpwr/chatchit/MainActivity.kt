package com.wllpwr.chatchit

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var message: String
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = ChatListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()


            refresh_button.setOnClickListener {
                val intent = intent
                finish()
                startActivity(intent)
            }


            post_button.setOnClickListener {
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Message")

                // Set up the input

                // Set up the input
                val input = EditText(this)
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.inputType =
                    InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                // Set up the buttons

                // Set up the buttons
                builder.setPositiveButton(
                    "OK"
                ) { _, _ ->
                    message = input.text.toString()
                    ChitterGitter().postMessage(message)
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                builder.setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                builder.show()
            }
        }


    }
}