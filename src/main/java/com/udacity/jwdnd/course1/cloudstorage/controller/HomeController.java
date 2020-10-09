package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    private NoteService noteService;
    private UserService userService;
    private CredentialService credentialService;
    private FileService fileService;
    private EncryptionService encryptionService;
    private static final int FILESIZE = 1000000;
    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String loginView(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential, Authentication authentication, Model model) throws Exception {
        model.addAttribute("notes",this.noteService.getNote(getUserId(authentication)));
        model.addAttribute("credentials",this.credentialService.getAllCredentials(getUserId(authentication)));
        model.addAttribute("files", this.fileService.getAllFiles(getUserId(authentication)));
        model.addAttribute("encryptionService", this.encryptionService);
        return "home";
    }
    @PostMapping("/note")
    public String submitNote(@ModelAttribute("note") Note note, Model model, Authentication authentication) {
        if (note.getNoteid() != null) {
            this.noteService.updateNote(note);
        } else {
            this.noteService.addNote(note,getUserId(authentication));
        }
        return "redirect:/result?success";
    }
    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam Integer noteId) {
        this.noteService.deleteNote(noteId);
        return "redirect:/result?success";
    }
    @PostMapping("/credential")
    public String submitCredential(@ModelAttribute("credential") Credential credential, Model model, Authentication authentication) {
        if (credential.getCredentialid() != null) {
            this.credentialService.updateCredential(credential);
        } else {
            this.credentialService.addCredential(credential, getUserId(authentication));
        }
        return "redirect:/result?success";
    }
    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam Integer credentialId) {
        this.credentialService.deleteCredential(credentialId);
        return "redirect:/result?success";
    }
    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication) throws Exception {
        String name = fileUpload.getOriginalFilename();
        boolean nameUsed = false;
        List<File> files = fileService.getAllFiles(getUserId(authentication));
        int beforeUpload = files.size();
        for (File file : files) {
            if (file.getFilename().equals(name)) {
                nameUsed = true;
                break;
            }
        }
        if (nameUsed || fileUpload.getSize() > FILESIZE) {
            return "redirect:/result?error";
        }
        this.fileService.addFile(fileUpload, getUserId(authentication));
        int afterUpload = fileService.getAllFiles(getUserId(authentication)).size();
        if (afterUpload == beforeUpload) {
            return "redirect:/result?error";
        }
        return "redirect:/result?success";
    }
    @GetMapping("/download")
    public ResponseEntity download(@RequestParam String fileName, Authentication authentication) throws Exception {
        List<File> files = this.fileService.getAllFiles(getUserId(authentication));
        File file = files.stream().filter(f -> fileName.equals(f.getFilename())).findAny().orElse(null);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+file.getFilename()+"\"").body(file.getFiledata());
    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam Integer fileId) {
        this.fileService.deleteFile(fileId);
        return "result?success";
    }

    private Integer getUserId(Authentication authentication) {
        String userName = authentication.getName();
        User user = userService.getUserByName(userName);
        return user.getUserId();
    }
}
