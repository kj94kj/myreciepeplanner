package com.example.mobileappproject.data

// 알람에 관련된거만 나머지는 상위에서 받아옴.
data class AlarmData(
    // 알람id는 나중에 db연결할 때 타입을 바꿀예정, 지금은 그냥 index느낌으로 정한다.
    val alarmId: Int,
    val hour: Int,
    val minute: Int,
    val activate: Boolean,
)