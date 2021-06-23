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
public class Yjs_vehicle_licence implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编号id
     */
    @TableId(value = "vl_id", type = IdType.AUTO)
    private Integer vl_id;

    /**
     * 当事人id
     */
    private Integer pts_id;

    /**
     * 照片id
     */
    private Integer img_id;

    /**
     * 车牌号码
     */
    private String plate_no;

    /**
     * 车辆类型
     */
    private String vehicle_type;

    /**
     * 所有人
     */
    private String owner;

    /**
     * 住址
     */
    private String address;

    /**
     * 使用性质
     */
    private String use_character;

    /**
     * 品牌型号
     */
    private String model;

    /**
     * 车辆识别代号
     */
    private String vin;

    /**
     * 发动机号码
     */
    private String engine_no;

    /**
     * 注册日期
     */
    private String register_date;

    /**
     * 发证日期
     */
    private String issue_date;

    /**
     * 印章
     */
    private String seal;

    /**
     * 档案编号
     */
    private String file_no;

    /**
     * 核定人数
     */
    private String allow_num;

    /**
     * 总质量
     */
    private String total_mass;

    /**
     * 整备质量
     */
    private String curb_weight;

    /**
     * 核定载质量
     */
    private String load_quality;

    /**
     * 外廓尺寸
     */
    private String external_size;

    /**
     * 备注
     */
    private String marks;

    /**
     * 检验记录
     */
    private String record;

    /**
     * 准牵引总质量
     */
    private String total_mass_norm;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 创建时间
     */
    private String create_time;


}
