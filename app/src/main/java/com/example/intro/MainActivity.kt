package com.example.intro

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intro.datasource.PreferencesDataStore
import com.example.intro.ui.theme.IntroTheme
import com.example.intro.view.SaveTask
import com.example.intro.view.TaskList

object PreferencesKeys {
    val IS_DARK_MODE = booleanPreferencesKey("isDarkMode")
    val FORMAT_TIME = stringPreferencesKey("formatTime")
    val LANGUAGE = stringPreferencesKey("lang")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val isDarkModeFlow = remember {
                PreferencesDataStore(this).getPreference(PreferencesKeys.IS_DARK_MODE)
            }




            val isDarkMode by isDarkModeFlow.collectAsState(initial = false)

            IntroTheme(darkTheme = isDarkMode ?: false) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "taskList") {
                    composable(route = "taskList") { TaskList(navController) }
                    composable(route = "saveTask") { SaveTask(navController) }
                }
            }
        }
    }
}
