package com.kayque.api.repository;

import com.kayque.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UesrRepository extends JpaRepository<User, Long> {
}
