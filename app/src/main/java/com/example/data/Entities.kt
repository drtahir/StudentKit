package com.example.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String, // ISO 8601 String
    val note: String?,
    @ColumnInfo(name = "is_recurring") val isRecurring: Int = 0
)

@Entity(tableName = "income")
data class Income(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val source: String,
    val date: String, // ISO 8601 String
    val note: String?
)

@Entity(tableName = "bills")
data class Bill(
    @PrimaryKey val id: String,
    val name: String,
    val amount: Double,
    @ColumnInfo(name = "due_date") val dueDate: String,
    @ColumnInfo(name = "is_paid") val isPaid: Int = 0,
    val category: String,
    @ColumnInfo(name = "is_recurring") val isRecurring: Int = 1
)

@Entity(tableName = "bc_committees")
data class BcCommittee(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "amount_per_head") val amountPerHead: Double,
    @ColumnInfo(name = "total_members") val totalMembers: Int,
    @ColumnInfo(name = "start_date") val startDate: String,
    val frequency: String = "monthly"
)

@Entity(tableName = "bc_members")
data class BcMember(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "committee_id") val committeeId: String,
    val name: String,
    val phone: String?,
    @ColumnInfo(name = "payout_position") val payoutPosition: Int,
    @ColumnInfo(name = "has_received") val hasReceived: Int = 0
)

@Entity(tableName = "bc_payments")
data class BcPayment(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "committee_id") val committeeId: String,
    @ColumnInfo(name = "member_id") val memberId: String,
    val month: String,
    @ColumnInfo(name = "is_paid") val isPaid: Int = 0,
    @ColumnInfo(name = "paid_date") val paidDate: String? = null
)

@Entity(tableName = "bc_history")
data class BcHistory(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "committee_id") val committeeId: String,
    @ColumnInfo(name = "round_number") val roundNumber: Int,
    @ColumnInfo(name = "winner_id") val winnerId: String,
    @ColumnInfo(name = "winner_name") val winnerName: String,
    @ColumnInfo(name = "amount_won") val amountWon: Double,
    @ColumnInfo(name = "draw_date") val drawDate: String
)

@Entity(tableName = "loans")
data class Loan(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "person_name") val personName: String,
    val amount: Double,
    val type: String, // "I Lent", "I Borrowed"
    val date: String,
    @ColumnInfo(name = "due_date") val dueDate: String?,
    @ColumnInfo(name = "is_settled") val isSettled: Int = 0,
    val note: String?
)

@Entity(tableName = "savings_goals")
data class SavingsGoal(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo(name = "target_amount") val targetAmount: Double,
    @ColumnInfo(name = "current_amount") val currentAmount: Double = 0.0,
    @ColumnInfo(name = "target_date") val targetDate: String?,
    val icon: String?,
    val color: String?
)

@Entity(tableName = "notes")
data class NoteEntry(
    @PrimaryKey val id: String,
    val title: String,
    val content: String?,
    val color: String?, // hex or name
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "is_pinned") val isPinned: Int = 0
)

@Entity(tableName = "tasks")
data class TaskEntry(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo(name = "is_done") val isDone: Int = 0,
    @ColumnInfo(name = "due_date") val dueDate: String?,
    val subject: String?,
    @ColumnInfo(name = "created_at") val createdAt: String
)

@Entity(tableName = "timetable")
data class TimetableEntry(
    @PrimaryKey val id: String,
    val subject: String,
    val teacher: String?,
    val room: String?,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int, // 1 to 7 (Mon = 1, Sun = 7)
    @ColumnInfo(name = "start_time") val startTime: String, // e.g., "09:00"
    @ColumnInfo(name = "end_time") val endTime: String, // e.g., "10:30"
    val color: String?
)

@Entity(tableName = "passwords")
data class PasswordEntry(
    @PrimaryKey val id: String,
    val title: String,
    val username: String?,
    @ColumnInfo(name = "password_encrypted") val passwordEncrypted: String,
    val category: String?,
    val website: String?,
    val note: String?,
    @ColumnInfo(name = "created_at") val createdAt: String
)

@Entity(tableName = "temp_pdf_images")
data class TempPdfImage(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "file_path") val filePath: String,
    @ColumnInfo(name = "original_uri") val originalUri: String,
    @ColumnInfo(name = "added_at") val addedAt: Long
)

@Entity(tableName = "intruder_logs")
data class IntruderLog(
    @PrimaryKey val id: String,
    val timestamp: Long,
    @ColumnInfo(name = "photo_path") val photoPath: String?,
    @ColumnInfo(name = "attempt_status") val attemptStatus: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val notes: String? = null,
    val batteryLevel: Int? = null,
    val networkStatus: String? = null,
    val cameraFacing: String? = "Front Camera"
)

@Entity(tableName = "pin_vault")
data class PinVaultEntry(
    @PrimaryKey val id: String,
    val title: String,
    @ColumnInfo(name = "pin_encrypted") val pinEncrypted: String,
    val category: String, // "ATM", "WiFi", "Social Media", "Locker", "Custom"
    val note: String?,
    @ColumnInfo(name = "created_at") val createdAt: String
)

@Entity(tableName = "photo_vault")
data class PhotoVaultEntry(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "encrypted_file_path") val encryptedFilePath: String,
    @ColumnInfo(name = "original_file_path") val originalFilePath: String,
    @ColumnInfo(name = "mime_type") val mimeType: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "is_video") val isVideo: Int = 0
)

@Entity(tableName = "private_notes")
data class PrivateNoteEntry(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title_encrypted") val titleEncrypted: String,
    @ColumnInfo(name = "content_encrypted") val contentEncrypted: String,
    val color: String?,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "is_decoy") val isDecoy: Int = 0
)

@Entity(tableName = "wifi_devices")
data class WifiDevice(
    @PrimaryKey val macAddress: String,
    val ipAddress: String,
    val hostname: String?,
    val manufacturer: String?,
    val customName: String?,
    @ColumnInfo(name = "first_seen") val firstSeen: Long,
    @ColumnInfo(name = "last_seen") val lastSeen: Long,
    @ColumnInfo(name = "seen_count") val seenCount: Int,
    @ColumnInfo(name = "is_known") val isKnown: Int = 0 // 1 = known, 0 = unknown/new
)

@Entity(tableName = "speed_test_history")
data class SpeedTestHistory(
    @PrimaryKey val id: String,
    val timestamp: Long,
    @ColumnInfo(name = "download_speed") val downloadSpeedMbps: Double,
    @ColumnInfo(name = "upload_speed") val uploadSpeedMbps: Double
)

@Entity(tableName = "cached_quran_verses")
data class CachedQuranVerse(
    @PrimaryKey val id: String, // format: "surah_verse"
    @ColumnInfo(name = "surah_number") val surahNumber: Int,
    @ColumnInfo(name = "verse_number") val verseNumber: Int,
    @ColumnInfo(name = "juz_number") val juz: Int,
    @ColumnInfo(name = "page_number") val page: Int,
    @ColumnInfo(name = "text_arabic") val textArabic: String,
    @ColumnInfo(name = "text_urdu") val textUrdu: String,
    @ColumnInfo(name = "text_english") val textEnglish: String
)




// --- POS SYSTEM ---
@Entity(tableName = "pos_products")
data class PosProduct(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val unit: String
)

@Entity(tableName = "pos_clients")
data class PosClient(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val type: String // "Customer" or "Supplier"
)

@Entity(tableName = "pos_orders")
data class PosOrder(
    @PrimaryKey val id: String,
    val date: String,
    val clientId: String?,
    val subtotal: Double,
    val tax: Double,
    val discount: Double,
    val total: Double,
    val documentType: String // "Invoice", "Receipt", "Estimate"
)

@Entity(tableName = "pos_order_items")
data class PosOrderItem(
    @PrimaryKey val id: String,
    val orderId: String,
    val productId: String,
    val name: String,
    val quantity: Int,
    val price: Double
)
