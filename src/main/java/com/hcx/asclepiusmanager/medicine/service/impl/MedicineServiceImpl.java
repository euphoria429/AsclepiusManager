package com.hcx.asclepiusmanager.medicine.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hcx.asclepiusmanager.common.enums.OperationEnum;
import com.hcx.asclepiusmanager.medicine.domain.*;
import com.hcx.asclepiusmanager.medicine.mapper.MedicineMapper;
import com.hcx.asclepiusmanager.medicine.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.hcx.asclepiusmanager.common.enums.SoldStatusEnum.ON_SOLD;

/**
 * @author huangcaixia
 * @Description TODO
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
}
