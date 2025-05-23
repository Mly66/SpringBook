package cn.nbmly.springbook.mapper;

import cn.nbmly.springbook.po.ApplyBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplyBookMapper {
    int findApplyCountByBookId(int bookId);

    ApplyBook findApplyBookByApplyBookId(int applyBookId);

    List<ApplyBook> findApplyBooksByUserId(int userId);

    List<ApplyBook> findNoApprovedApplyBooks(); // 查询未审批的申请

    List<ApplyBook> findApprovedApplyBooks(); // 查询已通过审批的申请

    List<ApplyBook> findApplyBooksInStorage();

    int editApplyBookStatus(ApplyBook applyBook);

    int editApplyBook(ApplyBook applyBook);

    int editApplyCount(ApplyBook applyBook);

    int addApplyBook(ApplyBook applyBook);

    int approveApply(ApplyBook applyBook);

    // 新增：根据bookId和userId查找申请记录
    ApplyBook findApplyBookByBookIdAndUserId(@Param("bookId") int bookId, @Param("userId") int userId);

    // 新增：更新申请数量
    int updateApplyCount(ApplyBook applyBook);

    // 新增：根据图书ID查询未审批的申请记录
    List<ApplyBook> findNoApprovedApplyBooksByBookId(int bookId);
}
