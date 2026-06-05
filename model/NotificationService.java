import java.util.ArrayList;
import java.util.List;

public class NotificationService implements Subject {

    private List<Observer> observers;

    public NotificationService() {
        observers = new ArrayList<>();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void sendNotification(Student student, String message) {
        student.update(message);
    }

    public void borrowConfirmationNotification(Student student) {
        student.update("Book borrowed successfully.");
    }

    public void reservationConfirmationNotification(Student student) {
        student.update("Seat reserved successfully.");
    }

    public void reservationExpiredNotification(Student student) {
        student.update("Your reservation has expired.");
    }
}
