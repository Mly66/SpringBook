package cn.nbmly.springbook.po;

public class DistributeBook {
    private Book book;
    private User user;
    private String distributeDate;
    private int distributeCount;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDistributeDate() {
        return distributeDate;
    }

    public void setDistributeDate(String distributeDate) {
        this.distributeDate = distributeDate;
    }

    public int getDistributeCount() {
        return distributeCount;
    }

    public void setDistributeCount(int distributeCount) {
        this.distributeCount = distributeCount;
    }

    @Override
    public String toString() {
        return "DistributeBook{" +
                "book=" + book +
                ", user=" + user +
                ", distributeDate='" + distributeDate + '\'' +
                ", distributeCount=" + distributeCount +
                '}';
    }
}
