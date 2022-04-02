package com.hcx.asclepiusmanager.medicine.service;

import com.hcx.asclepiusmanager.medicine.domain.ImageResponse;
import com.hcx.asclepiusmanager.medicine.domain.MedicineImg;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/27 22:29
 */
public interface MedicineImgService {
    ImageResponse uploadMedicineImg(MultipartFile file) throws IOException;

    MedicineImg selectByImgId(String s);

    Integer updateByImgId(MedicineImg medicineImg);

    List<String> findImgIdsByMedicineId(Integer id);

    List<String> getMedicineImgsByImgIds(List<String> imgIds);
}
