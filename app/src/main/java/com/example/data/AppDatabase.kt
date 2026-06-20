package com.example.data

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
}

@Database(
    entities = [
        Expense::class,
        Income::class,
        Bill::class,
        BcCommittee::class,
        BcMember::class,
        BcPayment::class,
        Loan::class,
        SavingsGoal::class,
        NoteEntry::class,
        TaskEntry::class,
        TimetableEntry::class,
        PasswordEntry::class
    ],
    version = 1,
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
    }
}
