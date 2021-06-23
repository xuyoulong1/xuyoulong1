package com.swt.jxproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
public class Yjs_ident_card implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编号id
     */
    @TableId(value = "ic_id", type = IdType.AUTO)
    private Integer ic_id;

    /**
     * 当事人id
     */
    private Integer pts_id;

    /**
     * 照片id
     */
    private Integer img_id;

    /**
     * 身份证号码
     */
    private String card_num;

    /**
     * 身份证名称
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 出生日期
     */
    private String birth;

    /**
     * 地址
     */
    private String address;

    /**
     * 发证机关
     */
    private String authority;

    /**
     * 有效期
     */
    private String valid_date;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 创建时间
     */
    private String create_time;


}
