package com.example.racekat.application;

import com.example.racekat.domain.Racekat;
import com.example.racekat.domain.User;
import com.example.racekat.infrastucture.Racekatrepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Userservice {

    private final Racekatrepo racekatrepo = null;

    public Userservice(Racekatrepo racekatrepo) {
        this.racekatrepo = racekatrepo;
    }

    public static User Login(String email, String password) {
        return null;
    }

    public Racekatrepo createRacekatrepo() {
        return racekatrepo;
    }
    public Racekatrepo getRacekatrepo() {
        return racekatrepo;
    }
    public Racekatrepo List<Racekat> getAllRacekat(){
        return racekatrepo.findAll();
    }


    public void setRacekatrepo(Racekat racekat) {
        racekatrepo.update(racekat);
    }

    public void deleteRacekatrepo() {
        racekatrepo.delete(Email);
    }
}
