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
