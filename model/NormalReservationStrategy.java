public class NormalReservationStrategy implements ReservationStrategy {

    /**
     * Normal reservation — open to all students.
     * Duration: 60 minutes (15 min to show up before expiry)
     *
     * Returns true if seat was successfully reserved.
     * Returns false if seat is not available.
     */
    @Override
    public boolean reserveSeat(Student student, Seat seat) {

        if (!seat.isAvailable()) {
            System.out.println("[NormalStrategy] Seat " + seat.getSeatId() + " is not available.");
            return false;
        }

        seat.reserve();
        System.out.println("[NormalStrategy] Seat " + seat.getSeatId()
                + " reserved for " + student.getName());
        return true;
    }
}
