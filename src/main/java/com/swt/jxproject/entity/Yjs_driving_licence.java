package com.swt.jxproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xyl
 * @since 2021-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Yjs_driving_licence implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编号id
     */
    @TableId(value = "dl_id", type = IdType.AUTO)
    private Integer dl_id;

    /**
     * 当事人id
     */
    private Integer pts_id;

    /**
     * 照片id
     */
    private Integer img_id;

    /**
     * 驾驶证号
     */
    private String card_code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 准驾车型
     */
    @TableField("class")
    private String classs;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 住址
     */
    private String address;

    /**
     * 出生日期
     */
    private String birth;

    /**
     * 初次领证日期
     */
    private String first_date;

    /**
     * 有效期开始时间
     */
    private String start_date;

    /**
     * 有效期截止时间
     */
    private String end_date;

    /**
     * 档案编号
     */
    private String archives_code;

    /**
     * 发证单位
     */
    private String authority;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 创建时间
     */
    private String create_time;


}
