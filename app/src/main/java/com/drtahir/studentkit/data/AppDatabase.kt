package com.drtahir.studentkit.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentKitDao {

    // --- EXPENSES ---
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteExpenseById(id: String)

    @Query("SELECT * FROM expenses WHERE date LIKE :yearMonth || '%'")
    fun getExpensesByMonth(yearMonth: String): Flow<List<Expense>>


    // --- INCOME ---
    @Query("SELECT * FROM income ORDER BY date DESC")
    fun getAllIncome(): Flow<List<Income>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: Income)

    @Delete
    suspend fun deleteIncome(income: Income)

    @Query("DELETE FROM income WHERE id = :id")
    suspend fun deleteIncomeById(id: String)

    @Query("SELECT * FROM income WHERE date LIKE :yearMonth || '%'")
    fun getIncomeByMonth(yearMonth: String): Flow<List<Income>>


    // --- BILLS ---
    @Query("SELECT * FROM bills ORDER BY due_date ASC")
    fun getAllBills(): Flow<List<Bill>>

    @Query("SELECT * FROM bills WHERE is_paid = 0 ORDER BY due_date ASC")
    fun getAllUnpaidBills(): Flow<List<Bill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: Bill)

    @Update
    suspend fun updateBill(bill: Bill)

    @Query("UPDATE bills SET is_paid = :isPaid WHERE id = :id")
    suspend fun setBillPaidStatus(id: String, isPaid: Int)

    @Query("DELETE FROM bills WHERE id = :id")
    suspend fun deleteBillById(id: String)


    // --- BC COMMITTEES ---
    @Query("SELECT * FROM bc_committees")
    fun getAllCommittees(): Flow<List<BcCommittee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommittee(committee: BcCommittee)

    @Query("DELETE FROM bc_committees WHERE id = :id")
    suspend fun deleteCommitteeById(id: String)


    // --- BC MEMBERS ---
    @Query("SELECT * FROM bc_members WHERE committee_id = :committeeId ORDER BY payout_position ASC")
    fun getMembersByCommittee(committeeId: String): Flow<List<BcMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(members: List<BcMember>)

    @Query("UPDATE bc_members SET has_received = :hasReceived WHERE id = :memberId")
    suspend fun updateMemberReceivedStatus(memberId: String, hasReceived: Int)


    // --- BC PAYMENTS ---
    @Query("SELECT * FROM bc_payments WHERE committee_id = :committeeId")
    fun getPaymentsByCommittee(committeeId: String): Flow<List<BcPayment>>

    @Query("SELECT * FROM bc_payments WHERE committee_id = :committeeId AND month = :month")
    fun getBCPaymentsForMonth(committeeId: String, month: String): Flow<List<BcPayment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayments(payments: List<BcPayment>)

    @Query("UPDATE bc_payments SET is_paid = :isPaid, paid_date = :paidDate WHERE id = :paymentId")
    suspend fun setPaymentPaidStatus(paymentId: String, isPaid: Int, paidDate: String?)


    // --- BC HISTORY ---
    @Query("SELECT * FROM bc_history WHERE committee_id = :committeeId ORDER BY round_number DESC")
    fun getHistoryByCommittee(committeeId: String): Flow<List<BcHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: BcHistory)

    @Query("SELECT * FROM bc_members")
    suspend fun getAllMembersSuspend(): List<BcMember>

    @Query("SELECT * FROM bc_payments")
    suspend fun getAllPaymentsSuspend(): List<BcPayment>

    @Query("SELECT * FROM bc_history")
    suspend fun getAllHistorySuspend(): List<BcHistory>


    // --- LOANS ---
    @Query("SELECT * FROM loans ORDER BY date DESC")
    fun getAllLoans(): Flow<List<Loan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoan(loan: Loan)

    @Query("UPDATE loans SET is_settled = :isSettled WHERE id = :id")
    suspend fun setLoanSettledStatus(id: String, isSettled: Int)

    @Query("DELETE FROM loans WHERE id = :id")
    suspend fun deleteLoanById(id: String)


    // --- SAVINGS GOALS ---
    @Query("SELECT * FROM savings_goals")
    fun getAllSavingsGoals(): Flow<List<SavingsGoal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingsGoal(goal: SavingsGoal)

    @Query("UPDATE savings_goals SET current_amount = :amount WHERE id = :id")
    suspend fun updateSavingsGoalAmount(id: String, amount: Double)

    @Query("DELETE FROM savings_goals WHERE id = :id")
    suspend fun deleteSavingsGoalById(id: String)


    // --- NOTES ---
    @Query("SELECT * FROM notes ORDER BY is_pinned DESC, updated_at DESC")
    fun getAllNotes(): Flow<List<NoteEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntry)

    @Delete
    suspend fun deleteNote(note: NoteEntry)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: String)


    // --- TASKS ---
    @Query("SELECT * FROM tasks ORDER BY due_date ASC, created_at DESC")
    fun getAllTasks(): Flow<List<TaskEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntry)

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :id")
    suspend fun setTaskDoneStatus(id: String, isDone: Int)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTaskById(id: String)


    // --- TIMETABLE ---
    @Query("SELECT * FROM timetable ORDER BY day_of_week ASC, start_time ASC")
    fun getAllTimetableClasses(): Flow<List<TimetableEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableClass(classEntry: TimetableEntry)

    @Query("DELETE FROM timetable WHERE id = :id")
    suspend fun deleteTimetableClassById(id: String)


    // --- PASSWORDS ---
    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): Flow<List<PasswordEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordEntry)

    @Query("DELETE FROM passwords WHERE id = :id")
    suspend fun deletePasswordById(id: String)

    // --- TEMP PDF IMAGES ---
    @Query("SELECT * FROM temp_pdf_images ORDER BY added_at ASC")
    fun getAllTempPdfImages(): Flow<List<TempPdfImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTempPdfImage(image: TempPdfImage)

    @Query("DELETE FROM temp_pdf_images WHERE id = :id")
    suspend fun deleteTempPdfImageById(id: String)

    @Query("DELETE FROM temp_pdf_images")
    suspend fun clearAllTempPdfImages()

    // --- INTRUDER LOGS ---
    @Query("SELECT * FROM intruder_logs ORDER BY timestamp DESC")
    fun getAllIntruderLogs(): Flow<List<IntruderLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntruderLog(log: IntruderLog)

    @Query("DELETE FROM intruder_logs WHERE id = :id")
    suspend fun deleteIntruderLogById(id: String)

    @Query("DELETE FROM intruder_logs")
    suspend fun clearAllIntruderLogs()

    // --- PIN VAULT ---
    @Query("SELECT * FROM pin_vault ORDER BY created_at DESC")
    fun getAllPinVaultEntries(): Flow<List<PinVaultEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPinVaultEntry(entry: PinVaultEntry)

    @Query("DELETE FROM pin_vault WHERE id = :id")
    suspend fun deletePinVaultEntryById(id: String)


    // --- PHOTO VAULT ---
    @Query("SELECT * FROM photo_vault ORDER BY created_at DESC")
    fun getAllPhotoVaultEntries(): Flow<List<PhotoVaultEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoVaultEntry(entry: PhotoVaultEntry)

    @Query("DELETE FROM photo_vault WHERE id = :id")
    suspend fun deletePhotoVaultEntryById(id: String)


    // --- PRIVATE NOTES ---
    @Query("SELECT * FROM private_notes ORDER BY created_at DESC")
    fun getAllPrivateNoteEntries(): Flow<List<PrivateNoteEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrivateNoteEntry(entry: PrivateNoteEntry)

    @Query("DELETE FROM private_notes WHERE id = :id")
    suspend fun deletePrivateNoteEntryById(id: String)

    // --- WIFI DEVICES ---
    @Query("SELECT * FROM wifi_devices ORDER BY last_seen DESC")
    fun getAllWifiDevices(): Flow<List<WifiDevice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWifiDevice(device: WifiDevice)

    @Query("UPDATE wifi_devices SET customName = :customName, is_known = :isKnown WHERE macAddress = :macAddress")
    suspend fun renameWifiDevice(macAddress: String, customName: String?, isKnown: Int)

    @Query("SELECT * FROM wifi_devices WHERE macAddress = :macAddress")
    suspend fun getWifiDeviceByMac(macAddress: String): WifiDevice?

    @Query("DELETE FROM wifi_devices WHERE macAddress = :macAddress")
    suspend fun deleteWifiDeviceByMac(macAddress: String)

    // --- SPEED TEST HISTORY ---
    @Query("SELECT * FROM speed_test_history ORDER BY timestamp DESC")
    fun getAllSpeedTestHistory(): Flow<List<SpeedTestHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeedTestHistory(history: SpeedTestHistory)

    @Query("DELETE FROM speed_test_history")
    suspend fun clearSpeedTestHistory()

    // --- CACHED QURAN ---
    @Query("SELECT * FROM cached_quran_verses WHERE surah_number = :surahNumber ORDER BY verse_number ASC")
    fun getCachedVersesForSurah(surahNumber: Int): Flow<List<CachedQuranVerse>>

    @Query("SELECT * FROM cached_quran_verses WHERE page_number = :pageNumber ORDER BY surah_number ASC, verse_number ASC")
    fun getCachedVersesForPage(pageNumber: Int): Flow<List<CachedQuranVerse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuranVerses(verses: List<CachedQuranVerse>)

    @Query("SELECT COUNT(*) FROM cached_quran_verses")
    suspend fun getCachedQuranVersesCount(): Int

    @Query("DELETE FROM cached_quran_verses")
    suspend fun clearCachedQuran()

    // --- CACHED OFFLINE EXAM QUESTIONS ---
    @Query("SELECT * FROM cached_offline_questions WHERE category_type = :categoryType ORDER BY question_id ASC")
    fun getCachedQuestions(categoryType: String): Flow<List<CachedOfflineQuestion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedQuestions(questions: List<CachedOfflineQuestion>)

    @Query("SELECT COUNT(*) FROM cached_offline_questions WHERE category_type = :categoryType")
    suspend fun getCachedQuestionsCount(categoryType: String): Int

    @Query("DELETE FROM cached_offline_questions WHERE category_type = :categoryType")
    suspend fun clearCachedQuestions(categoryType: String)

    @Query("UPDATE cached_offline_questions SET user_saved_answer = :answer WHERE id = :id")
    suspend fun updateQuestionSavedAnswer(id: String, answer: Int)

    @Query("UPDATE cached_offline_questions SET is_bookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateQuestionBookmark(id: String, isBookmarked: Boolean)

    // --- POS SYSTEM ---
    @Query("SELECT * FROM pos_products ORDER BY name ASC")
    fun getAllPosProducts(): Flow<List<PosProduct>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosProduct(product: PosProduct)
    @Query("DELETE FROM pos_products WHERE id = :id")
    suspend fun deletePosProductById(id: String)

    @Query("SELECT * FROM pos_clients ORDER BY name ASC")
    fun getAllPosClients(): Flow<List<PosClient>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosClient(client: PosClient)
    @Query("DELETE FROM pos_clients WHERE id = :id")
    suspend fun deletePosClientById(id: String)

    @Query("SELECT * FROM pos_orders ORDER BY date DESC")
    fun getAllPosOrders(): Flow<List<PosOrder>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosOrder(order: PosOrder)

    @Query("SELECT * FROM pos_order_items WHERE orderId = :orderId")
    fun getPosOrderItems(orderId: String): Flow<List<PosOrderItem>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosOrderItem(item: PosOrderItem)
}

@Database(
    entities = [
        Expense::class,
        Income::class,
        Bill::class,
        BcCommittee::class,
        BcMember::class,
        BcPayment::class,
        BcHistory::class,
        Loan::class,
        SavingsGoal::class,
        NoteEntry::class,
        TaskEntry::class,
        TimetableEntry::class,
        PasswordEntry::class,
        TempPdfImage::class,
        IntruderLog::class,
        PinVaultEntry::class,
        PhotoVaultEntry::class,
        PrivateNoteEntry::class,
        WifiDevice::class,
        SpeedTestHistory::class,
        CachedQuranVerse::class,
        CachedOfflineQuestion::class,
        PosProduct::class,
        PosClient::class,
        PosOrder::class,
        PosOrderItem::class
    ],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): StudentKitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studentkit_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }

        fun resetInstance() {
            synchronized(this) {
                INSTANCE = null
            }
        }
    }
}
