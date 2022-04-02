package com.hcx.asclepiusmanager.medicine.service.impl;

import com.hcx.asclepiusmanager.common.utils.CustFileUtils;
import com.hcx.asclepiusmanager.medicine.domain.ImageResponse;
import com.hcx.asclepiusmanager.medicine.domain.MedicineImg;
import com.hcx.asclepiusmanager.medicine.mapper.MedicineImgMapper;
import com.hcx.asclepiusmanager.medicine.service.MedicineImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/27 22:29
 */
@Service
@Slf4j
public class MedicineImgServiceImpl implements MedicineImgService {

    @Autowired
    MedicineImgMapper medicineImgMapper;

    @Value("${image.path}")
    String imagePath;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImageResponse uploadMedicineImg(MultipartFile file) throws IOException {
        //随机生成UUID
        UUID uuid = UUID.randomUUID();
        String folder = imagePath;
        //创建存放上传文件的文件夹，如果不存在则自动创建
        if(!new File(folder).exists()){
            new File(folder).mkdir();
        }
        //文件路径
        String outputFile = folder +  File.separatorChar + CustFileUtils.generateFileNameUUID() + CustFileUtils.getSuffix(file.getOriginalFilename());
        log.debug("insert image: {} with path: {} into database", file.getOriginalFilename(), outputFile);
        //上传文件
        file.transferTo(new File(outputFile));
        //文件信息写入数据库
        MedicineImg doc = new MedicineImg();
        //处理UUID为32位
        String docId = uuid.toString().replace("-", "");
        doc.setImageId(docId);
        doc.setImageName(file.getOriginalFilename());
        doc.setImagePath(outputFile);
        medicineImgMapper.insert(doc);
        //构造返回的图片链接
        ImageResponse metadata = new ImageResponse();
        metadata.setLink(docId);
        return metadata;
    }

    @Override
    public MedicineImg selectByImgId(String s) {
        return medicineImgMapper.selectById(s);
    }

    @Override
    public Integer updateByImgId(MedicineImg medicineImg) {
        return medicineImgMapper.updateById(medicineImg);
    }

    @Override
    public List<String> findImgIdsByMedicineId(Integer id) {
        return medicineImgMapper.findImgIdsByMedicineId(id);
    }

    @Override
    public List<String> getMedicineImgsByImgIds(List<String> imgIds) {
        List<String> paths=new ArrayList<>();
        for (String id:imgIds){
            Path path = Paths.get(medicineImgMapper.selectById(id).getImagePath());
            Resource resource = null;
            try {
                resource = new UrlResource(path.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            // TODO 获取本地ip方式待改进！！
            paths.add("http://10.168.1.184:8989/api/image/"+resource.getFilename());
        }
        return paths;
    }


}
