package cn.nbmly.springbook.po;

public class ApplyBook {
    private Book book;
    private User user;
    private String applyDate;
    private int applyCount;
    private int applyStatus;
    private String applyStatusText;
    private int approver;
    private String approveDate;

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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public int getApprover() {
        return approver;
    }

    public void setApprover(int approver) {
        this.approver = approver;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getApplyStatusText() {
        return applyStatusText;
    }

    public void setApplyStatusText(String applyStatusText) {
        this.applyStatusText = applyStatusText;
    }

    @Override
    public String toString() {
        return "ApplyBook{" +
                "book=" + book +
                ", user=" + user +
                ", applyDate='" + applyDate + '\'' +
                ", applyCount=" + applyCount +
                ", applyStatus=" + applyStatus +
                ", applyStatusText='" + applyStatusText + '\'' +
                ", approver=" + approver +
                ", approveDate='" + approveDate + '\'' +
                '}';
    }
}
