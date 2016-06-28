package com.theironyard.controllers;

import com.theironyard.PasswordStorage;
import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;


import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jonathandavidblack on 6/27/16.
 */
@Controller
public class AnonFileController {
    @Autowired
    AnonFileRepository files;

    @PostConstruct
    public void init() throws SQLException {
        Server.createWebServer().start();
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, Integer id, boolean isPerm, String comment, String deletionPassword) throws IOException, PasswordStorage.CannotPerformOperationException {
        if (files.countByIsPermFalse() <= 4) {

            File dir = new File("public/files");
            dir.mkdirs();

            File uploadedFile = File.createTempFile("file", file.getOriginalFilename(), dir);
            FileOutputStream fos = new FileOutputStream(uploadedFile);
            fos.write(file.getBytes());

            AnonFile anonFile = new AnonFile(file.getOriginalFilename(), uploadedFile.getName(), isPerm, comment, PasswordStorage.createHash(deletionPassword));
            files.save(anonFile);
        }
        else {
            AnonFile fileToDelete = files.findOne(files.findMinNonPermId());  //creating an object into which we insert the item(file) to delete
            File fileOnDisk = new File("public/files/" + fileToDelete.getRealFileName());
            fileOnDisk.delete(); //is highlighted bc we are not capturing the return value that that indicates the success of the deletion

            files.delete(files.findMinNonPermId());

            File dir = new File("public/files");
            dir.mkdirs();

            File uploadedFile = File.createTempFile("file", file.getOriginalFilename(), dir);
            FileOutputStream fos = new FileOutputStream(uploadedFile);
            fos.write(file.getBytes());

            AnonFile anonFile = new AnonFile(file.getOriginalFilename(), uploadedFile.getName(), isPerm, comment, PasswordStorage.createHash(deletionPassword));
            files.save(anonFile);
        }
        return "redirect:/";

    }
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
        public String deleteUpload(int id, String deletionPassword) throws Exception {
        AnonFile anonFile = files.findOne(id);
        if (!PasswordStorage.verifyPassword(deletionPassword, anonFile.getDeletionPassword())) {
            throw new Exception("Wrong password");
        }
        else {
            files.delete(id);
        }

        return "redirect:/";
    }

}
