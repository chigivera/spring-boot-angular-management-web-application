package io.getarrayus.securecapita.service.implementation;

import io.getarrayus.securecapita.domain.User;
import io.getarrayus.securecapita.dto.UserDTO;
import io.getarrayus.securecapita.dtomapper.UserDTOMapper;
import io.getarrayus.securecapita.exception.ApiException;
import io.getarrayus.securecapita.repository.UserRepository;
import io.getarrayus.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) throws ApiException {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
