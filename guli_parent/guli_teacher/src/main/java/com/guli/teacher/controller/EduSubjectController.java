package com.guli.teacher.controller;


import com.guli.common.result.Result;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Tang
 * @since 2020-10-12
 */
@RestController
@CrossOrigin
@RequestMapping("/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 导入课程
     * @param file
     * @return
     */
    @PostMapping("import")
    public Result importSubject(MultipartFile file){
        // 因为考虑到EXCL模板中数据不准确所以返回多个错误信息，那么多个错误信息放在集合中
        List<String> mesList = eduSubjectService.importEXCL(file);
        if (mesList.size() == 0){
            return Result.ok();
        } else {
            return Result.error().data("messageList",mesList);
        }
    }

    /**
     * 获取所有的课程分类树
     * @return
     */
    @GetMapping("list")
    public Result getSubjectList(){
        List<OneSubject> list = eduSubjectService.getTree();
        return Result.ok().data("subjectList",list);
    }

}

