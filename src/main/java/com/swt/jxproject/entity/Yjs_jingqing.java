package com.swt.jxproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Yjs_jingqing implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 警情id
     */
    @TableId(value = "jq_id", type = IdType.AUTO)
    private Integer jq_id;

    /**
     * 报警用户id
     */
    private Integer user_id;

    /**
     * 民警id
     */
    private Integer epy_id;

    /**
     * 情指勤督编号
     */
    @TableField("JQBH")
    private String JQBH;

    /**
     * 事故状态
     */
    private String status;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 事故地址
     */
    private String address;

    /**
     * 是否有人受伤
     */
    private String bruise;

    /**
     * 事故车辆数
     */
    private Integer car_num;

    /**
     * 天气
     */
    private String weather;

    /**
     * 是否关闭案件
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
