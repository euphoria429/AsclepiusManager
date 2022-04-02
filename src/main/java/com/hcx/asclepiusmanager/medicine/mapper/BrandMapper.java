package com.hcx.asclepiusmanager.medicine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hcx.asclepiusmanager.medicine.domain.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/26 12:08
 */
@Mapper
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 根据供应商名称查询
     */
    @Select("select * from brand where brand_name = #{name}")
    List<Brand> findByBrandName(@Param("name") String name);

    List<Brand> findBrandByOption(@Param("brand") Brand brand);
}
