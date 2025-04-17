package com.example.ecommerce.repository;

import com.example.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    //@Query(value = "select * from users where name=?1", nativeQuery = true)
    Users findByName(String username);

}
