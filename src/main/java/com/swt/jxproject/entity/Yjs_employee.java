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
public class Yjs_employee implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 员工id
     */
    @TableId(value = "epy_id", type = IdType.AUTO)
    private Integer epy_id;

    /**
     * 工号
     */
    private Integer epy_num;

    /**
     * 名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 角色（1交警、2保险公司）
     */
    private String role;

    /**
     * 1有效 0删除
     */
    private String flag;

    /**
     * 登录时间
     */
    private Date login_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 创建时间
     */
    private Date create_time;


}
