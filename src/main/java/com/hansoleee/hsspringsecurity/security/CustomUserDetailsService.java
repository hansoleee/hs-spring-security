package com.hansoleee.hsspringsecurity.security;

import com.hansoleee.hsspringsecurity.member.domain.Member;
import com.hansoleee.hsspringsecurity.member.domain.MemberRole;
import com.hansoleee.hsspringsecurity.member.domain.Role;
import com.hansoleee.hsspringsecurity.member.repository.MemberRepository;
import com.hansoleee.hsspringsecurity.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User[" + username + "] is not found"));

        List<MemberRole> memberRoles = memberRoleRepository.findByMemberId(member.getId());

        List<GrantedAuthority> collect = memberRoles.stream()
                .map(MemberRole::getRole)
                .map(Role::getEnglishName)
                .collect(Collectors.toList()).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(member.getUsername(), member.getPassword(), collect);
    }
}
