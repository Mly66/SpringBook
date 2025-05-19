package cn.nbmly.springbook.po;

public class Book {
    private int bookId;
    private String bookName;
    private String isbn;
    private float price;
    private String pubDate;
    private String warehousingDate;
    private String author;
    private int inventory;
    private Publisher publisher;
    private Course course;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getWarehousingDate() {
        return warehousingDate;
    }

    public void setWarehousingDate(String warehousingDate) {
        this.warehousingDate = warehousingDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", pubDate='" + pubDate + '\'' +
                ", warehousingDate='" + warehousingDate + '\'' +
                ", author='" + author + '\'' +
                ", inventory=" + inventory +
                ", publisher=" + publisher +
                ", course=" + course +
                '}';
    }
}
