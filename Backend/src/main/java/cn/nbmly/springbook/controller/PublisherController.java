package cn.nbmly.springbook.controller;

import cn.nbmly.springbook.mapper.BookMapper;
import cn.nbmly.springbook.mapper.PublisherMapper;
import cn.nbmly.springbook.po.Publisher;
import cn.nbmly.springbook.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    @Autowired
    private PublisherMapper publisherMapper;
    @Autowired
    private BookMapper bookMapper;

    /**
     * 保存出版社信息
     */
    @PostMapping("/save")
    public Result<String> savePublisher(@RequestBody Publisher publisher) {
        int result;
        if (publisher.getPubId() > 0) {
            result = publisherMapper.editPublisher(publisher);
        } else {
            result = publisherMapper.addPublisher(publisher);
        }
        return result > 0 ? Result.success("保存成功") : Result.failed("保存失败");
    }

    /**
     * 获取出版社详情
     */
    @GetMapping("/{pubId}")
    public Result<Publisher> getPublisher(@PathVariable(required = false) String pubId) {
        // 检查pubId是否为undefined或无效值
        if (pubId == null || "undefined".equals(pubId)) {
            return Result.failed("无效的出版社ID");
        }

        int pubIdInt;
        try {
            pubIdInt = Integer.parseInt(pubId);
        } catch (NumberFormatException e) {
            return Result.failed("出版社ID必须是数字");
        }

        Publisher publisher = publisherMapper.findPublisherByPubId(pubIdInt);
        if (publisher != null) {
            return Result.success(publisher);
        } else {
            return Result.failed("未找到该出版社");
        }
    }

    /**
     * 删除出版社
     */
    @DeleteMapping("/{pubId}")
    public Result<String> deletePublisher(@PathVariable(required = false) String pubId) {
        // 检查pubId是否为undefined或无效值
        if (pubId == null || "undefined".equals(pubId)) {
            return Result.failed("无效的出版社ID");
        }

        int pubIdInt;
        try {
            pubIdInt = Integer.parseInt(pubId);
        } catch (NumberFormatException e) {
            return Result.failed("出版社ID必须是数字");
        }

        int count = bookMapper.findBookCountByPubId(pubIdInt);
        if (count > 0) {
            return Result.failed("该出版社有关联的图书，不能删除");
        }
        int result = publisherMapper.deletePublisher(pubIdInt);
        return result > 0 ? Result.success("删除成功") : Result.failed("删除失败");
    }
}
