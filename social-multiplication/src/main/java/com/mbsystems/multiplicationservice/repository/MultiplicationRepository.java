package com.mbsystems.multiplicationservice.repository;

import com.mbsystems.multiplicationservice.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
}
