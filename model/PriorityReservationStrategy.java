public class PriorityReservationStrategy implements ReservationStrategy {

    /**
     * Priority reservation — only for students where isPriority() == true.
     * Eligibility is already checked in Main.java before calling this,
     * but we double-check here as a safety net.
     *
     * Priority students also go to position 1 in the waiting list —
     * that logic is handled in LibrarySystem/NotificationService (Person 2).
     *
     * Returns true if seat was successfully reserved.
     * Returns false if seat is not available or student is not priority.
     */
    @Override
    public boolean reserveSeat(Student student, Seat seat) {

        // Safety check — should already be verified in Main.java
        if (!student.isPriority()) {
            System.out.println("[PriorityStrategy] Student " + student.getName()
                    + " does not have priority status.");
            return false;
        }

        if (!seat.isAvailable()) {
            System.out.println("[PriorityStrategy] Seat " + seat.getSeatId() + " is not available.");
            return false;
        }

        seat.reserve();
        System.out.println("[PriorityStrategy] Seat " + seat.getSeatId()
                + " reserved (priority) for " + student.getName());
        return true;
    }
}
