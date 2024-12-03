package com.example.farmerapp.ui.General

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmerapp.ui.FarmerMarketViewModelProvider
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = FarmerMarketViewModelProvider.Factory),
    onBack: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val profile = viewModel.profile.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF5F5F5)), // Light background
                contentAlignment = Alignment.Center
            ) {
                if (profile == null) {
                    CircularProgressIndicator() // Loading indicator while data is fetched
                } else {
                    ProfileContent(profile = profile, navigateToLogin, viewModel::logOut)
                }
            }
        }
    )
}

@Composable
fun ProfileContent(profile: Profile, navigateToLogin: () -> Unit, logOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        // Profile Picture
        AsyncImage(
            model = profile.profilePictureUrl,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape) // Circular shape for the profile picture
                .background(Color.Gray), // Optional background color
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Name
        Text(
            text = profile.name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Username
        Text(
            text = profile.username,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Options List
        ProfileOptionsList(
            profile = profile,
            onLogout = {
                logOut()
                navigateToLogin()
        })
    }
}

@Composable
fun ProfileOptionsList(profile: Profile, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfileOption(title = "Language", value = profile.language)
        ProfileOption(title = "App Version", value = profile.appVersion)
        ProfileOption(title = "Invoices", value = "View Invoices")
        ProfileOption(title = "Help", value = "Contact Support")
        Button(onClick = { onLogout() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) { Text("Log Out") }
    }
}


@Composable
fun ProfileOption(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Gray,
                fontSize = 14.sp
            )
        )
    }
}

