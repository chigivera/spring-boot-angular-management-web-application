package io.getarrayus.securecapita.repository;

import io.getarrayus.securecapita.domain.Role;
import io.getarrayus.securecapita.exception.ApiException;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    T create(T data) throws ApiException;
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    //##

    void addRoleToUser(Long userId, String roleName) throws ApiException;
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName) ;
}
