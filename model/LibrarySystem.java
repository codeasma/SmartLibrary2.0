import java.util.ArrayList;
import java.util.List;

public class LibrarySystem {
    private static LibrarySystem instance;

    private List<Seat> seats;
    private List<Reservation> reservations;
    private List<Book> books;
    private List<User> users;

    private NotificationService notificationService;

    private LibrarySystem() {
        seats = new ArrayList<>();
        reservations = new ArrayList<>();
        books = new ArrayList<>();
        users = new ArrayList<>();
        notificationService = new NotificationService();
    }
    public static LibrarySystem getInstance() {
        if (instance == null) {
            instance = new LibrarySystem();
        }
        return instance;
    }

    public Student login(String email, String password) {
        for(User user: users){
            if(user instanceof Student){
                Student student = (Student) user;
                if(student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    return student;
                }
            }
        }
        return null;
    }

    public void addSeat(Seat seat){
        seats.add(seat);
    }
    public void addBook(Book book){
        books.add(book);
    }
    public void addUser(User user){
        users.add(user);
    }

    public void reserveSeat(Student student, Seat seat, ReservationStrategy strategy) {
        boolean success=strategy.reserveSeat(student,seat);

        if(success){
            notificationService.sendNotification(student, "Seat " + seat.getSeatId()+ " has been reserved successfully.");
        }else {
            notificationService.attach(student);
            notificationService.sendNotification(student, "Seat is not available. You are added to the waiting list.");
        }

    }

    public void showAvailableSeats(){
        boolean found = false;

        System.out.println("Available Seats:");

        for(Seat seat : seats){
            if(seat.isAvailable()){
                System.out.println("Seat " + seat.getSeatId());
                found = true;
            }
        }
        if(!found){
            System.out.println("There are no seats available.");
        }
    }
    public boolean hasAvailableSeats(){
        for(Seat seat : seats){
            if(seat.isAvailable()){
                return true;
            }
        }
        return false;
    }

    public Seat findSeatById(int seatId){
        for(Seat seat : seats){
            if(seat.getSeatId() == seatId){
                return seat;
            }
        }
        return null;
    }

    public void joinWaitingList(Student student){
        notificationService.attach(student);

        notificationService.sendNotification(student, "You have joined the waiting list.");
    }

    public void cancelReservation(Student student, Seat seat) {
        seat.release();
        notificationService.sendNotification(student, "Your reservation has been cancelled.");
        notificationService.notifyObservers("A seat is now available.");
        notificationService.detach(student);
    }

    public Book searchBook(String title) {
        for (Book book : books){
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void borrowBook(Student student, Book book) {
        if(book.isAvailable()){
            book.borrow();
            notificationService.attach(student);
            notificationService.borrowConfirmationNotification(student);
        }else
            notificationService.sendNotification(student, "Book is not available.");
    }

    public void returnBook(Student student, Book book) {
        book.returnBook();
        notificationService.detach(student);
        notificationService.sendNotification(student, "Book got returned successfully.");
    }

}