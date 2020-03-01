package com.mglb.springbootimageuploader.repo;

import com.mglb.springbootimageuploader.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<ImageModel, Long> {

}
