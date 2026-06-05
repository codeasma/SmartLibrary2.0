public interface ReservationStrategy {

    /**
     * Attempts to reserve a seat for a student.
     * Called by LibrarySystem.reserveSeat()
     *
     * @param student The student requesting the reservation
     * @param seat    The seat to reserve
     * @return true if reservation was successful, false otherwise
     */
    boolean reserveSeat(Student student, Seat seat);
}
