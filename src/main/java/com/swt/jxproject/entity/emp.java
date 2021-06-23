package com.swt.jxproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


@TableName("emp")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class emp {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private int age;

}
