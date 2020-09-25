package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.query.TeacherQuery;
import com.guli.teacher.mapper.EduTeacherMapper;
import com.guli.teacher.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author guli
 * @since 2019-08-14
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> teacherPage, TeacherQuery query) {

        if(query == null){
            baseMapper.selectPage(teacherPage,null);
        }

        //获取对象属性
        String name = query.getName();
        Integer level = query.getLevel();
        Date gmtCreate = query.getGmtCreate();
        Date gmtModified = query.getGmtModified();

        //创建一个Wrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //判断对象属性是否存在
        if(!StringUtils.isEmpty(name)){
            //如果存在再加入条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        //判断创建时间是否存在，如果存在，查询的是大于等于此时间的
        if (!StringUtils.isEmpty(gmtCreate)) {
            wrapper.ge("gmt_create", gmtCreate);
        }

        if (!StringUtils.isEmpty(gmtModified)) {
            wrapper.le("gmt_create", gmtModified);
        }
        baseMapper.selectPage(teacherPage,wrapper);
    }


    //根据多个ID删分类

    /*Tree
     A
            B  刪除
                    b1
                    b2
                            bb1
                            bb2
            C*/
    /*public void deletes(String parent_id){
        List<String> ids = new ArrayList<>();
        ids.add(parent_id);
        //递归获取ID
        getIds(ids,parent_id);
        baseMapper.deleteBatchIds(ids);
    }*/

    //方法递归方法
    /*public void getIds(List<String> ids, String parent_id){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parent_id);
        List<EduTeacher> teacherList = baseMapper.selectList(wrapper);
        for(EduTeacher teacher : teacherList){
            ids.add(teacher.getId());
            getIds(ids,teacher.getId());
        }
    }*/

}
