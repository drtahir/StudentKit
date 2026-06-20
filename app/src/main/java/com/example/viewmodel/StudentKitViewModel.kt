package com.example.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

sealed class Screen {
    object Dashboard : Screen()
    object ExpenseTracker : Screen()
    object IncomeTracker : Screen()
    object UtilityBills : Screen()
    object ZakatCalculator : Screen()
    object BcCommittee : Screen()
    data class BcCommitteeDetails(val committeeId: String) : Screen()
    object LoanTracker : Screen()
    object SavingsGoals : Screen()
    object ImageToPdf : Screen()
    object ImageToXls : Screen()
    object CvBuilder : Screen()
    object DocumentScanner : Screen()
    object PdfTools : Screen()
    object QrGenerator : Screen()
    object QrScanner : Screen()
    object Calculator : Screen()
    object UnitConverter : Screen()
    object PasswordManager : Screen()
    object ImageTools : Screen()
    object Notes : Screen()
    object StudyTimer : Screen()
    object Timetable : Screen()
    object BmiCalculator : Screen()
}

class StudentKitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentKitRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = StudentKitRepository(database.dao())
    }

    // --- NAVIGATION BACKSTACK ---
    private val _backstack = MutableStateFlow<List<Screen>>(listOf(Screen.Dashboard))
    val currentScreen: StateFlow<Screen> = _backstack
        .map { it.lastOrNull() ?: Screen.Dashboard }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Screen.Dashboard)

    fun navigateTo(screen: Screen) {
        val currentList = _backstack.value.toMutableList()
        currentList.add(screen)
        _backstack.value = currentList
    }

    fun navigateBack(): Boolean {
        val currentList = _backstack.value.toMutableList()
        if (currentList.size > 1) {
            currentList.removeAt(currentList.lastIndex)
            _backstack.value = currentList
            return true
        }
        return false // Exits app or no-op
    }

    // --- DATA FLOWS ---
    val expenses: StateFlow<List<Expense>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val income: StateFlow<List<Income>> = repository.allIncome
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bills: StateFlow<List<Bill>> = repository.allBills
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unpaidBills: StateFlow<List<Bill>> = repository.allUnpaidBills
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val committees: StateFlow<List<BcCommittee>> = repository.allCommittees
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val loans: StateFlow<List<Loan>> = repository.allLoans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savingsGoals: StateFlow<List<SavingsGoal>> = repository.allSavingsGoals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notes: StateFlow<List<NoteEntry>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tasks: StateFlow<List<TaskEntry>> = repository.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val timetableClasses: StateFlow<List<TimetableEntry>> = repository.allTimetableClasses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val passwords: StateFlow<List<PasswordEntry>> = repository.allPasswords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // --- OPERATIONS: EXPENSES ---
    fun addExpense(title: String, amount: Double, category: String, date: String, note: String?, isRecurring: Int = 0) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.insertExpense(Expense(id, title, amount, category, date, note, isRecurring))
        }
    }

    fun deleteExpense(id: String) {
        viewModelScope.launch {
            repository.deleteExpenseById(id)
        }
    }


    // --- OPERATIONS: INCOME ---
    fun addIncome(title: String, amount: Double, source: String, date: String, note: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.insertIncome(Income(id, title, amount, source, date, note))
        }
    }

    fun deleteIncome(id: String) {
        viewModelScope.launch {
            repository.deleteIncomeById(id)
        }
    }


    // --- OPERATIONS: BILLS ---
    fun addBill(name: String, amount: Double, dueDate: String, category: String, isRecurring: Int = 1) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.insertBill(Bill(id, name, amount, dueDate, isPaid = 0, category, isRecurring))
        }
    }

    fun markBillPaid(id: String, isPaid: Boolean) {
        viewModelScope.launch {
            repository.setBillPaidStatus(id, if (isPaid) 1 else 0)
        }
    }

    fun deleteBill(id: String) {
        viewModelScope.launch {
            repository.deleteBillById(id)
        }
    }


    // --- OPERATIONS: BC COMMITTEES & SCHEDULING ---
    fun addCommittee(name: String, amountPerHead: Double, totalMembers: Int, startDate: String, frequency: String, memberNames: List<String>) {
        viewModelScope.launch {
            val committeeId = UUID.randomUUID().toString()
            val committee = BcCommittee(committeeId, name, amountPerHead, totalMembers, startDate, frequency)
            repository.insertCommittee(committee)

            // Auto-generate members and assigned payouts
            val members = memberNames.take(totalMembers).mapIndexed { index, memberName ->
                BcMember(
                    id = UUID.randomUUID().toString(),
                    committeeId = committeeId,
                    name = memberName,
                    phone = "",
                    payoutPosition = index + 1,
                    hasReceived = 0
                )
            }
            repository.insertMembers(members)

            // Auto-generate expected cycle payments
            // Frequency calculation standard month representation
            val payments = mutableListOf<BcPayment>()
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calendar = Calendar.getInstance()
            try {
                sdf.parse(startDate)?.let { calendar.time = it }
            } catch (e: Exception) {
                // fallback
            }

            for (monthIndex in 0 until totalMembers) {
                val cycleMonthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.time)
                members.forEach { m ->
                    payments.add(
                        BcPayment(
                            id = UUID.randomUUID().toString(),
                            committeeId = committeeId,
                            memberId = m.id,
                            month = cycleMonthName,
                            isPaid = 0,
                            paidDate = null
                        )
                    )
                }
                // increment calendar
                if (frequency.lowercase() == "monthly") {
                    calendar.add(Calendar.MONTH, 1)
                } else if (frequency.lowercase() == "weekly") {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1)
                } else {
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }
            }
            repository.insertPayments(payments)
        }
    }

    fun getMembersByCommittee(committeeId: String): Flow<List<BcMember>> = repository.getMembersByCommittee(committeeId)
    fun getPaymentsByCommittee(committeeId: String): Flow<List<BcPayment>> = repository.getPaymentsByCommittee(committeeId)

    fun markPaymentStatus(paymentId: String, isPaid: Boolean) {
        viewModelScope.launch {
            val paidDate = if (isPaid) SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) else null
            repository.setPaymentPaidStatus(paymentId, if (isPaid) 1 else 0, paidDate)
        }
    }

    fun setMemberReceivedPayout(memberId: String, hasReceived: Boolean) {
        viewModelScope.launch {
            repository.updateMemberReceivedStatus(memberId, if (hasReceived) 1 else 0)
        }
    }

    fun deleteCommittee(id: String) {
        viewModelScope.launch {
            repository.deleteCommitteeById(id)
        }
    }


    // --- OPERATIONS: LOANS ---
    fun addLoan(personName: String, amount: Double, type: String, date: String, dueDate: String?, note: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.insertLoan(Loan(id, personName, amount, type, date, dueDate, isSettled = 0, note))
        }
    }

    fun markLoanSettled(id: String, isSettled: Boolean) {
        viewModelScope.launch {
            repository.setLoanSettledStatus(id, if (isSettled) 1 else 0)
        }
    }

    fun deleteLoan(id: String) {
        viewModelScope.launch {
            repository.deleteLoanById(id)
        }
    }


    // --- OPERATIONS: SAVINGS GOALS ---
    fun addSavingsGoal(title: String, targetAmount: Double, currentAmount: Double, targetDate: String?, icon: String?, color: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.insertSavingsGoal(SavingsGoal(id, title, targetAmount, currentAmount, targetDate, icon, color))
        }
    }

    fun depositToSavingsGoal(id: String, currentAmount: Double, depositAmount: Double) {
        viewModelScope.launch {
            repository.updateSavingsGoalAmount(id, currentAmount + depositAmount)
        }
    }

    fun deleteSavingsGoal(id: String) {
        viewModelScope.launch {
            repository.deleteSavingsGoalById(id)
        }
    }


    // --- OPERATIONS: NOTES ---
    fun addNote(title: String, content: String, color: String, isPinned: Boolean = false) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertNote(NoteEntry(id, title, content, color, createdAt = now, updatedAt = now, isPinned = if (isPinned) 1 else 0))
        }
    }

    fun updateNote(id: String, title: String, content: String, color: String, isPinned: Boolean) {
        viewModelScope.launch {
            val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertNote(NoteEntry(id, title, content, color, createdAt = now, updatedAt = now, isPinned = if (isPinned) 1 else 0))
        }
    }

    fun deleteNote(note: NoteEntry) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }


    // --- OPERATIONS: TASKS & AGENDA ---
    fun addTask(title: String, dueDate: String?, subject: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val created = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertTask(TaskEntry(id, title, isDone = 0, dueDate, subject, createdAt = created))
        }
    }

    fun setTaskDone(id: String, isDone: Boolean) {
        viewModelScope.launch {
            repository.setTaskDoneStatus(id, if (isDone) 1 else 0)
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            repository.deleteTaskById(id)
        }
    }


    // --- OPERATIONS: TIMETABLE ---
    fun addTimetableClass(subject: String, teacher: String?, room: String?, dayOfWeek: Int, startTime: String, endTime: String, color: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            repository.insertTimetableClass(TimetableEntry(id, subject, teacher, room, dayOfWeek, startTime, endTime, color))
        }
    }

    fun deleteTimetableClass(id: String) {
        viewModelScope.launch {
            repository.deleteTimetableClassById(id)
        }
    }


    // --- OPERATIONS: PASSWORDS ---
    fun addPassword(title: String, username: String?, passwordEncrypted: String, category: String?, website: String?, note: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val created = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertPassword(PasswordEntry(id, title, username, passwordEncrypted, category, website, note, created))
        }
    }

    fun deletePassword(id: String) {
        viewModelScope.launch {
            repository.deletePasswordById(id)
        }
    }


    // --- NON-PERSISTED LIVE ANALYTICS STATES ---

    // Scientific Calculator
    var calculatorExpression by mutableStateOf("")
    var calculatorResult by mutableStateOf("")
    val calculatorHistory = mutableStateListOf<String>()

    // BMI Calculator
    var bmiHeightCm by mutableStateOf("170")
    var bmiWeightKg by mutableStateOf("65")
    var bmiHeightFt by mutableStateOf("5")
    var bmiHeightIn by mutableStateOf("7")
    var bmiWeightLbs by mutableStateOf("140")
    var bmiIsMetric by mutableStateOf(true)
    var bmiSelectionAct by mutableStateOf("Sedentary")
    var dailyWaterLitres by mutableStateOf(0.0)

    // Unit Converter
    var converterInput by mutableStateOf("1.0")

    // Zakat Prices
    var goldPricePerGram by mutableStateOf("25000") // standard PKR estimate
    var silverPricePerGram by mutableStateOf("3000") // standard PKR estimate

    // Password Generator Preferences
    var genPassLength by mutableStateOf(12f)
    var genPassUpper by mutableStateOf(true)
    var genPassLower by mutableStateOf(true)
    var genPassNumbers by mutableStateOf(true)
    var genPassSymbols by mutableStateOf(true)
    var genPassResult by mutableStateOf("")
    
    // Water Intake Logged
    var waterLoggedAmountML by mutableStateOf(0)

    // Study Focus Timer
    var timerStudyMinutes by mutableStateOf(25)
    var timerBreakMinutes by mutableStateOf(5)
    var timerTimeLeftSeconds by mutableStateOf(1500)
    var timerIsRunning by mutableStateOf(false)
    var timerMode by mutableStateOf("Study") // "Study", "Break", "Long Break"
    var timerActiveSubject by mutableStateOf("Aviation Systems")
    var focusMinutesLoggedToday by mutableStateOf(0)

    fun resetTimer() {
        timerIsRunning = false
        timerTimeLeftSeconds = if (timerMode == "Study") timerStudyMinutes * 60 else timerBreakMinutes * 60
    }

    fun logFocusSession() {
        focusMinutesLoggedToday += timerStudyMinutes
    }
}

// Simple mutableStateListOf wrapper helper
fun <T> mutableStateListOf(vararg elements: T): androidx.compose.runtime.snapshots.SnapshotStateList<T> {
    val list = androidx.compose.runtime.snapshots.SnapshotStateList<T>()
    list.addAll(elements)
    return list
}
