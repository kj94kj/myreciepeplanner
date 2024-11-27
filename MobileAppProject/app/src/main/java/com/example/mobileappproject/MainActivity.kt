package com.example.mobileappproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobileappproject.ui.AlarmPage
import com.example.mobileappproject.ui.AlarmSettingPage
import com.example.mobileappproject.ui.TodoListPage
import com.example.mobileappproject.ui.theme.MobileAppProjectTheme
import androidx.navigation.compose.rememberNavController
import com.example.mobileappproject.ui.AlarmViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MobileAppProjectTheme{
                val alarmViewModel = viewModel<AlarmViewModel>()

                val navController = rememberNavController()

                val context = LocalContext.current

                alarmViewModel.ensureExactAlarmPermission(context)

                NavHost(navController = navController, startDestination = "TodoListPage"){
                    composable(route = "TodoListPage"){
                        // 여기서 위에서 레시피 정보 받아옴
                        TodoListPage(
                            goToAlarmPage = {navController.navigate(route = "AlarmPage")}
                        )
                    }

                    composable(route = "AlarmPage"){
                        AlarmPage(
                            alarmViewModel = alarmViewModel,
                            context = context,
                            alarmDataToState = { alarmId -> alarmViewModel.alarmDataToState(alarmId) },
                            removeAlarm = { context,alarmId -> alarmViewModel.removeAlarm(context, alarmId) },
                            alarmSwitch = { context, alarmId -> alarmViewModel.alarmSwitch(context, alarmId) },
                            returnToTodoListPage = {navController.navigate(route = "TodoListPage")},
                            goToAlarmSettingPage = {navController.navigate(route = "AlarmSettingPage")}
                        )
                    }

                    composable(route = "AlarmSettingPage"){
                        AlarmSettingPage(
                            context= context,
                            alarmViewModel= alarmViewModel,
                            returnToAlarmPage={navController.navigateUp()},
                            saveAlarm={context -> alarmViewModel.saveAlarm(context)},
                            updateAlarm={context -> alarmViewModel.updateAlarm(context)},
                            resetState = {alarmViewModel.resetState()}
                        )
                    }
                }
            }
        }
    }
}