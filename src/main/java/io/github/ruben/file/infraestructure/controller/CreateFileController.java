package io.github.ruben.file.infraestructure.controller;

import io.github.ruben.file.application.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("file/upload")
@RestController
public class CreateFileController {

    private final FileService fileService;

    @Autowired
    public CreateFileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   RedirectAttributes redirectAttributes) {

        fileService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return ResponseEntity.status(HttpStatus.OK)
                .body("Files uploaded successfully: " + file.getOriginalFilename());
    }

    @PostMapping("/{type}")
    public ResponseEntity<String> handleFileUploadType(@PathVariable String type,
                                                       @RequestParam("file") MultipartFile file,
                                                       RedirectAttributes redirectAttributes) {

        fileService.storeType(type, file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return ResponseEntity.status(HttpStatus.OK)
                .body("Files uploaded successfully: " + file.getOriginalFilename());
    }
}
