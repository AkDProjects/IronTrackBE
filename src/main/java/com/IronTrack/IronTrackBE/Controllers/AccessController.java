package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Models.User;
import com.IronTrack.IronTrackBE.Repository.Entities.UserEntitiy;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ControllerAdvice
public class AccessController {

    @Autowired
    UserRepo userRepo;
    @ResponseBody
    @PostMapping("/login")
    //returns back the credentials of the user from the db in the responseBody of the http request
    public List<UserEntitiy> loginSubmit(@RequestBody User user) {
        List<UserEntitiy> listPeople = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
                System.out.println(listPeople);
        return userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());

    }


    @ResponseBody
    @PostMapping("/signup")
    //returns the user credentials in the response body after creating the user in the db
    public List<UserEntitiy> signUpSubmit(@RequestBody UserEntitiy user) {
        UserEntitiy userCreation = new UserEntitiy();
        if (userRepo.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
            return userRepo.findByEmail(user.getEmail());
            //potentially return boolean for false, signifying the user is already in the DB

        } else {userCreation.setEmail(user.getEmail());
            userCreation.setPassword(user.getPassword());
            userCreation.setName(user.getName());
            userRepo.save(userCreation);



        }
        return null;
    }

    @ResponseBody
    @GetMapping("/testAccess")
    public Object test(){
        return userRepo.findAll();
    }

}
