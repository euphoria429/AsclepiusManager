package com.hcx.asclepiusmanager.store.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hcx.asclepiusmanager.medicine.domain.MedicineRequest;
import com.hcx.asclepiusmanager.medicine.domain.MedicineVO;
import com.hcx.asclepiusmanager.medicine.service.BrandService;
import com.hcx.asclepiusmanager.medicine.service.MedicineService;
import com.hcx.asclepiusmanager.store.domain.ShopCar;
import com.hcx.asclepiusmanager.store.domain.ShopCarDTO;
import com.hcx.asclepiusmanager.store.domain.ShopCarUpdateDTO;
import com.hcx.asclepiusmanager.store.domain.ShopCarVO;
import com.hcx.asclepiusmanager.store.mapper.ShopCarMapper;
import com.hcx.asclepiusmanager.store.service.ShopCarService;
import com.hcx.asclepiusmanager.sysmgr.wechat.domain.SysWechat;
import com.hcx.asclepiusmanager.sysmgr.wechat.service.SysWechatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/4/2 17:43
 */
@Service
public class ShopCarServiceImpl implements ShopCarService {
    @Resource
    ShopCarMapper shopCarMapper;

    @Resource
    SysWechatService sysWechatService;

    @Resource
    BrandService brandService;

    @Resource
    MedicineService medicineService;

    /**
     * 添加购物车逻辑:
     * 获取当前用户id，在购物车表中，根据用户id、商品编号、状态（1）查询有没有记录
     * 如有，在药品原有数量+1；如没有，新增记录。
     * 状态字段说明：
     * 1 --正常，正在用户购物车列表中；
     * 0 --禁用，意味着曾经加购过且购买了；
     * -1 --删除，曾加购过，从购物车中删除了；
     *
     * @param shopCarDTO
     * @return
     */
    @Override
    public Integer saveShopCar(ShopCarDTO shopCarDTO) {
        //获取当前用户id
        Integer userId = sysWechatService.getUserInfo().getId();
        //判空
        if (shopCarDTO.getMedicineId() == null || userId == null) {
            return 0;
        }
        //查询记录
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("medicine_id", shopCarDTO.getMedicineId());
        wrapper.eq("status", 1);
        ShopCar shopCar = shopCarMapper.selectOne(wrapper);

        //不同状态不同操作
        if (shopCar == null) {
            //无的情况，插入新纪录
            shopCar.setMedicineId(shopCarDTO.getMedicineId());
            shopCar.setUserId(userId);
            //数量默认为1
            shopCar.setMedicineNumber(1);
            //状态为 1：正常
            shopCar.setStatus(1);
            return shopCarMapper.insert(shopCar);
        } else {
            //有的情况,数量自增
            shopCar.setMedicineNumber(shopCar.getMedicineNumber() + 1);
            return shopCarMapper.updateById(shopCar);
        }

    }

    /**
     * 查询当前用户购物车列表：
     * 先获取当前用户id，根据用户id查询状态为1（正常）的购物车记录
     * 再根据记录中的药品id获取药品信息，返回VO
     *
     * @return
     */
    @Override
    public List<ShopCarVO> findUserShopCar() {
        //获取当前用户id
        Integer userId = sysWechatService.getUserInfo().getId();
        //判空
        if (userId == null) {
            return null;
        }
        //查询记录
        QueryWrapper<ShopCar> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status", 1);
        ShopCar shopCar = shopCarMapper.selectOne(wrapper);

        //复制到VO
        String mos = JSON.toJSONString(shopCar);
        List<ShopCarVO> shopCarVOS = JSON.parseArray(mos, ShopCarVO.class);
        //构建VO 关联品牌名 药品名 单位 单价
        MedicineRequest medicineRequest = new MedicineRequest();
        for (ShopCarVO shopCarVO : shopCarVOS) {
            medicineRequest.setId(shopCarVO.getMedicineId());
            MedicineVO medicineVO = medicineService.findMedicineWithPages(medicineRequest).get(0);
            shopCarVO.setBrandName(medicineVO.getBrandName());
            shopCarVO.setMedicineName(medicineVO.getMedicineName());
            shopCarVO.setMedicineUnit(medicineVO.getMedicineUnit());
            shopCarVO.setMedicineUnitPrice(medicineVO.getMedicineUnitPrice());
        }
        return shopCarVOS;
    }

    /**
     * 修改购物车药品数量：
     * 根据购物车记录id，查询获得那条记录，然后看请求参数medicineNumber：
     * 1 -- 记录数量自增；
     * -1 -- 记录数量自减。判断如果自减前记录数量为1，则数量不做改变，依然返回成功
     *
     * @param shopCarUpdateDTO
     * @return
     */
    @Override
    public Integer updateShopCar(ShopCarUpdateDTO shopCarUpdateDTO) {
        ShopCar shopCar = shopCarMapper.selectById(shopCarUpdateDTO.getShopCarId());
        if (shopCar == null) {
            return 0;
        } else if (shopCarUpdateDTO.getMedicineNumber() == 1) {
            shopCar.setMedicineNumber(shopCar.getMedicineNumber() + 1);
            return shopCarMapper.updateById(shopCar);
        } else if (shopCarUpdateDTO.getMedicineNumber() == -1) {
            if (shopCar.getMedicineNumber() == 1) {
                return 1;
            } else {
                shopCar.setMedicineNumber(shopCar.getMedicineNumber() - 1);
                return shopCarMapper.updateById(shopCar);
            }
        }
        return 1;
    }

    /**
     * 删除购物车记录：
     * 根据购物车记录id，查询获得那条记录，然后把状态改成-1
     *
     * @param shopCarId
     * @return
     */
    @Override
    public Integer deleteShopCar(Integer shopCarId) {
        ShopCar shopCar = shopCarMapper.selectById(shopCarId);
        if (shopCar == null) {
            return 0;
        }
        shopCar.setStatus(-1);
        return shopCarMapper.updateById(shopCar);

    }
}

