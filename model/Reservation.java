import java.time.LocalDateTime;

/**
 * Represents a seat reservation made by a student.
 * Expiry is set to 15 minutes — student must show up within that time.
 */
public class Reservation {

    public enum ReservationStatus {
        ACTIVE,
        CANCELLED,
        EXPIRED,
        COMPLETED
    }

    private static final int EXPIRY_MINUTES = 15;

    private final String reservationId;
    private final Student student;
    private final Seat seat;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;
    private final String strategyUsed;
    private ReservationStatus status;

    public Reservation(String reservationId, Student student, Seat seat, String strategyUsed) {
        this.reservationId = reservationId;
        this.student = student;
        this.seat = seat;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusMinutes(EXPIRY_MINUTES);
        this.strategyUsed = strategyUsed;
        this.status = ReservationStatus.ACTIVE;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        this.seat.release();
        System.out.println("[Reservation] Reservation " + reservationId + " cancelled.");
    }

    public void expire() {
        this.status = ReservationStatus.EXPIRED;
        this.seat.release();
        student.receiveNotification("Your reservation for Seat "
                + seat.getSeatId() + " has expired (15 min timeout). Seat is now free.");
    }

    public void complete() {
        this.status = ReservationStatus.COMPLETED;
    }

    public boolean isActive()               { return this.status == ReservationStatus.ACTIVE; }
    public String getReservationId()        { return reservationId; }
    public Student getStudent()             { return student; }
    public Seat getSeat()                   { return seat; }
    public LocalDateTime getCreatedAt()     { return createdAt; }
    public LocalDateTime getExpiresAt()     { return expiresAt; }
    public String getStrategyUsed()         { return strategyUsed; }
    public ReservationStatus getStatus()    { return status; }
}
