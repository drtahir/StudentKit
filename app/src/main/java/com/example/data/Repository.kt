package com.example.data

import kotlinx.coroutines.flow.Flow

class StudentKitRepository(private val dao: StudentKitDao) {

    // --- EXPENSES ---
    val allExpenses: Flow<List<Expense>> = dao.getAllExpenses()
    suspend fun insertExpense(expense: Expense) = dao.insertExpense(expense)
    suspend fun deleteExpenseById(id: String) = dao.deleteExpenseById(id)
    fun getExpensesByMonth(yearMonth: String): Flow<List<Expense>> = dao.getExpensesByMonth(yearMonth)

    // --- INCOME ---
    val allIncome: Flow<List<Income>> = dao.getAllIncome()
    suspend fun insertIncome(income: Income) = dao.insertIncome(income)
    suspend fun deleteIncomeById(id: String) = dao.deleteIncomeById(id)
    fun getIncomeByMonth(yearMonth: String): Flow<List<Income>> = dao.getIncomeByMonth(yearMonth)

    // --- BILLS ---
    val allBills: Flow<List<Bill>> = dao.getAllBills()
    val allUnpaidBills: Flow<List<Bill>> = dao.getAllUnpaidBills()
    suspend fun insertBill(bill: Bill) = dao.insertBill(bill)
    suspend fun setBillPaidStatus(id: String, isPaid: Int) = dao.setBillPaidStatus(id, isPaid)
    suspend fun deleteBillById(id: String) = dao.deleteBillById(id)

    // --- BC COMMITTEES ---
    val allCommittees: Flow<List<BcCommittee>> = dao.getAllCommittees()
    suspend fun insertCommittee(committee: BcCommittee) = dao.insertCommittee(committee)
    suspend fun deleteCommitteeById(id: String) = dao.deleteCommitteeById(id)

    // --- BC MEMBERS ---
    fun getMembersByCommittee(committeeId: String): Flow<List<BcMember>> = dao.getMembersByCommittee(committeeId)
    suspend fun insertMembers(members: List<BcMember>) = dao.insertMembers(members)
    suspend fun updateMemberReceivedStatus(memberId: String, hasReceived: Int) = dao.updateMemberReceivedStatus(memberId, hasReceived)

    // --- BC PAYMENTS ---
    fun getPaymentsByCommittee(committeeId: String): Flow<List<BcPayment>> = dao.getPaymentsByCommittee(committeeId)
    fun getBCPaymentsForMonth(committeeId: String, month: String): Flow<List<BcPayment>> = dao.getBCPaymentsForMonth(committeeId, month)
    suspend fun insertPayments(payments: List<BcPayment>) = dao.insertPayments(payments)
    suspend fun setPaymentPaidStatus(paymentId: String, isPaid: Int, paidDate: String?) = dao.setPaymentPaidStatus(paymentId, isPaid, paidDate)

    // --- BC HISTORY ---
    fun getHistoryByCommittee(committeeId: String): Flow<List<BcHistory>> = dao.getHistoryByCommittee(committeeId)
    suspend fun insertHistory(history: BcHistory) = dao.insertHistory(history)

    // --- LOANS ---
    val allLoans: Flow<List<Loan>> = dao.getAllLoans()
    suspend fun insertLoan(loan: Loan) = dao.insertLoan(loan)
    suspend fun setLoanSettledStatus(id: String, isSettled: Int) = dao.setLoanSettledStatus(id, isSettled)
    suspend fun deleteLoanById(id: String) = dao.deleteLoanById(id)

    // --- SAVINGS GOALS ---
    val allSavingsGoals: Flow<List<SavingsGoal>> = dao.getAllSavingsGoals()
    suspend fun insertSavingsGoal(goal: SavingsGoal) = dao.insertSavingsGoal(goal)
    suspend fun updateSavingsGoalAmount(id: String, amount: Double) = dao.updateSavingsGoalAmount(id, amount)
    suspend fun deleteSavingsGoalById(id: String) = dao.deleteSavingsGoalById(id)

    // --- NOTES ---
    val allNotes: Flow<List<NoteEntry>> = dao.getAllNotes()
    suspend fun insertNote(note: NoteEntry) = dao.insertNote(note)
    suspend fun deleteNote(note: NoteEntry) = dao.deleteNote(note)
    suspend fun deleteNoteById(id: String) = dao.deleteNoteById(id)

    // --- TASKS ---
    val allTasks: Flow<List<TaskEntry>> = dao.getAllTasks()
    suspend fun insertTask(task: TaskEntry) = dao.insertTask(task)
    suspend fun setTaskDoneStatus(id: String, isDone: Int) = dao.setTaskDoneStatus(id, isDone)
    suspend fun deleteTaskById(id: String) = dao.deleteTaskById(id)

    // --- TIMETABLE ---
    val allTimetableClasses: Flow<List<TimetableEntry>> = dao.getAllTimetableClasses()
    suspend fun insertTimetableClass(classEntry: TimetableEntry) = dao.insertTimetableClass(classEntry)
    suspend fun deleteTimetableClassById(id: String) = dao.deleteTimetableClassById(id)

    // --- PASSWORDS ---
    val allPasswords: Flow<List<PasswordEntry>> = dao.getAllPasswords()
    suspend fun insertPassword(password: PasswordEntry) = dao.insertPassword(password)
    suspend fun deletePasswordById(id: String) = dao.deletePasswordById(id)

    // --- TEMP PDF IMAGES ---
    val allTempPdfImages: Flow<List<TempPdfImage>> = dao.getAllTempPdfImages()
    suspend fun insertTempPdfImage(image: TempPdfImage) = dao.insertTempPdfImage(image)
    suspend fun deleteTempPdfImageById(id: String) = dao.deleteTempPdfImageById(id)
    suspend fun clearAllTempPdfImages() = dao.clearAllTempPdfImages()

    // --- INTRUDER LOGS ---
    val allIntruderLogs: Flow<List<IntruderLog>> = dao.getAllIntruderLogs()
    suspend fun insertIntruderLog(log: IntruderLog) = dao.insertIntruderLog(log)
    suspend fun deleteIntruderLogById(id: String) = dao.deleteIntruderLogById(id)
    suspend fun clearAllIntruderLogs() = dao.clearAllIntruderLogs()

    // --- PIN VAULT ---
    val allPinVaultEntries: Flow<List<PinVaultEntry>> = dao.getAllPinVaultEntries()
    suspend fun insertPinVaultEntry(entry: PinVaultEntry) = dao.insertPinVaultEntry(entry)
    suspend fun deletePinVaultEntryById(id: String) = dao.deletePinVaultEntryById(id)

    // --- PHOTO VAULT ---
    val allPhotoVaultEntries: Flow<List<PhotoVaultEntry>> = dao.getAllPhotoVaultEntries()
    suspend fun insertPhotoVaultEntry(entry: PhotoVaultEntry) = dao.insertPhotoVaultEntry(entry)
    suspend fun deletePhotoVaultEntryById(id: String) = dao.deletePhotoVaultEntryById(id)

    // --- PRIVATE NOTES ---
    val allPrivateNoteEntries: Flow<List<PrivateNoteEntry>> = dao.getAllPrivateNoteEntries()
    suspend fun insertPrivateNoteEntry(entry: PrivateNoteEntry) = dao.insertPrivateNoteEntry(entry)
    suspend fun deletePrivateNoteEntryById(id: String) = dao.deletePrivateNoteEntryById(id)

    // --- WIFI MONITOR ---
    val allWifiDevices: Flow<List<WifiDevice>> = dao.getAllWifiDevices()
    suspend fun insertWifiDevice(device: WifiDevice) = dao.insertWifiDevice(device)
    suspend fun renameWifiDevice(mac: String, customName: String?, isKnown: Int) = dao.renameWifiDevice(mac, customName, isKnown)
    suspend fun getWifiDeviceByMac(mac: String): WifiDevice? = dao.getWifiDeviceByMac(mac)
    suspend fun deleteWifiDeviceByMac(mac: String) = dao.deleteWifiDeviceByMac(mac)

    val allSpeedTestHistory: Flow<List<SpeedTestHistory>> = dao.getAllSpeedTestHistory()
    suspend fun insertSpeedTestHistory(history: SpeedTestHistory) = dao.insertSpeedTestHistory(history)
    suspend fun clearSpeedTestHistory() = dao.clearSpeedTestHistory()
}
