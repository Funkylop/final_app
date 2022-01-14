package com.hramyko.finalapp.web.controllers;

import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.persistence.entity.WaitingList;
import com.hramyko.finalapp.service.UserService;
import com.hramyko.finalapp.service.WaitingListService;
import com.hramyko.finalapp.service.impl.EmailSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api//waiting_list")
public class WaitingListController {

    private final WaitingListService waitingListService;
    private final UserService userService;
    private final EmailSenderServiceImpl emailSenderServiceImpl;

    @Autowired
    public WaitingListController(WaitingListService waitingListService, UserService userService,
                                 EmailSenderServiceImpl emailSenderServiceImpl) {
        this.waitingListService = waitingListService;
        this.userService = userService;
        this.emailSenderServiceImpl = emailSenderServiceImpl;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user.delete')")
    public String showWaitingList() {
        return waitingListService.findAll().toString();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String showUserInfo(@PathVariable("id") int formId) {
        return userService.findUserById(waitingListService.getUserId(formId)).toString();
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAuthority('user.delete')")
    public String applyForm(@PathVariable("id") int formId, @RequestBody String jsonString) {
        User user = userService.getUserFromOptional(waitingListService.getUserId(formId));
        if (user == null) throw new RuntimeException("Error in apply form");
        userService.updateUserRole(user.getId(), jsonString);
        emailSenderServiceImpl.notifyMessage(user);
        return "User has been added to traders";
    }

    @PostMapping("/reg_trader")
    @PreAuthorize("hasAuthority('user.read')")
    public String regTraderPage() {
        WaitingList waitingList = new WaitingList();
        waitingList.setUser(userService.currentUser());
        waitingListService.addUserForm(waitingList);
        return "You successfully send your form";
    }
}