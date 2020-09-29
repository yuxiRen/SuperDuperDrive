package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {
    private NoteService noteService;
    private UserService userService;
    private CredentialService credentialService;
    private FileService fileService;
    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String loginView(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Authentication authentication, Model model) throws Exception {
        model.addAttribute("notes",this.noteService.getNote(getUserId(authentication)));
        model.addAttribute("credentials",this.credentialService.getAllCredentials(getUserId(authentication)));
        model.addAttribute("files", this.fileService.getAllFiles(getUserId(authentication)));
        return "home";
    }
    @PostMapping("/note")
    public String submitNote(@ModelAttribute("note") Note note, Model model, Authentication authentication) {
        this.noteService.addNote(note,getUserId(authentication));
        return "redirect:/home";
    }
    @PostMapping("/credential")
    public String submitCredential(@ModelAttribute("credential") Credential credential, Model model, Authentication authentication) {
        this.credentialService.addCredential(credential, getUserId(authentication));
        return "redirect:/home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication) throws IOException {
        this.fileService.addFile(fileUpload, getUserId(authentication));
        return "redirect:/home";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUserByName(userName);
        return user.getUserId();
    }
}
