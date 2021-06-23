package com.swt.jxproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("yjs_image")
public class Yjs_image implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 照片id
     */
    @TableId(type = IdType.AUTO)
    private  Integer img_id;

    /**
     * 当事人id
     */
    private Integer pts_id;

    /**
     * 照片类型
     */
    private String img_type;

    /**
     * 照片名称
     */
    private String img_name;

    /**
     * 1有效、0删除
     */
    private String flag;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 创建时间
     */
    private String create_time;


}
