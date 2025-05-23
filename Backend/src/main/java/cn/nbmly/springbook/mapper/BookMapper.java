package cn.nbmly.springbook.mapper;

import cn.nbmly.springbook.po.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    Book findBookByBookId(int bookId);

    List<Book> findBooks();

    List<Book> getBookList(@Param("page") int page, @Param("size") int size);

    int editBook(Book book);

    int addBook(Book book);

    int deleteBook(int bookId);

    /*
     * List<Book> findApplysByUserId(int userId);
     * List<Book> findApplys();
     */
    int findBookCountByCourseId(int courseId);

    int findBookCountByPubId(int pubId);
}
