package com.mglb.springbootimageuploader;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mglb.springbootimageuploader.model.ImageModel;
import com.mglb.springbootimageuploader.repo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploader {

    // CLOUDINARY? A CO TO JEST!
    // First result in the google after "image upload api"
    // Works fine, 1gb free account

    private Cloudinary cloudinary;

    @Autowired
    private ImageRepo imageRepo;

    public ImageUploader(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "mglbt-cloud",
                "api_key", "157621811978625",
                "api_secret", "DBfLc8KifYbD_pq-XJ9hFCVy17o"));
    }

    public String uploadFileAndSaveToDb(String path) {
        File file = new File(path);
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            imageRepo.save(new ImageModel(uploadResult.get("url").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadResult.get("url").toString();
    }
}
