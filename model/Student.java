import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Observer {
    private String studentNo;
    private boolean isPriority;
    private List<String> notifications;

    public Student(int userId, String name, String email, String password, String studentNo, boolean isPriority) {
        super(userId, name, email, password);
        this.studentNo = studentNo;
        this.isPriority = isPriority;
        this.notifications = new ArrayList<>();
    }

    public void reserveSeat(int seatId) {
        System.out.println(getName() + " is requesting reservation for Seat #" + seatId);
    }

    public void cancelReservation(int reservationId) {
        System.out.println(getName() + " cancelled reservation #" + reservationId);
    }

    public void borrowBook(int bookId) {
        System.out.println(getName() + " is requesting to borrow Book #" + bookId);
    }

    public void returnBook(int bookId) {
        System.out.println(getName() + " is returning Book #" + bookId);
    }

    public void receiveNotification(String message) {
        notifications.add(message);
        System.out.println("[NOTIFICATION] " + getName() + ": " + message);
    }

    public String getStudentNo() { return studentNo; }
    public boolean isPriority() { return isPriority; }
    public List<String> getNotifications() { return notifications; }

    @Override
    public String toString() {
        return "Student[no=" + studentNo + ", name=" + getName() + ", email=" + getEmail() + ", priority=" + isPriority + "]";
    }

    @Override
    public void update(String message) {
        notifications.add(message);
        System.out.println("[NOTIFICATION] " + getName() + ": " + message);
    }
}
