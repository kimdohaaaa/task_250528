package com.example.task_250528.domain

data class PersonDto(
    val id : String? = null, // PK 키
    var name : String, // 이름
    var age : Int // 나이
)
