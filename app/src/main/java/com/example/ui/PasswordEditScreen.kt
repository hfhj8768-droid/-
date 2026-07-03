package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEditScreen(viewModel: PasswordViewModel, navController: NavController, passwordId: Int?) {
    val passwords by viewModel.passwords.collectAsStateWithLifecycle()
    val existingPassword = remember(passwordId, passwords) {
        passwords.find { it.id == passwordId }
    }

    var title by remember { mutableStateOf(existingPassword?.title ?: "") }
    var username by remember { mutableStateOf(existingPassword?.username ?: "") }
    var password by remember { mutableStateOf(existingPassword?.passwordHash ?: "") }
    var url by remember { mutableStateOf(existingPassword?.url ?: "") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (passwordId == null) "Add Password" else "Edit Password") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (passwordId != null) {
                        IconButton(onClick = {
                            viewModel.deletePassword(passwordId)
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth().testTag("input_title"),
                singleLine = true
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username or Email") },
                modifier = Modifier.fillMaxWidth().testTag("input_username"),
                singleLine = true
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().testTag("input_password"),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) "Hide" else "Show"
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(icon)
                    }
                }
            )
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("URL / Website (Optional)") },
                modifier = Modifier.fillMaxWidth().testTag("input_url"),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (title.isNotBlank() && password.isNotBlank()) {
                        if (passwordId == null) {
                            viewModel.addPassword(title, username, password, url)
                        } else {
                            viewModel.updatePassword(passwordId, title, username, password, url)
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp).testTag("save_button"),
                enabled = title.isNotBlank() && password.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}
