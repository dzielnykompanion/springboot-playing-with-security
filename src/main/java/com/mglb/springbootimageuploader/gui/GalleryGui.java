package com.mglb.springbootimageuploader.gui;

import com.mglb.springbootimageuploader.model.ImageModel;
import com.mglb.springbootimageuploader.repo.ImageRepo;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route("gallery")
public class GalleryGui extends VerticalLayout {

    private ImageRepo imageRepo;

    @Autowired
    public GalleryGui(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;

        List<ImageModel> all = imageRepo.findAll();

        all.stream().forEach(element -> {
            Image image = new Image(element.getImageAdress(), "empty");
            add(image); // add to vertcal layoput from vaadim
        });


    }




}
