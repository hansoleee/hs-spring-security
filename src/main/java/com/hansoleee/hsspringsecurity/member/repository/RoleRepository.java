package com.hansoleee.hsspringsecurity.member.repository;

import com.hansoleee.hsspringsecurity.member.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.englishName = :englishName")
    Optional<Role> findByEnglishName(@Param("englishName") String englishName);
}
