package com.hansoleee.hsspringsecurity.member.controller;

import com.hansoleee.hsspringsecurity.member.domain.Member;
import com.hansoleee.hsspringsecurity.member.domain.MemberRole;
import com.hansoleee.hsspringsecurity.member.domain.Role;
import com.hansoleee.hsspringsecurity.member.dto.MemberSaveDto;
import com.hansoleee.hsspringsecurity.member.repository.MemberRepository;
import com.hansoleee.hsspringsecurity.member.repository.MemberRoleRepository;
import com.hansoleee.hsspringsecurity.member.repository.RoleRepository;
import com.hansoleee.hsspringsecurity.member.service.MemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/{email}")
    public RestResponse<Member> findByEmail(@PathVariable String email,
                                            HttpServletRequest request) {
        log.info(request.getRequestURL().toString() + " " + request.getMethod() + " email: {}", email);
        Member member = memberService.findMemberByEmail(email);
        return new RestResponse<>(true, "found member", member);
    }

    @PostMapping("/new")
    public RestResponse<?> register(@RequestBody MemberSaveDto memberSaveDto,
                                 HttpServletRequest request) {
        log.info(request.getRequestURL().toString() + " " + request.getMethod() + " memberSaveDto: {}", memberSaveDto);

        memberService.register(memberSaveDto);

        return new RestResponse<>(true, "saved member", memberSaveDto.getUsername());
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    static class RestResponse<T> {

        private boolean success;
        private String message;
        private T data;

        public RestResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public RestResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
