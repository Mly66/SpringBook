package cn.nbmly.springbook.mapper;

import cn.nbmly.springbook.po.DistributeBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DistributeBookMapper {
    int addDistributeBook(DistributeBook distributeBook);

    int updateDistributeBook(DistributeBook distributeBook);

    DistributeBook findDistributeBookByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") int userId);

    List<DistributeBook> findDistributeBooks();
}
