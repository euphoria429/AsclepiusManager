package com.hcx.asclepiusmanager.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcx.asclepiusmanager.common.enums.OperationEnum;
import com.hcx.asclepiusmanager.common.enums.OrderEnum;
import com.hcx.asclepiusmanager.medicine.domain.Medicine;
import com.hcx.asclepiusmanager.medicine.domain.MedicineOperated;
import com.hcx.asclepiusmanager.medicine.service.BrandService;
import com.hcx.asclepiusmanager.medicine.service.MedicineImgService;
import com.hcx.asclepiusmanager.medicine.service.MedicineOperatedService;
import com.hcx.asclepiusmanager.medicine.service.MedicineService;
import com.hcx.asclepiusmanager.store.domain.*;
import com.hcx.asclepiusmanager.store.mapper.MedicineOrderMapper;
import com.hcx.asclepiusmanager.store.service.AddressService;
import com.hcx.asclepiusmanager.store.service.MedicineOrderGoodsService;
import com.hcx.asclepiusmanager.store.service.MedicineOrderService;
import com.hcx.asclepiusmanager.store.service.ShopCarService;
import com.hcx.asclepiusmanager.sysmgr.wechat.service.SysWechatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangcaixia
 * @Description
 * @date 2022/4/6 21:16
 */
@Service
public class MedicineOrderServiceImpl implements MedicineOrderService {

    @Resource
    MedicineOrderMapper medicineOrderMapper;

    @Resource
    MedicineOrderGoodsService medicineOrderGoodsService;

    @Resource
    SysWechatService sysWechatService;

    @Resource
    ShopCarService shopCarService;

    @Resource
    MedicineService medicineService;

    @Resource
    MedicineOperatedService medicineOperatedService;

    @Resource
    AddressService addressService;

    @Resource
    BrandService brandService;

    @Resource
    MedicineImgService medicineImgService;

    /**
     * 提交订单方法：
     *      1-获取当前用户id
     *      2-插入订单表，状态为待发货,获取刚插入的订单id
     *      3-从请求参数获得购物车id列表，循环：
     *          3-1-购物车记录状态设置禁用
     *          3-2-根据购物车记录中的药品id和数量构建订单商品对象，插入订单商品表
     *          3-3-根据购物车记录中的药品id和数量，更新药品库存
     *          3-4-根据购物车记录中的药品id和数量，插入操作表，操作类型为售出
     * @param medicineOrderDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveOrder(MedicineOrderDTO medicineOrderDTO) {
        //1-获取当前用户id
        Integer userId = sysWechatService.getUserInfo().getId();
        if(userId==null){
            return 0;
        }
        //2-插入订单表，状态为待发货,获取刚插入的订单id
        MedicineOrder medicineOrder=new MedicineOrder();
        medicineOrder.setUserId(userId);
        medicineOrder.setAddressId(medicineOrderDTO.getAddressId());
        medicineOrder.setTotalPrice(medicineOrderDTO.getTotalPrice());
        medicineOrder.setStatus(OrderEnum.SOLD.getCode());
        medicineOrderMapper.insert(medicineOrder);
        Integer orderId=medicineOrder.getId();
        //3-从请求参数获得购物车id列表，循环：
        List<Integer> shopCarIds=medicineOrderDTO.getShopCarIds();
        for(Integer id:shopCarIds){
            //3-1-购物车记录状态设置禁用
            ShopCar shopCar=shopCarService.getShopCarById(id);
            shopCar.setStatus(0);
            if(shopCarService.updateShopCarStatus(shopCar)==0){
                return 0;
            }
            //3-2-根据购物车记录中的药品id和数量构建订单商品对象，插入订单商品表
            MedicineOrderGoods medicineOrderGoods=new MedicineOrderGoods();
            medicineOrderGoods.setMedicineOrderId(orderId);
            medicineOrderGoods.setMedicineId(shopCar.getMedicineId());
            medicineOrderGoods.setMedicineNumber(shopCar.getMedicineNumber());
            medicineOrderGoods.setMedicineUnitPrice(medicineService.findMedicineById(shopCar.getMedicineId()).getMedicineUnitPrice());
            if(medicineOrderGoodsService.insert(medicineOrderGoods)==0){
                return 0;
            }
            //3-3-根据购物车记录中的药品id和数量，更新药品库存
            Medicine medicine=medicineService.findMedicineById(shopCar.getMedicineId());
            if(medicine.getMedicineStore()<shopCar.getMedicineNumber()){
                return -1;
            }
            Integer store=medicine.getMedicineStore()-shopCar.getMedicineNumber();
            Integer number=store-medicine.getMedicineStore();
            medicine.setMedicineStore(store);
            if(medicineService.updateSoldStore(medicine)==0){
                return 0;
            }
            //3-4-根据购物车记录中的药品id和数量，插入操作表，操作类型为售出
            MedicineOperated medicineOperated=new MedicineOperated();
            medicineOperated.setMedicineId(shopCar.getMedicineId());
            medicineOperated.setOperationCode(OperationEnum.SOLD.getCode());
            medicineOperated.setNumber(number);
            medicineOperated.setAfterStore(store);
            medicineOperated.setOrderId(orderId);
            if(medicineOperatedService.insert(medicineOperated)==0){
                return 0;
            }
        }
        return 1;
    }

    /**
     * 根据当前用户及状态查询订单：
     *      1-判断参数是否合法，判断用户是否合法
     *      2-status状态：
     *          0: 全部
     *          1：待发货
     *          2：已发货
     *          3：已完成
     *      2-根据状态查询相应的MedicineOrderList
     *      3-构造返回VOList (内部类constructOrder)
     *          订单id、总金额、状态（String）、创建时间、发货时间、收货时间
     *          调用地址查询方法，地址信息:省市区、详细地址、电话、姓名
     *          调用购物车药品查询方法，获得订单药品列表
     *          根据药品id列表构造药品信息list：
     *              [药品id、药品品牌、药品名称、药品数量、药品单价、药品图片list、药品单位]
     *
     * @param status
     * @return
     */
    @Override
    public List<MedicineOrderVO> findUserOrder(Integer status) {
        //1-判断参数是否合法，判断用户是否合法
        Integer userId = sysWechatService.getUserInfo().getId();
        if(userId==null){
            return null;
        }
        //2-status状态
        QueryWrapper<MedicineOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if(status!=0){
            wrapper.eq("status", status);
        }
        List<MedicineOrder> medicineOrders=medicineOrderMapper.selectList(wrapper.orderByDesc("created_at"));

        return constructOrder(medicineOrders);

    }

    private List<MedicineOrderVO> constructOrder(List<MedicineOrder> medicineOrders) {
        //3-构造返回VOList
        List<MedicineOrderVO> medicineOrderVOS=new ArrayList<>();
        for(MedicineOrder medicineOrder:medicineOrders){
            MedicineOrderVO medicineOrderVO=new MedicineOrderVO();
            //订单id、总金额、状态（String）、创建时间、发货时间、收货时间
            medicineOrderVO.setOrderId(medicineOrder.getId());
            medicineOrderVO.setTotalPrice(medicineOrder.getTotalPrice());
            medicineOrderVO.setStatus(OrderEnum.findByCode(medicineOrder.getStatus()).toString());
            medicineOrderVO.setCreatedAt(medicineOrder.getCreatedAt());
            medicineOrderVO.setDeliveryAt(medicineOrder.getDeliveryAt());
            medicineOrderVO.setReceiptAt(medicineOrder.getReceiptAt());
            //调用地址查询方法，地址信息：省市区、详细地址、电话、姓名
            Address address=addressService.findAddressById(medicineOrder.getAddressId());
            medicineOrderVO.setLocation(address.getLocation());
            medicineOrderVO.setDetailAddress(address.getDetailAddress());
            medicineOrderVO.setRecipientPhone(address.getRecipientPhone());
            medicineOrderVO.setRecipientName(address.getRecipientName());
            //调用购物车药品查询方法，获得订单药品列表
            List<MedicineOrderGoods> medicineOrderGoods=medicineOrderGoodsService.findGoodsListByOrderId(medicineOrder.getId());
            List<MedicineOrderGoodsVO> medicineOrderGoodsVOS=new ArrayList<>();
            //药品id、药品品牌、药品名称、药品数量、药品单价、药品图片list、药品单位
            for(MedicineOrderGoods mg:medicineOrderGoods){
                MedicineOrderGoodsVO medicineOrderGoodsVO=new MedicineOrderGoodsVO();
                medicineOrderGoodsVO.setMedicineId(mg.getMedicineId());
                medicineOrderGoodsVO.setBrandName(medicineService.findBrandNameByMedicineId(mg.getMedicineId()));
                medicineOrderGoodsVO.setMedicineName(medicineService.findMedicineById(mg.getMedicineId()).getMedicineName());
                medicineOrderGoodsVO.setMedicineNumber(mg.getMedicineNumber());
                medicineOrderGoodsVO.setMedicineUnitPrice(mg.getMedicineUnitPrice());
                medicineOrderGoodsVO.setImglist(medicineImgService.getMedicineImgsByImgIds(medicineImgService.findImgIdsByMedicineId(mg.getMedicineId())));
                medicineOrderGoodsVO.setMedicineUnit(medicineService.findMedicineById(mg.getMedicineId()).getMedicineUnit());
                medicineOrderGoodsVOS.add(medicineOrderGoodsVO);
            }
            medicineOrderVO.setMedicineOrderGoodsVOS(medicineOrderGoodsVOS);
            medicineOrderVOS.add(medicineOrderVO);
        }
        return medicineOrderVOS;
    }

    /**
     * 根据订单id查询订单详情：
     *      1-各种查询
     * @param orderId
     * @return
     */
    @Override
    public MedicineOrderVO getOrderInfo(Integer orderId) {
        QueryWrapper<MedicineOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("id", orderId);
        List<MedicineOrder> medicineOrders=medicineOrderMapper.selectList(wrapper);
        return constructOrder(medicineOrders).get(0);
    }

    @Override
    public List<MedicineOrderVO> findOrderByQuery(MedicineOrderQuery medicineOrderQuery) {
        List<MedicineOrder> medicineOrders=medicineOrderMapper.findOrderByQuery(medicineOrderQuery);
        return constructOrder(medicineOrders);
    }

}
