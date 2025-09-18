package com.example.intro.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.intro.PreferencesKeys
import com.example.intro.datasource.PreferencesDataStore
import com.example.intro.model.TaskModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(navController: NavController) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val isDarkModeFlow = remember {
        PreferencesDataStore(context).getPreference(PreferencesKeys.IS_DARK_MODE)
    }
    val isDarkMode by isDarkModeFlow.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Lista de tarefas", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = Color.White
                ),
                actions = {
                    Switch(
                        checked = isDarkMode ?: false,
                        onCheckedChange = {isChecked ->
                            scope.launch {
                                PreferencesDataStore(context).setPreferences(PreferencesKeys.IS_DARK_MODE, isChecked)
                            }
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("saveTask") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
//            items(myTaskList) { taskItem ->
//                TaskItem(task = taskItem)
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    val navController = rememberNavController()
    TaskList(navController)
}