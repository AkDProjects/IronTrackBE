package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Repository.Entities.UserEntitiy;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import com.IronTrack.IronTrackBE.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ControllerAdvice
public class AccessController extends UserDetailsService {

    @Autowired
    UserRepo repo;

    @ResponseBody
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    //returns back the credentials of the user from the db in the responseBody of the http request
    public List<UserEntitiy> loginSubmit(@ModelAttribute("loginForm") UserEntitiy user) {

        return repo.findByEmailAndPassword(user.getEmail(), user.getPassword());

    }


    @ResponseBody
    @PostMapping("/signup")
    @CrossOrigin(origins = "http://localhost:4200")
    //returns the user credentials in the response body after creating the user in the db
    public List<UserEntitiy> signUpSubmit(@ModelAttribute("singupForm") UserEntitiy user) {
        if (repo.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            return repo.findByEmailAndPassword(user.getEmail(), user.getPassword());

        } else {//pass data to db

        }
    }
}
