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
@TableName("yjs_user")
public class Yjs_user implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer user_id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户名称
     */
    private String user_name;

    /**
     * 登录密码
     */
    private String user_pwd;

    /**
     * 用户来源
     */
    private String source;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 创建时间
     */
    private String create_time;


}
