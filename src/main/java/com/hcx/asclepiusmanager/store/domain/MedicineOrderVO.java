package com.hcx.asclepiusmanager.store.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcx.asclepiusmanager.medicine.domain.MedicineVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author huangcaixia
 * @Description 订单列表VO
 * @date 2022/4/7 15:46
 */
@Data
public class MedicineOrderVO {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 用户id
     */
    private Integer userId;

    private String location;//地址信息-省市区

    private String detailAddress;//地址信息-详细地址

    private String recipientPhone;//收件人电话

    private String recipientName;//收件人姓名

    /**
     * 总价
     */
    private Integer totalPrice;

    /**
     * 具体状态
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deliveryAt;

    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiptAt;

    /**
     * 药品信息列表
     */
    private List<MedicineOrderGoodsVO> medicineOrderGoodsVOS;

    public String getGoodsInfo(){
        StringBuffer goodsInfo=new StringBuffer();
        if(medicineOrderGoodsVOS!=null||medicineOrderGoodsVOS.size()!=0){
            for(MedicineOrderGoodsVO medicineOrderGoodsVO:medicineOrderGoodsVOS){
                goodsInfo.append("["+medicineOrderGoodsVO.getBrandName()+"]"+medicineOrderGoodsVO.getMedicineName()+" *"+medicineOrderGoodsVO.getMedicineNumber()+"\n");
            }
        }
        return goodsInfo.toString();
    }

}
