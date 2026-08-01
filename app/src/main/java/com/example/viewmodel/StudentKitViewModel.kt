package com.example.viewmodel

import android.app.Application
import android.content.Context
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
    object ImageToWord : Screen()
    object CvBuilder : Screen()
    object DocumentScanner : Screen()
    object IdCardScanner : Screen()
    object PassportScanner : Screen()
    object PdfTools : Screen()
    object InvoiceGenerator : Screen()
    object QrGenerator : Screen()
    object QrScanner : Screen()
    object WifiQrGenerator : Screen()
    object Calculator : Screen()
    object UnitConverter : Screen()
    object PasswordManager : Screen()
    object ImageTools : Screen()
    object Notes : Screen()
    object StudyTimer : Screen()
    object Timetable : Screen()
    object BmiCalculator : Screen()
    object GpaCalculator : Screen()
    object AgeCalculator : Screen()
    object IvCalculator : Screen()
    object DosageCalculator : Screen()
    object GfrCalculator : Screen()
    object AnatomyAtlas : Screen()
    object PharmacyExam : Screen()
    object NursingExam : Screen()
    object HajjMedicalPrep : Screen()
    object MoavineenHujjajPrep : Screen()
    object IslamicHub : Screen()
    object IntruderGuard : Screen()
    object SignaturePad : Screen()
    object WatermarkStudio : Screen()
    object BackgroundEraser : Screen()
    object FileEncryptor : Screen()
    object HiddenLocker : Screen()
    object Steganography : Screen()
    object Steganalysis : Screen()
    object ImageEnhancer : Screen()
    
    // --- SECURITY SUITE ---
    object SecurityHub : Screen()
    object PinVault : Screen()
    object AppLock : Screen()
    object CalculatorVault : Screen()
    object PhotoVault : Screen()
    object PrivateNotes : Screen()
    object SecureDelete : Screen()
    object PermissionAuditor : Screen()
    object WifiScanner : Screen()
    object UssdCheck : Screen()
    object ThermalPrinterManager : Screen()
    object BiometricManagerScreen : Screen()
    object FinanceReportAndBackup : Screen()
    object About : Screen()
    object Settings : Screen()
}

class StudentKitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentKitRepository
    private val prefs = application.getSharedPreferences("student_kit_prefs", Context.MODE_PRIVATE)

    private val _isDarkTheme = MutableStateFlow<Boolean?>(
        if (prefs.contains("dark_theme")) prefs.getBoolean("dark_theme", false) else null
    )
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    fun setDarkTheme(enabled: Boolean?) {
        _isDarkTheme.value = enabled
        if (enabled == null) {
            prefs.edit().remove("dark_theme").apply()
        } else {
            prefs.edit().putBoolean("dark_theme", enabled).apply()
        }
    }

    // --- USER PROFILE REGISTRATION STATES ---
    private val _userName = MutableStateFlow(prefs.getString("user_name", "") ?: "")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userOccupation = MutableStateFlow(prefs.getString("user_occupation", "") ?: "")
    val userOccupation: StateFlow<String> = _userOccupation.asStateFlow()

    private val _userEmail = MutableStateFlow(prefs.getString("user_email", "") ?: "")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    private val _userPhone = MutableStateFlow(prefs.getString("user_phone", "") ?: "")
    val userPhone: StateFlow<String> = _userPhone.asStateFlow()

    private val _userCity = MutableStateFlow(prefs.getString("user_city", "") ?: "")
    val userCity: StateFlow<String> = _userCity.asStateFlow()

    fun saveUserProfile(name: String, occupation: String, email: String, phone: String, city: String) {
        _userName.value = name.trim()
        _userOccupation.value = occupation.trim()
        _userEmail.value = email.trim()
        _userPhone.value = phone.trim()
        _userCity.value = city.trim()

        prefs.edit()
            .putString("user_name", name.trim())
            .putString("user_occupation", occupation.trim())
            .putString("user_email", email.trim())
            .putString("user_phone", phone.trim())
            .putString("user_city", city.trim())
            .apply()
    }

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

    // --- TEMP PDF IMAGES ---
    val allTempPdfImages: StateFlow<List<TempPdfImage>> = repository.allTempPdfImages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- INTRUDER LOGS ---
    val intruderLogs: StateFlow<List<IntruderLog>> = repository.allIntruderLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- SECURITY FLOWS ---
    val pinVaultEntries: StateFlow<List<PinVaultEntry>> = repository.allPinVaultEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val photoVaultEntries: StateFlow<List<PhotoVaultEntry>> = repository.allPhotoVaultEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val privateNoteEntries: StateFlow<List<PrivateNoteEntry>> = repository.allPrivateNoteEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- WIFI MONITOR FLOWS ---
    val wifiDevices: StateFlow<List<WifiDevice>> = repository.allWifiDevices
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val speedTestHistory: StateFlow<List<SpeedTestHistory>> = repository.allSpeedTestHistory
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
    fun getHistoryByCommittee(committeeId: String): Flow<List<BcHistory>> = repository.getHistoryByCommittee(committeeId)

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

    fun recordLuckyDraw(committeeId: String, roundNumber: Int, winnerId: String, winnerName: String, amountWon: Double) {
        viewModelScope.launch {
            val drawId = UUID.randomUUID().toString()
            val drawDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val draw = BcHistory(
                id = drawId,
                committeeId = committeeId,
                roundNumber = roundNumber,
                winnerId = winnerId,
                winnerName = winnerName,
                amountWon = amountWon,
                drawDate = drawDate
            )
            repository.insertHistory(draw)
            repository.updateMemberReceivedStatus(winnerId, 1)
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
    var vaultKeyBytes by mutableStateOf<ByteArray?>(null)

    fun unlockVault(pin: String) {
        val salt = "com.example.studentkit.salt.1234".toByteArray()
        vaultKeyBytes = Argon2.deriveKey(pin.toByteArray(), salt, mCost = 1024, tCost = 2)
    }

    fun lockVault() {
        vaultKeyBytes = null
    }

    fun decryptStoredPassword(encryptedPass: String): String {
        val key = vaultKeyBytes ?: return "••••••••"
        return try {
            Argon2.decryptWithArgon2Key(encryptedPass, key)
        } catch (e: Exception) {
            "Decryption Error"
        }
    }

    fun addSecurePassword(title: String, username: String?, plainPass: String, category: String?, website: String?, note: String?) {
        val key = vaultKeyBytes ?: return
        viewModelScope.launch {
            val id = java.util.UUID.randomUUID().toString()
            val encryptedPass = Argon2.encryptWithArgon2Key(plainPass, key)
            val created = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertPassword(PasswordEntry(id, title, username, encryptedPass, category, website, note, created))
        }
    }

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
    var bmiWeightUnit by mutableStateOf("KG") // "KG" vs "LBS"
    var bmiHeightUnit by mutableStateOf("CM") // "CM" vs "FT_IN"
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

    // --- TEMP PDF IMAGES OPERATIONS ---
    fun addTempPdfImage(filePath: String, originalUri: String) {
        viewModelScope.launch {
            val image = TempPdfImage(
                id = UUID.randomUUID().toString(),
                filePath = filePath,
                originalUri = originalUri,
                addedAt = System.currentTimeMillis()
            )
            repository.insertTempPdfImage(image)
        }
    }

    fun removeTempPdfImage(id: String) {
        viewModelScope.launch {
            repository.deleteTempPdfImageById(id)
        }
    }

    fun clearAllTempPdfImages() {
        viewModelScope.launch {
            repository.clearAllTempPdfImages()
        }
    }

    // --- INTRUDER LOG OPERATIONS ---
    fun addIntruderLog(
        photoPath: String?,
        status: String,
        notes: String? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        batteryLevel: Int? = null,
        networkStatus: String? = null,
        cameraFacing: String? = "Front Camera"
    ) {
        viewModelScope.launch {
            val log = IntruderLog(
                id = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                photoPath = photoPath,
                attemptStatus = status,
                latitude = latitude,
                longitude = longitude,
                notes = notes,
                batteryLevel = batteryLevel,
                networkStatus = networkStatus,
                cameraFacing = cameraFacing
            )
            repository.insertIntruderLog(log)
        }
    }

    fun removeIntruderLog(id: String) {
        viewModelScope.launch {
            repository.deleteIntruderLogById(id)
        }
    }

    fun clearAllIntruderLogs() {
        viewModelScope.launch {
            repository.clearAllIntruderLogs()
        }
    }

    // --- SECURITY SUITE: PIN VAULT ---
    fun addPinVaultEntry(title: String, plainPin: String, category: String, note: String?) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val encryptedPin = KeystoreHelper.encryptString(plainPin)
            val createdAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertPinVaultEntry(PinVaultEntry(id, title, encryptedPin, category, note, createdAt))
        }
    }

    fun deletePinVaultEntry(id: String) {
        viewModelScope.launch {
            repository.deletePinVaultEntryById(id)
        }
    }

    // --- SECURITY SUITE: PHOTO VAULT ---
    fun addPhotoVaultEntry(fileName: String, encryptedFilePath: String, originalFilePath: String, mimeType: String, isVideo: Int) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val createdAt = System.currentTimeMillis()
            repository.insertPhotoVaultEntry(PhotoVaultEntry(id, fileName, encryptedFilePath, originalFilePath, mimeType, createdAt, isVideo))
        }
    }

    fun deletePhotoVaultEntry(id: String) {
        viewModelScope.launch {
            repository.deletePhotoVaultEntryById(id)
        }
    }

    // --- SECURITY SUITE: PRIVATE NOTES ---
    fun addPrivateNoteEntry(title: String, content: String, color: String?, isDecoy: Int = 0) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val encryptedTitle = KeystoreHelper.encryptString(title)
            val encryptedContent = KeystoreHelper.encryptString(content)
            val createdAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            repository.insertPrivateNoteEntry(PrivateNoteEntry(id, encryptedTitle, encryptedContent, color, createdAt, isDecoy))
        }
    }

    fun updatePrivateNoteEntry(id: String, title: String, content: String, color: String?, createdAt: String, isDecoy: Int) {
        viewModelScope.launch {
            val encryptedTitle = KeystoreHelper.encryptString(title)
            val encryptedContent = KeystoreHelper.encryptString(content)
            repository.insertPrivateNoteEntry(PrivateNoteEntry(id, encryptedTitle, encryptedContent, color, createdAt, isDecoy))
        }
    }

    fun deletePrivateNoteEntry(id: String) {
        viewModelScope.launch {
            repository.deletePrivateNoteEntryById(id)
        }
    }

    // --- WIFI MONITOR OPERATIONS ---
    fun addWifiDevice(device: WifiDevice) {
        viewModelScope.launch {
            repository.insertWifiDevice(device)
        }
    }

    fun renameWifiDevice(mac: String, customName: String?, isKnown: Int) {
        viewModelScope.launch {
            repository.renameWifiDevice(mac, customName, isKnown)
        }
    }

    fun deleteWifiDevice(mac: String) {
        viewModelScope.launch {
            repository.deleteWifiDeviceByMac(mac)
        }
    }

    fun addSpeedTestHistory(download: Double, upload: Double) {
        viewModelScope.launch {
            val history = SpeedTestHistory(
                id = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                downloadSpeedMbps = download,
                uploadSpeedMbps = upload
            )
            repository.insertSpeedTestHistory(history)
        }
    }

    fun clearSpeedTestHistory() {
        viewModelScope.launch {
            repository.clearSpeedTestHistory()
        }
    }

    // --- QURAN CACHE METHODS ---
    fun getCachedVersesForSurah(surahNumber: Int): Flow<List<com.example.data.CachedQuranVerse>> = repository.getCachedVersesForSurah(surahNumber)
    fun getCachedVersesForPage(pageNumber: Int): Flow<List<com.example.data.CachedQuranVerse>> = repository.getCachedVersesForPage(pageNumber)
    
    fun insertQuranVerses(verses: List<com.example.data.CachedQuranVerse>) {
        viewModelScope.launch {
            repository.insertQuranVerses(verses)
        }
    }
    
    suspend fun getCachedQuranVersesCount(): Int {
        return repository.getCachedQuranVersesCount()
    }
    
    fun clearCachedQuran() {
        viewModelScope.launch {
            repository.clearCachedQuran()
        }
    }

    // --- POS SYSTEM ---
    val allPosProducts: StateFlow<List<PosProduct>> = repository.allPosProducts.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun insertPosProduct(product: PosProduct) = viewModelScope.launch { repository.insertPosProduct(product) }
    fun deletePosProductById(id: String) = viewModelScope.launch { repository.deletePosProductById(id) }

    val allPosClients: StateFlow<List<PosClient>> = repository.allPosClients.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun insertPosClient(client: PosClient) = viewModelScope.launch { repository.insertPosClient(client) }
    fun deletePosClientById(id: String) = viewModelScope.launch { repository.deletePosClientById(id) }

    val allPosOrders: StateFlow<List<PosOrder>> = repository.allPosOrders.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun insertPosOrder(order: PosOrder) = viewModelScope.launch { repository.insertPosOrder(order) }
    fun insertPosOrderItem(item: PosOrderItem) = viewModelScope.launch { repository.insertPosOrderItem(item) }

    fun importFinanceJsonData(jsonString: String, onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val root = org.json.JSONObject(jsonString)
                
                // 1. Import Expenses
                if (root.has("expenses")) {
                    val array = root.getJSONArray("expenses")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                        val title = obj.optString("title", "")
                        val amount = obj.optDouble("amount", 0.0)
                        val category = obj.optString("category", "General")
                        val date = obj.optString("date", "")
                        val note = if (obj.isNull("note")) null else obj.optString("note", null)
                        val isRecurring = obj.optInt("is_recurring", 0)
                        repository.insertExpense(Expense(id, title, amount, category, date, note, isRecurring))
                    }
                }

                // 2. Import Income
                if (root.has("income")) {
                    val array = root.getJSONArray("income")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                        val title = obj.optString("title", "")
                        val amount = obj.optDouble("amount", 0.0)
                        val source = obj.optString("source", "General")
                        val date = obj.optString("date", "")
                        val note = if (obj.isNull("note")) null else obj.optString("note", null)
                        repository.insertIncome(Income(id, title, amount, source, date, note))
                    }
                }

                // 3. Import Bills
                if (root.has("bills")) {
                    val array = root.getJSONArray("bills")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                        val name = obj.optString("name", "")
                        val amount = obj.optDouble("amount", 0.0)
                        val dueDate = obj.optString("due_date", "")
                        val isPaid = obj.optInt("is_paid", 0)
                        val category = obj.optString("category", "General")
                        val isRecurring = obj.optInt("is_recurring", 1)
                        repository.insertBill(Bill(id, name, amount, dueDate, isPaid, category, isRecurring))
                    }
                }

                // 4. Import Loans
                if (root.has("loans")) {
                    val array = root.getJSONArray("loans")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                        val personName = obj.optString("person_name", "")
                        val amount = obj.optDouble("amount", 0.0)
                        val type = obj.optString("type", "I Lent")
                        val date = obj.optString("date", "")
                        val dueDate = if (obj.isNull("due_date")) null else obj.optString("due_date", null)
                        val isSettled = obj.optInt("is_settled", 0)
                        val note = if (obj.isNull("note")) null else obj.optString("note", null)
                        repository.insertLoan(Loan(id, personName, amount, type, date, dueDate, isSettled, note))
                    }
                }

                // 5. Import Savings Goals
                if (root.has("savings_goals")) {
                    val array = root.getJSONArray("savings_goals")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                        val title = obj.optString("title", "")
                        val targetAmount = obj.optDouble("target_amount", 0.0)
                        val currentAmount = obj.optDouble("current_amount", 0.0)
                        val targetDate = if (obj.isNull("target_date")) null else obj.optString("target_date", null)
                        val icon = if (obj.isNull("icon")) null else obj.optString("icon", null)
                        val color = if (obj.isNull("color")) null else obj.optString("color", null)
                        repository.insertSavingsGoal(SavingsGoal(id, title, targetAmount, currentAmount, targetDate, icon, color))
                    }
                }

                // 6. Import Committees
                if (root.has("bc_committees")) {
                    val array = root.getJSONArray("bc_committees")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        val id = obj.optString("id", java.util.UUID.randomUUID().toString())
                        val name = obj.optString("name", "")
                        val amountPerHead = obj.optDouble("amount_per_head", 0.0)
                        val totalMembers = obj.optInt("total_members", 0)
                        val startDate = obj.optString("start_date", "")
                        val frequency = obj.optString("frequency", "monthly")
                        repository.insertCommittee(BcCommittee(id, name, amountPerHead, totalMembers, startDate, frequency))
                    }
                }
                if (root.has("bc_members")) {
                    val array = root.getJSONArray("bc_members")
                    val membersList = mutableListOf<BcMember>()
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        membersList.add(BcMember(
                            id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                            committeeId = obj.optString("committee_id", ""),
                            name = obj.optString("name", ""),
                            phone = if (obj.isNull("phone")) null else obj.optString("phone", null),
                            payoutPosition = obj.optInt("payout_position", 1),
                            hasReceived = obj.optInt("has_received", 0)
                        ))
                    }
                    if (membersList.isNotEmpty()) {
                        repository.insertMembers(membersList)
                    }
                }
                if (root.has("bc_payments")) {
                    val array = root.getJSONArray("bc_payments")
                    val paymentsList = mutableListOf<BcPayment>()
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        paymentsList.add(BcPayment(
                            id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                            committeeId = obj.optString("committee_id", ""),
                            memberId = obj.optString("member_id", ""),
                            month = obj.optString("month", ""),
                            isPaid = obj.optInt("is_paid", 0),
                            paidDate = if (obj.isNull("paid_date")) null else obj.optString("paid_date", null)
                        ))
                    }
                    if (paymentsList.isNotEmpty()) {
                        repository.insertPayments(paymentsList)
                    }
                }
                if (root.has("bc_history")) {
                    val array = root.getJSONArray("bc_history")
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        repository.insertHistory(BcHistory(
                            id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                            committeeId = obj.optString("committee_id", ""),
                            roundNumber = obj.optInt("round_number", 1),
                            winnerId = obj.optString("winner_id", ""),
                            winnerName = obj.optString("winner_name", ""),
                            amountWon = obj.optDouble("amount_won", 0.0),
                            drawDate = obj.optString("draw_date", "")
                        ))
                    }
                }

                onComplete(true, "All selected modular data imported successfully!")
            } catch (e: Exception) {
                onComplete(false, "Import failed: ${e.message}")
            }
        }
    }

    fun exportFinanceJsonData(onComplete: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val root = org.json.JSONObject()

                // 1. Export Expenses
                val expArray = org.json.JSONArray()
                for (exp in expenses.value) {
                    val obj = org.json.JSONObject()
                    obj.put("id", exp.id)
                    obj.put("title", exp.title)
                    obj.put("amount", exp.amount)
                    obj.put("category", exp.category)
                    obj.put("date", exp.date)
                    obj.put("note", exp.note ?: org.json.JSONObject.NULL)
                    obj.put("is_recurring", exp.isRecurring)
                    expArray.put(obj)
                }
                root.put("expenses", expArray)

                // 2. Export Income
                val incArray = org.json.JSONArray()
                for (inc in income.value) {
                    val obj = org.json.JSONObject()
                    obj.put("id", inc.id)
                    obj.put("title", inc.title)
                    obj.put("amount", inc.amount)
                    obj.put("source", inc.source)
                    obj.put("date", inc.date)
                    obj.put("note", inc.note ?: org.json.JSONObject.NULL)
                    incArray.put(obj)
                }
                root.put("income", incArray)

                // 3. Export Bills
                val billArray = org.json.JSONArray()
                for (bill in bills.value) {
                    val obj = org.json.JSONObject()
                    obj.put("id", bill.id)
                    obj.put("name", bill.name)
                    obj.put("amount", bill.amount)
                    obj.put("due_date", bill.dueDate)
                    obj.put("is_paid", bill.isPaid)
                    obj.put("category", bill.category)
                    obj.put("is_recurring", bill.isRecurring)
                    billArray.put(obj)
                }
                root.put("bills", billArray)

                // 4. Export Loans
                val loanArray = org.json.JSONArray()
                for (loan in loans.value) {
                    val obj = org.json.JSONObject()
                    obj.put("id", loan.id)
                    obj.put("person_name", loan.personName)
                    obj.put("amount", loan.amount)
                    obj.put("type", loan.type)
                    obj.put("date", loan.date)
                    obj.put("due_date", loan.dueDate ?: org.json.JSONObject.NULL)
                    obj.put("is_settled", loan.isSettled)
                    obj.put("note", loan.note ?: org.json.JSONObject.NULL)
                    loanArray.put(obj)
                }
                root.put("loans", loanArray)

                // 5. Export Savings Goals
                val svArray = org.json.JSONArray()
                for (sg in savingsGoals.value) {
                    val obj = org.json.JSONObject()
                    obj.put("id", sg.id)
                    obj.put("title", sg.title)
                    obj.put("target_amount", sg.targetAmount)
                    obj.put("current_amount", sg.currentAmount)
                    obj.put("target_date", sg.targetDate ?: org.json.JSONObject.NULL)
                    obj.put("icon", sg.icon ?: org.json.JSONObject.NULL)
                    obj.put("color", sg.color ?: org.json.JSONObject.NULL)
                    svArray.put(obj)
                }
                root.put("savings_goals", svArray)

                // 6. Export Committees
                val commArray = org.json.JSONArray()
                for (comm in committees.value) {
                    val obj = org.json.JSONObject()
                    obj.put("id", comm.id)
                    obj.put("name", comm.name)
                    obj.put("amount_per_head", comm.amountPerHead)
                    obj.put("total_members", comm.totalMembers)
                    obj.put("start_date", comm.startDate)
                    obj.put("frequency", comm.frequency)
                    commArray.put(obj)
                }
                root.put("bc_committees", commArray)

                // 7. Export BC Members
                val membersList = repository.getAllMembersSuspend()
                val memArray = org.json.JSONArray()
                for (mem in membersList) {
                    val obj = org.json.JSONObject()
                    obj.put("id", mem.id)
                    obj.put("committee_id", mem.committeeId)
                    obj.put("name", mem.name)
                    obj.put("phone", mem.phone ?: org.json.JSONObject.NULL)
                    obj.put("payout_position", mem.payoutPosition)
                    obj.put("has_received", mem.hasReceived)
                    memArray.put(obj)
                }
                root.put("bc_members", memArray)

                // 8. Export BC Payments
                val paymentsList = repository.getAllPaymentsSuspend()
                val payArray = org.json.JSONArray()
                for (pay in paymentsList) {
                    val obj = org.json.JSONObject()
                    obj.put("id", pay.id)
                    obj.put("committee_id", pay.committeeId)
                    obj.put("member_id", pay.memberId)
                    obj.put("month", pay.month)
                    obj.put("is_paid", pay.isPaid)
                    obj.put("paid_date", pay.paidDate ?: org.json.JSONObject.NULL)
                    payArray.put(obj)
                }
                root.put("bc_payments", payArray)

                // 9. Export BC History
                val historyList = repository.getAllHistorySuspend()
                val histArray = org.json.JSONArray()
                for (hist in historyList) {
                    val obj = org.json.JSONObject()
                    obj.put("id", hist.id)
                    obj.put("committee_id", hist.committeeId)
                    obj.put("round_number", hist.roundNumber)
                    obj.put("winner_id", hist.winnerId)
                    obj.put("winner_name", hist.winnerName)
                    obj.put("amount_won", hist.amountWon)
                    obj.put("draw_date", hist.drawDate)
                    histArray.put(obj)
                }
                root.put("bc_history", histArray)

                onComplete(root.toString(4))
            } catch (e: Exception) {
                onComplete(null)
            }
        }
    }
}

// Simple mutableStateListOf wrapper helper
fun <T> mutableStateListOf(vararg elements: T): androidx.compose.runtime.snapshots.SnapshotStateList<T> {
    val list = androidx.compose.runtime.snapshots.SnapshotStateList<T>()
    list.addAll(elements)
    return list
}
