package com.hcx.asclepiusmanager.medicine.controller;

import com.hcx.asclepiusmanager.medicine.domain.ImageResponse;
import com.hcx.asclepiusmanager.medicine.service.MedicineImgService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author huangcaixia

 * @date 2022/3/27 22:30
 */
@RestController
@RequestMapping("/medicineImg")
public class MedicineImgController {
    @Resource
    MedicineImgService medicineImgService;

    /**
     * 上传商品图片:一张张上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadMedicineImg", method = RequestMethod.POST)
    public ImageResponse uploadMedicineImg(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return medicineImgService.uploadMedicineImg(file);
    }

    /**
     * 根据药品图片id下载图片
     * @param imgID
     * @return 返回多张图片带请求头格式
     */
    @RequestMapping(value = "/downloadMedicineImage", method = RequestMethod.GET)
    public HttpEntity downloadMedicineImage(String imgID) {
        String imagePath = medicineImgService.selectByImgId(imgID).getImagePath();
        Path path = Paths.get(imagePath);
        org.springframework.core.io.Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            System.out.printf(resource.getFilename());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity=ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        return httpEntity;
    }
}
