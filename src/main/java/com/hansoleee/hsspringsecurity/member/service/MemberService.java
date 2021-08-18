package com.hansoleee.hsspringsecurity.member.service;

import com.hansoleee.hsspringsecurity.member.domain.Member;
import com.hansoleee.hsspringsecurity.member.domain.MemberRole;
import com.hansoleee.hsspringsecurity.member.domain.Role;
import com.hansoleee.hsspringsecurity.member.dto.MemberSaveDto;
import com.hansoleee.hsspringsecurity.member.repository.MemberRepository;
import com.hansoleee.hsspringsecurity.member.repository.MemberRoleRepository;
import com.hansoleee.hsspringsecurity.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자[" + email + "]이 존재하지 않습니다."));
    }

    public void register(MemberSaveDto memberSaveDto) {
        memberRepository.findByUsername(memberSaveDto.getUsername())
                .ifPresent(member -> {
                    throw new RuntimeException("사용자[" + member.getUsername() + "]가 존재합니다.");
                });

        Member member = memberRepository.save(memberSaveDto.toMemberEntity(passwordEncoder.encode(memberSaveDto.getPassword())));
        em.flush();

        Role role = roleRepository.findByEnglishName(memberSaveDto.getRoleEnglishName())
                .orElseThrow(() -> new RuntimeException("권한[" + memberSaveDto.getRoleEnglishName() + "]이 존재하지 않습니다."));

        memberRoleRepository.save(new MemberRole(member, role));
    }
}
