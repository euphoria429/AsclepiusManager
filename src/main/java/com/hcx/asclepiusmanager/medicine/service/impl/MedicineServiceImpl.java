package com.hcx.asclepiusmanager.medicine.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hcx.asclepiusmanager.common.enums.OperationEnum;
import com.hcx.asclepiusmanager.medicine.domain.*;
import com.hcx.asclepiusmanager.medicine.mapper.MedicineMapper;
import com.hcx.asclepiusmanager.medicine.service.*;
import com.hcx.asclepiusmanager.store.service.MedicineOrderService;
import com.hcx.asclepiusmanager.sysmgr.wechat.service.SysWechatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.hcx.asclepiusmanager.common.enums.SoldStatusEnum.ON_SOLD;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:02
 */
@Service
public class MedicineServiceImpl implements MedicineService {
    @Resource
    MedicineMapper medicineMapper;

    @Resource
    MedicineImgService medicineImgService;

    @Resource
    BrandService brandService;

    @Resource
    MedicineTypeService medicineTypeService;

    @Resource
    MedicineOperatedService medicineOperatedService;

    @Resource
    SysWechatService sysWechatService;

    @Lazy
    @Autowired
    MedicineOrderService medicineOrderService;

    @Override
    public List<MedicineVO> findMedicineWithPages(MedicineRequest medicineRequest) {
        List<Medicine> medicines=medicineMapper.findMedicineWithPages(medicineRequest);
        String mos = JSON.toJSONString(medicines);
        List<MedicineVO> medicineVOS = JSON.parseArray(mos, MedicineVO.class);
        //关联品牌名 药品类别名 图片
        for(MedicineVO medicineVO:medicineVOS){
            medicineVO.setBrandName(brandService.findBrandById(medicineVO.getBrandId()).getBrandName());
            medicineVO.setMedicineTypeName(medicineTypeService.findMedicineTypeById(medicineVO.getMedicineTypeId()).getMedicineTypeName());
            medicineVO.setMedicineImgs(medicineImgService.getMedicineImgsByImgIds(medicineImgService.findImgIdsByMedicineId(medicineVO.getId())));
        }
        return medicineVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveMedicine(MedicineDTO medicineDTO) {
        Medicine medicine=new Medicine();
        BeanUtils.copyProperties(medicineDTO,medicine);
        //设置药品初始状态：在售
        medicine.setStatus(ON_SOLD.getCode());
        if(medicineMapper.insert(medicine)!=1){
            return 0;
        }
        int medicineId=medicine.getId();
        if(medicineDTO.getMedicineImg()!=null && !medicineDTO.getMedicineImg().isEmpty()){
            for (String s:medicineDTO.getMedicineImg()){
                MedicineImg medicineImg=medicineImgService.selectByImgId(s);
                medicineImg.setMedicineId(medicineId);
                if(medicineImgService.updateByImgId(medicineImg)!=1){
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * 手动修改库存
     * @param medicine
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateStock(Medicine medicine) {
        //判空
        if(medicine.getMedicineStore()<0 || medicine.getMedicineStore()==null){
            return 0;
        }
        //查询原库存
        Integer beforeStore=medicineMapper.selectById(medicine.getId()).getMedicineStore();

        //获得修改数:新库存-原库存
        Integer number=medicine.getMedicineStore()-beforeStore;

        //更新修改
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("id",medicine.getId());
        updateWrapper.set("medicine_store",medicine.getMedicineStore());
        if(medicineMapper.update(null,updateWrapper)==0){
            return 0;
        }

        //写入操作表
        MedicineOperated medicineOperated=new MedicineOperated();
        medicineOperated.setMedicineId(medicine.getId());
        medicineOperated.setOperationCode(OperationEnum.MODIFY.getCode());
        medicineOperated.setNumber(number);
        medicineOperated.setAfterStore(medicine.getMedicineStore());
        if(medicineOperatedService.insert(medicineOperated)==0){
            return 0;
        }
        return 1;

    }

    @Override
    public Integer updateMedicine(Medicine medicine) {
        return medicineMapper.updateById(medicine);
    }

    @Override
    public Integer changeSoldStatus(Medicine medicine) {
        return medicineMapper.updateById(medicine);
    }

    /**
     * 入库
     * @param medicine
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addStock(Medicine medicine) {
        //判空
        if(medicine.getMedicineStore()<0 || medicine.getMedicineStore()==null){
            return 0;
        }
        //查询原库存
        Integer beforeStore=medicineMapper.selectById(medicine.getId()).getMedicineStore();

        //修改数:直接从前端获取 medicine.getMedicineStore()

        //修改后库存：原库存+修改数
        Integer afterStore=beforeStore+medicine.getMedicineStore();

        //更新修改
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("id",medicine.getId());
        updateWrapper.set("medicine_store",afterStore);
        if(medicineMapper.update(null,updateWrapper)==0){
            return 0;
        }

        //写入操作表
        MedicineOperated medicineOperated=new MedicineOperated();
        medicineOperated.setMedicineId(medicine.getId());
        medicineOperated.setOperationCode(OperationEnum.RESTOCK.getCode());
        //修改数
        medicineOperated.setNumber(medicine.getMedicineStore());
        medicineOperated.setAfterStore(afterStore);
        if(medicineOperatedService.insert(medicineOperated)==0){
            return 0;
        }
        return 1;
    }

    @Override
    public List<MedicineNumberVO> findMedicineWithNumber(MedicineRequest medicineRequest) {
        List<Medicine> medicines=medicineMapper.findMedicineWithNumber(medicineRequest);
        String mos = JSON.toJSONString(medicines);
        List<MedicineNumberVO> medicineVOS = JSON.parseArray(mos, MedicineNumberVO.class);
        //关联品牌名 药品类别名 图片 销量
        for(MedicineNumberVO medicineVO:medicineVOS){
            medicineVO.setBrandName(brandService.findBrandById(medicineVO.getBrandId()).getBrandName());
            medicineVO.setMedicineTypeName(medicineTypeService.findMedicineTypeById(medicineVO.getMedicineTypeId()).getMedicineTypeName());
            medicineVO.setMedicineImgs(medicineImgService.getMedicineImgsByImgIds(medicineImgService.findImgIdsByMedicineId(medicineVO.getId())));
            medicineVO.setNumber(medicineOperatedService.findMedicineMonthlyNumber(medicineVO.getId()));
        }
        return medicineVOS;
    }

    @Override
    public Medicine findMedicineById(Integer medicineId) {
        return medicineMapper.selectById(medicineId);
    }

    @Override
    public Integer updateSoldStore(Medicine medicine) {
        return medicineMapper.updateById(medicine);
    }

    @Override
    public String findBrandNameByMedicineId(Integer medicineId) {
        Medicine medicine=medicineMapper.selectById(medicineId);
        return brandService.findBrandById(medicine.getBrandId()).getBrandName();
    }

    @Override
    public Map<String, Integer> findCountInfo() {
        Map<String, Integer> map = new HashMap<>(16);
        int userNumber=sysWechatService.countUser();
        QueryWrapper<Medicine> wrapper=new QueryWrapper<>();
        int medicineNumber=medicineMapper.selectCount(wrapper);
        int orderNumber=medicineOrderService.countOrder();
        map.put("userNumber",userNumber);
        map.put("medicineNumber",medicineNumber);
        map.put("orderNumber",orderNumber);
        return map;
    }

    /**
     * 最近六天的记录
     * @return
     */
    @Override
    public List<OutInDateVO> findMedicineOutInRecord() {
        List<MedicineOutInVO> medicineOutInVOS=medicineMapper.findMedicineOutInRecord();
        Map<String, List<MedicineOutInVO>> map = new LinkedHashMap<>();
        for(MedicineOutInVO medicineOutInVO:medicineOutInVOS){
            int code=medicineOutInVO.getOperationCode();
            medicineOutInVO.setOperation(OperationEnum.findByCode(code).toString());
            //key不存在
            if(!map.containsKey(medicineOutInVO.getDays())){
                List<MedicineOutInVO> m=new ArrayList<>();
                m.add(medicineOutInVO);
                map.put(medicineOutInVO.getDays(),m);
            }else {
                map.get(medicineOutInVO.getDays()).add(medicineOutInVO);
            }
        }
        List<OutInDateVO> outInDateVOS=new ArrayList<>();
        for (Map.Entry<String, List<MedicineOutInVO>> entry : map.entrySet()) {
            OutInDateVO outInDateVO=new OutInDateVO();
            outInDateVO.setDays(entry.getKey());
            outInDateVO.setMedicineOutInVOS(entry.getValue());
            outInDateVOS.add(outInDateVO);
        }
        return outInDateVOS;
    }
}
