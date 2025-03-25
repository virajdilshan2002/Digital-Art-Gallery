package lk.viraj.backend.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveItemImage(MultipartFile image);
    String saveUserProfileImage(MultipartFile image);
    Resource getProfileImage(String image);
}
