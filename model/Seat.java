public class Seat {

    // ── Enums ────────────────────────────────────────────────────────────────

    public enum Status {
        AVAILABLE,
        RESERVED,
        OCCUPIED
    }

    // ── Fields ───────────────────────────────────────────────────────────────

    private final int seatId;       // e.g. 101, 102, 103
    private Status status;

    // ── Constructor ──────────────────────────────────────────────────────────

    public Seat(int seatId) {
        this.seatId = seatId;
        this.status = Status.AVAILABLE;
    }

    // ── Business Logic ───────────────────────────────────────────────────────

    /**
     * Marks the seat as RESERVED.
     */
    public void reserve() {
        this.status = Status.RESERVED;
    }

    /**
     * Releases the seat back to AVAILABLE.
     * Called on cancellation or expiry.
     */
    public void release() {
        this.status = Status.AVAILABLE;
    }

    /**
     * Returns true only if status is AVAILABLE.
     */
    public boolean isAvailable() {
        return this.status == Status.AVAILABLE;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public int getSeatId()      { return seatId; }
    public Status getStatus()   { return status; }

    // ── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Seat{id=" + seatId + ", status=" + status + "}";
    }
}
