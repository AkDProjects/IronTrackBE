package com.IronTrack.IronTrackBE.Controllers;

import com.IronTrack.IronTrackBE.Repository.Entities.UserEntitiy;
import com.IronTrack.IronTrackBE.Repository.UserRepo;
import com.IronTrack.IronTrackBE.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@ControllerAdvice
public class AccessController extends UserDetailsService {
@Autowired
    UserRepo repo;
    @PostMapping("/login")
    public String submit(@ModelAttribute("loginForm") UserEntitiy user, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }
        boolean passCredCheck = repo.existsByEmailAndPassword(user.getEmail(), user.getPassword());
        if (!passCredCheck){
            return "/% isn't a registered user";
        }else{}
        return "User";

}
    @PostMapping("/signup")
    public String Submit(@ModelAttribute("singupForm") UserEntitiy user, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }
        boolean credCheck = repo.existsByEmailAndPassword(user.getEmail(), user.getPassword());
        if (credCheck){
            //if true, do not allow sign up, throw error
        }else{

        }
        return "User";

    }
}
