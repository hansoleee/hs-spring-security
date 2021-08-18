package com.hansoleee.hsspringsecurity.member.repository;

import com.hansoleee.hsspringsecurity.member.domain.Member;
import com.hansoleee.hsspringsecurity.member.domain.MemberRole;
import com.hansoleee.hsspringsecurity.member.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberRoleRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MemberRoleRepository memberRoleRepository;

    @BeforeEach
    void init() {
        Member member = new Member("lhs", "a", "leehansol.kr@gmail.com");
        memberRepository.save(member);

        Role role = new Role("ROLE_USER", "사용자");
        roleRepository.save(role);
    }

    @Test
    void save() {
        Member lhs = memberRepository.findByUsername("lhs").orElseThrow();
        Role roleUser = roleRepository.findByEnglishName("ROLE_USER").orElseThrow();

        MemberRole memberRole = memberRoleRepository.save(new MemberRole(lhs, roleUser));
        assertThat(memberRole.getMember()).isEqualTo(lhs);
        assertThat(memberRole.getRole()).isEqualTo(roleUser);
    }
}