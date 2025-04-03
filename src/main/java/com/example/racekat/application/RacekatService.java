package com.example.racekat.application;


import com.example.racekat.domain.Racekat;
import com.example.racekat.infrastucture.Racekatrepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacekatService {


    private final Racekatrepo racekatrepo;

    public RacekatService(Racekatrepo racekatrepo) {
        this.racekatrepo = racekatrepo;
    }

    public Racekat createKat(Racekat racekat) {
        return racekatrepo.save(racekat);
    }

    public Racekat getUserByCatName(String Catname) {
        return racekatrepo.findByName(Catname);
    }

    public List<Racekat> getAllUsers(){
        return racekatrepo.findAll();
    }

    public void updateRacekat(Racekat racekat) {
        racekatrepo.update(racekat);
    }

    public void deleteKat(String Email) {
        racekatrepo.delete(Email);
    }
}

