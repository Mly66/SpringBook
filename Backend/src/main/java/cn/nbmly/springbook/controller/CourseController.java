package cn.nbmly.springbook.controller;

import cn.nbmly.springbook.mapper.BookMapper;
import cn.nbmly.springbook.mapper.CourseMapper;
import cn.nbmly.springbook.po.Course;
import cn.nbmly.springbook.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private BookMapper bookMapper;

    /**
     * 保存课程信息（新增或更新）
     */
    @PostMapping("/save")
    public Result<String> saveCourse(@RequestBody Course course) {
        int result;
        if (course.getCourseId() > 0) {
            result = courseMapper.editCourse(course);
        } else {
            result = courseMapper.addCourse(course);
        }
        return result > 0 ? Result.success("保存成功") : Result.failed("保存失败");
    }

    /**
     * 获取课程详情
     */
    @GetMapping("/{courseId}")
    public Result<Course> getCourse(@PathVariable(required = false) String courseId) {
        // 检查courseId是否为undefined或无效值
        if (courseId == null || "undefined".equals(courseId)) {
            return Result.failed("无效的课程ID");
        }

        int courseIdInt;
        try {
            courseIdInt = Integer.parseInt(courseId);
        } catch (NumberFormatException e) {
            return Result.failed("课程ID必须是数字");
        }

        Course course = courseMapper.findCourseByCourseId(courseIdInt);
        if (course != null) {
            return Result.success(course);
        } else {
            return Result.failed("未找到该课程");
        }
    }

    /**
     * 删除课程
     */
    @DeleteMapping("/{courseId}")
    public Result<String> deleteCourse(@PathVariable(required = false) String courseId) {
        // 检查courseId是否为undefined或无效值
        if (courseId == null || "undefined".equals(courseId)) {
            return Result.failed("无效的课程ID");
        }

        int courseIdInt;
        try {
            courseIdInt = Integer.parseInt(courseId);
        } catch (NumberFormatException e) {
            return Result.failed("课程ID必须是数字");
        }

        int count = bookMapper.findBookCountByCourseId(courseIdInt);
        if (count > 0) {
            return Result.failed("该课程有关联的图书，不能删除");
        }
        int result = courseMapper.deleteCourse(courseIdInt);
        return result > 0 ? Result.success("删除成功") : Result.failed("删除失败");
    }
}
