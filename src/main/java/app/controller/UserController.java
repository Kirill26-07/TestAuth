package app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @RequestMapping(value = "/api/user/create", method = RequestMethod.POST)
    public void createUser() {

    }

    @RequestMapping(value = "/api/user/add_role", method = RequestMethod.POST)
    public void addUserRole() {

    }

    @RequestMapping(value = "/api/user/block", method = RequestMethod.POST)
    public void blockUser() {

    }

    @RequestMapping(value = "/api/user/unblock")
    public void unblockUser() {

    }
}
