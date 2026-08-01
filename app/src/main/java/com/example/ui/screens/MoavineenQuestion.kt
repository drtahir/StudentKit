package com.example.ui.screens

/**
 * Data Model for Moavineen-e-Hujjaj NTS Test Question
 */
data class MoavineenQuestion(
    val id: Int,
    val positionTarget: String, // "Supervisor", "Supporting Staff", or "Both"
    val subjectCategory: String, // "Hajj Rules & Arkan", "Moavineen Operational SOPs", "Geography & Holy Sites", "Functional Arabic", "Management & Ethics", "Hajj Policy & Tech"
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String
)
