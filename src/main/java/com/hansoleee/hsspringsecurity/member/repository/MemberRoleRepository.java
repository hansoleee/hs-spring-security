package com.hansoleee.hsspringsecurity.member.repository;

import com.hansoleee.hsspringsecurity.member.domain.MemberRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    @EntityGraph(attributePaths = {"member", "role"})
    List<MemberRole> findByMemberId(Long memberId);
}
