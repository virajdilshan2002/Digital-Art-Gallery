package lk.viraj.backend.service;

import lk.viraj.backend.dto.ProfileDTO;
import lk.viraj.backend.dto.UserDTO;

public interface UserService {
    void updatePassword(String newPw);

    void updateUser(UserDTO userDTO);

    int saveUser(UserDTO userDTO);

    UserDTO getUserByToken(String token);

    UserDTO searchUser(String username);

    String getUserRoleByToken(String token);

    int saveAdmin(UserDTO userDTO);

    ProfileDTO convertToProfileDTO(UserDTO user);
}