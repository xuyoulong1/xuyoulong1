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
public class Yjs_parties implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 当事人id
     */
    @TableId(value = "pts_id", type = IdType.AUTO)
    private Integer pts_id;

    /**
     * 警情id
     */
    private Integer jq_id;

    /**
     * 当事人名称
     */
    private String pts_name;

    /**
     * 当事人联系电话
     */
    private String pts_phone;

    /**
     * 现场照片是否上传
     */
    private String xc_image;

    /**
     * 证件照片是否上传
     */
    private String zj_image;

    /**
     * 交通方式
     */
    private String traffic;

    /**
     * 驾驶证号
     */
    private String card_code;

    /**
     * 车牌号码
     */
    private String plate_no;

    /**
     * 车辆类型
     */
    private String vehicle_type;

    /**
     * 保险公司名称
     */
    private String policy_name;

    /**
     * 保险单号
     */
    private String policy_no;

    /**
     * 事故责任
     */
    private String accid_duty;

    /**
     * 签字照片
     */
    private String sign_image;

    /**
     * 签字时间
     */
    private String sign_date;

    /**
     * 更新时间
     */
    private String update_time;

    /**
     * 创建时间
     */
    private String create_time;


}
