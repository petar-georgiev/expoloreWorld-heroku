package com.example.explore.service.impl;

import com.example.explore.repository.PictureRepository;
import com.example.explore.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<String> findAllByUrls() {
        return pictureRepository.findAllByUrl();
    }
}
