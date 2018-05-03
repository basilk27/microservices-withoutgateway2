package com.mbsystems.multiplicationservice.repository;

import com.mbsystems.multiplicationservice.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User > findByAlias( final String alias);
}
