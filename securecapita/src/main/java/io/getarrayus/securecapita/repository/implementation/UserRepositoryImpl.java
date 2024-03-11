package io.getarrayus.securecapita.repository.implementation;

import io.getarrayus.securecapita.domain.Role;
import io.getarrayus.securecapita.domain.User;
import io.getarrayus.securecapita.exception.ApiException;
import io.getarrayus.securecapita.repository.RoleRepository;
import io.getarrayus.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import static io.getarrayus.securecapita.enumeration.RoleType.ROLE_USER;
import static io.getarrayus.securecapita.enumeration.VerificationType.ACCOUNT;
import static io.getarrayus.securecapita.query.UserQuery.*;
import static java.util.Map.of;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {
    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;
    @Override
    public User create(User user) throws ApiException {
        //#
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0 ) throw new ApiException("Email already in use, Please try another email");
        //#
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY,parameters,holder);
            user.setId(Objects.requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(user.getId(),ROLE_USER.name());
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(),ACCOUNT.getType());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId",user.getId(),"url",verificationUrl));
//            emailService.sendVerificationUrl(user.getFirstName(),user.getEmail(),verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            return user;



        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: "+ ROLE_USER.name());
        } catch (Exception exception) {
            throw new ApiException("An error occurred ");

        }
    }
    private String getVerificationUrl(String key,String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toUriString();
    };

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName",user.getFirstName())
                .addValue("lastName",user.getLastName())
                .addValue("email",user.getEmail())
                .addValue("password",encoder.encode(user.getPassword()));


    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email",email),Integer.class);
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
