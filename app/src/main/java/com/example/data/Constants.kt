package com.studentkit.buner.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object Constants {
    const val CURRENCY = "PKR"
    
    // Nisab thresholds in grams
    const val NISAB_GOLD_GRAMS = 87.48
    const val NISAB_SILVER_GRAMS = 612.36

    val EXPENSE_CATEGORIES = listOf(
        "Food", "Transport", "Education", "Health", "Utilities",
        "Shopping", "Entertainment", "Rent", "Other"
    )

    val INCOME_SOURCES = listOf(
        "Pocket Money", "Part-time Job", "Freelance",
        "Scholarship", "Family Support", "Other"
    )

    val BILL_CATEGORIES = listOf(
        "Electricity", "Gas", "Water", "Internet",
        "Phone", "Cable", "Other"
    )

    val LOAN_TYPES = listOf(
        "I Lent", "I Borrowed"
    )

    val BC_FREQUENCIES = listOf(
        "Daily", "Weekly", "Monthly"
    )

    val NOTES_COLORS = listOf(
        "#FAD02C", // Yellow
        "#00897B", // Teal
        "#1565C0", // Royal Blue
        "#E91E63", // Pink
        "#9C27B0", // Purple
        "#FF5722", // Orange
        "#4CAF50", // Green
        "#00BCD4"  // Cyan
    )

    fun getCategoryIcon(category: String): ImageVector {
        return when (category) {
            "Food" -> Icons.Default.Fastfood
            "Transport" -> Icons.Default.DirectionsCar
            "Education" -> Icons.Default.School
            "Health" -> Icons.Default.LocalHospital
            "Utilities", "Electricity", "Gas", "Water" -> Icons.Default.Bolt
            "Shopping" -> Icons.Default.ShoppingCart
            "Entertainment" -> Icons.Default.Movie
            "Rent" -> Icons.Default.Home
            "Internet" -> Icons.Default.Wifi
            "Phone" -> Icons.Default.PhoneAndroid
            "Cable" -> Icons.Default.Tv
            "Pocket Money" -> Icons.Default.CardGiftcard
            "Part-time Job" -> Icons.Default.Work
            "Freelance" -> Icons.Default.LaptopMac
            "Scholarship" -> Icons.Default.WorkspacePremium
            "Family Support" -> Icons.Default.People
            else -> Icons.Default.Category
        }
    }
}
