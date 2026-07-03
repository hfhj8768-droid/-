package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.PasswordApp
import com.example.ui.PasswordViewModel
import com.example.ui.PasswordViewModelFactory
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory((application as PasswordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PasswordApp(viewModel = viewModel)
            }
        }
    }
}
