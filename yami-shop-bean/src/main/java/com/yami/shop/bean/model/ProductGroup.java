/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yami.shop.common.serializer.json.ImgJsonSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("tz_product_group")
public class ProductGroup implements Serializable {

    private static final long serialVersionUID = -4644407386444894349L;
    /**
     * 商品ID
     */
    @TableId
    private Long id;

    /**
     * 订单id
     */
    private Long orderid;

    /**
     * 子订单id
     */
    private String suborderid;

    /**
     * 是否开团订单
     */
    private String isMainOrder;


    /**
     * 状态
     */
    private String status;





}
