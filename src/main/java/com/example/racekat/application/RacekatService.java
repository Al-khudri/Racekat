package com.example.racekat.application;

import com.example.racekat.domain.Racekat;
import com.example.racekat.infrastucture.Racekatrepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class RacekatService {

    private final Racekatrepo racekatrepo;
    private final String uploadDir = "src/main/resources/static/upload/";

    public RacekatService(Racekatrepo racekatrepo) {
        this.racekatrepo = racekatrepo;
        Path uploadPath = Paths.get(uploadDir);
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }
    }

    public Racekat createCat(Racekat racekat, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir + fileName);

            Files.write(uploadPath, imageFile.getBytes());
            racekat.setImageUrl("/upload/" + fileName);
        }
        return racekatrepo.save(racekat);
    }

    public List<Racekat> getAllPosts() {
        return racekatrepo.findAll();
    }

    public List<Racekat> getPostsByUserId(int userId) {
        return racekatrepo.findByUserId(userId);
    }

    public Racekat getPostById(int catId) {
        return racekatrepo.findById(catId);
    }

    public void updateRacecat(Racekat racekat, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Racekat existingRacekat = racekatrepo.findById(racekat.getId());
            if (existingRacekat != null && existingRacekat.getImageUrl() != null) {
                try {
                    Path oldImagePath = Paths.get("src/main/resources" + existingRacekat.getImageUrl());
                    Files.deleteIfExists(oldImagePath);
                } catch (Exception e) {
                    System.err.println("Could not delete old image: " + e.getMessage());
                }
            }
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir + fileName);
            Files.write(uploadPath, imageFile.getBytes());
            racekat.setImageUrl("/upload/" + fileName);
        }
        racekatrepo.update(racekat);
    }

    public void deleteCat(int catId) {
        Racekat racekat = racekatrepo.findById(catId);
        if (racekat != null && racekat.getImageUrl() != null) {
            try {
                Path imagePath = Paths.get("src/main/resources" + racekat.getImageUrl());
                Files.deleteIfExists(imagePath);
            } catch (Exception e) {
                System.err.println("Could not delete old image: " + e.getMessage());
            }
        }
        racekatrepo.delete(catId);
    }
}