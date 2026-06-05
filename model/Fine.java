import java.time.LocalDate;

/**
 * Represents a fine for returning a book late.
 * Created automatically when BorrowRecord.returnBook() detects an overdue return.
 *
 * Rate: $1.00 per overdue day.
 */
public class Fine {

    public enum FineStatus {
        UNPAID,
        PAID
    }

    private static final double DAILY_RATE = 1.00;

    private final String fineId;
    private final Student student;
    private final String borrowRecordId;
    private final double amount;
    private final long overdueDays;
    private final LocalDate issuedDate;
    private FineStatus status;
    private LocalDate paidDate;

    public Fine(String fineId, BorrowRecord record) {
        this.fineId = fineId;
        this.student = record.getStudent();
        this.borrowRecordId = record.getRecordId();
        this.overdueDays = record.getOverdueDays();
        this.amount = this.overdueDays * DAILY_RATE;
        this.issuedDate = LocalDate.now();
        this.status = FineStatus.UNPAID;
        this.paidDate = null;

        // Notify student immediately when fine is issued
        student.receiveNotification("A fine of $" + String.format("%.2f", amount)
                + " has been issued for returning \""
                + record.getBook().getTitle() + "\" "
                + overdueDays + " day(s) late.");
    }

    /**
     * Marks the fine as paid and notifies the student.
     */
    public boolean pay() {
        if (this.status == FineStatus.PAID) {
            System.out.println("[Fine] Fine " + fineId + " is already paid.");
            return false;
        }
        this.status = FineStatus.PAID;
        this.paidDate = LocalDate.now();
        student.receiveNotification("Your fine of $" + String.format("%.2f", amount)
                + " (Fine ID: " + fineId + ") has been paid successfully.");
        return true;
    }

    public boolean isPaid()             { return this.status == FineStatus.PAID; }
    public String getFineId()           { return fineId; }
    public Student getStudent()         { return student; }
    public String getBorrowRecordId()   { return borrowRecordId; }
    public double getAmount()           { return amount; }
    public long getOverdueDays()        { return overdueDays; }
    public LocalDate getIssuedDate()    { return issuedDate; }
    public FineStatus getStatus()       { return status; }
    public LocalDate getPaidDate()      { return paidDate; }

    @Override
    public String toString() {
        return "Fine{id='" + fineId + "', amount=$"
                + String.format("%.2f", amount)
                + ", days=" + overdueDays
                + ", status=" + status + "}";
    }
}
