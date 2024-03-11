package io.getarrayus.securecapita.service;

import io.getarrayus.securecapita.domain.User;
import io.getarrayus.securecapita.dto.UserDTO;
import io.getarrayus.securecapita.exception.ApiException;

public interface UserService {
    UserDTO createUser(User user) throws ApiException;
}
