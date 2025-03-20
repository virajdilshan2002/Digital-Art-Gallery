package lk.viraj.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveItemImage(MultipartFile image);
}
