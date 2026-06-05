import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Tracks a single book borrowing transaction.
 * Loan period: 14 days.
 * Reminder: sent when 3 or fewer days remain before due date.
 */
public class BorrowRecord {

    public enum BorrowStatus {
        ACTIVE,
        RETURNED,
        OVERDUE
    }

    private static final int BORROW_DAYS = 14;
    private static final int REMINDER_DAYS_BEFORE = 3;

    private final String recordId;
    private final Student student;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus status;

    public BorrowRecord(String recordId, Student student, Book book) {
        this.recordId = recordId;
        this.student = student;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(BORROW_DAYS);
        this.returnDate = null;
        this.status = BorrowStatus.ACTIVE;

        // Notify student of borrow confirmation with due date
        student.receiveNotification("You have borrowed \"" + book.getTitle()
                + "\". Please return it by " + this.dueDate + ".");
    }

    /**
     * Call this when the student returns the book.
     * Updates status and sends appropriate notification.
     */
    public void returnBook() {
        this.returnDate = LocalDate.now();
        if (this.returnDate.isAfter(this.dueDate)) {
            this.status = BorrowStatus.OVERDUE;
            student.receiveNotification("You returned \"" + book.getTitle()
                    + "\" late by " + getOverdueDays() + " day(s). A fine has been issued.");
        } else {
            this.status = BorrowStatus.RETURNED;
            student.receiveNotification("You have successfully returned \""
                    + book.getTitle() + "\". Thank you!");
        }
    }

    /**
     * Call this daily to check if a reminder should be sent.
     * LibrarySystem should loop through all active records and call this.
     */
    public void checkAndSendReminder() {
        if (this.status != BorrowStatus.ACTIVE) return;
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), this.dueDate);
        if (daysLeft >= 0 && daysLeft <= REMINDER_DAYS_BEFORE) {
            student.receiveNotification("Reminder: \"" + book.getTitle()
                    + "\" is due in " + daysLeft + " day(s) (" + dueDate + "). Please return it on time.");
        }
    }

    public boolean isOverdue() {
        if (this.status == BorrowStatus.RETURNED) return false;
        return LocalDate.now().isAfter(this.dueDate);
    }

    public long getOverdueDays() {
        if (!isOverdue()) return 0;
        LocalDate ref = (returnDate != null) ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(this.dueDate, ref);
    }

    public String getRecordId()     { return recordId; }
    public Student getStudent()     { return student; }
    public Book getBook()           { return book; }
    public LocalDate getBorrowDate(){ return borrowDate; }
    public LocalDate getDueDate()   { return dueDate; }
    public LocalDate getReturnDate(){ return returnDate; }
    public BorrowStatus getStatus() { return status; }
}
