package org.example.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.UserDao;

import java.io.*;
import java.nio.file.Paths;

@Slf4j
public class FileService {

    private final UserDao userDao;

    private final String path;

    public FileService(UserDao userDao, String path) {
        this.userDao = userDao;
        this.path = path;
    }

    //    @Override
//    public UUID updateFile(Part part, Long userId) {
//        UUID uuid = UUID.randomUUID();
//        if(part != null && part.getSize() > 0) {
//            String contentType = part.getContentType();
//            if(!contentType.equalsIgnoreCase("image/jpeg")) {
//                throw new IncorrectFileTypeException();
//            }
//            deleteFile(userId);
//            part.write(path + File.separator + uuid + ".jpg");
//            return uuid;
//        }
//        return null;
//    }
    public void downloadFile(String fileName, HttpServletResponse response) throws IOException, ServletException {
        String filePath = path + File.separator + fileName;
        File imageFile = new File(filePath);
        FileInputStream fis = new FileInputStream(imageFile);
        OutputStream os = response.getOutputStream();
        try {
            response.setContentType("image/*");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }catch (FileNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + e.getMessage());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading file: " + e.getMessage());
        } finally {
            try {
                fis.close();
                os.close();
            } catch (IOException ignore) {} //ignore errors in closing streams
        }

    }

//    @Override
//    public void deleteFile( Long userId){
//
//        String avatarId = userRepository.findUserById(userId).get().getAvatarId();
//        File imageFile = new File(path + File.separator + avatarId + ".jpg");
//        if(imageFile.exists()) {
//            imageFile.delete();
//        }
//
//    }
}

