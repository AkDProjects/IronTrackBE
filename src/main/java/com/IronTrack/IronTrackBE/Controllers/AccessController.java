package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.User;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntitiy;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ControllerAdvice
public class AccessController {

    @Autowired
    UserRepo userRepo;
    @ResponseBody
    @PostMapping("/login")
    //@CrossOrigin(origins = "http://localhost:4200")
    //returns back the credentials of the user from the db in the responseBody of the http request
    public List<UserEntitiy> loginSubmit(@ModelAttribute("loginForm") User user) {

        return userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());

    }


    @ResponseBody
    @PostMapping("/signup")
    //@CrossOrigin(origins = "http://localhost:4200")
    //returns the user credentials in the response body after creating the user in the db
    public List<UserEntitiy> signUpSubmit(@ModelAttribute("singupForm") UserEntitiy user) {
        UserEntitiy userCreation = new UserEntitiy();
        if (userRepo.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            return userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
            //potentially return boolean for false, signifying the user is already in the DB

        } else {userCreation.setEmail(user.getEmail());
            userCreation.setPassword(user.getPassword());
            userCreation.setName(user.getName());
            userRepo.save(userCreation);

            //send info from model to db, then .saveAll method (refer to other controller)

        }
        return null;
    }

    @ResponseBody
    @GetMapping("/testAccess")
    public Object test(){
        return userRepo.findAll();
    }

}
