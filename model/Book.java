public class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean available;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public void borrow() {
        if (available) {
            available = false;
            System.out.println("Book is borrowed: " + title);
        }else
            System.out.println("Book is not available: " + title);
    }
    public void returnBook() {
        available = true;
        System.out.println("Book is returned: " + title);
    }
    public boolean isAvailable() {
        return available;
    }
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

}
