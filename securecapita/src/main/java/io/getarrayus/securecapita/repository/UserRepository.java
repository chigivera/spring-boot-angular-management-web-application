package io.getarrayus.securecapita.repository;

import io.getarrayus.securecapita.domain.User;
import io.getarrayus.securecapita.exception.ApiException;

import java.util.Collection;

public interface UserRepository <T extends User>{
    T create(T data) throws ApiException;
    Collection<T> list(int page,int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
}
