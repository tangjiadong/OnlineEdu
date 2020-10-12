package com.guli.teacher.entity.vo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Tang on 2020/10/12
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    List<TwoSubject> children = new ArrayList<>();

}
