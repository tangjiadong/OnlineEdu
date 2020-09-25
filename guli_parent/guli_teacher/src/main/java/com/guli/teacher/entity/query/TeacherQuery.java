package com.guli.teacher.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 条件查询的对象
 */
@Data
public class TeacherQuery {

    private String name;

    private Integer level;
    @ApiModelProperty(value = "创建时间", example = "2019-01-01 8:00:00")
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间", example = "2019-01-01 8:00:00")
    private Date gmtModified;

}
