package com.hcx.asclepiusmanager.medicine.service;

import com.hcx.asclepiusmanager.medicine.domain.Brand;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/26 12:09
 */
public interface BrandService {
    Integer saveBrand(Brand brand);

    List findBrandWithPages(Brand brand);

    List findAllBrand();

    Brand findBrandById(Integer brandId);
}
