package lk.viraj.backend.service;

import lk.viraj.backend.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO getUserByToken(String token);

    UserDTO searchUser(String username);
    String getUserRoleByToken(String token);
}