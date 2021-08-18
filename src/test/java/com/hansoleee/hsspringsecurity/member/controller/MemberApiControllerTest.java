package com.hansoleee.hsspringsecurity.member.controller;

import com.google.gson.Gson;
import com.hansoleee.hsspringsecurity.member.domain.Member;
import com.hansoleee.hsspringsecurity.member.dto.MemberSaveDto;
import com.hansoleee.hsspringsecurity.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    void findByEmail() throws Exception {
        String content = "\"email\":\"leehansol.kr@gmail.com\"";
        Member member = new Member("lhs", "a", "leehansol.kr@gmail.com");

        given(memberService.findMemberByEmail("leehansol.kr@gmail.com")).willReturn(member);

        mockMvc.perform(get("/api/members/leehansol.kr@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void register() throws Exception {
        MemberSaveDto memberSaveDto = new MemberSaveDto("lhs", "a", "leehansol.kr@gmail.com", "ROLE_USER");
        Gson gson = new Gson();
        String content = gson.toJson(memberSaveDto);

        mockMvc.perform(post("/api/members/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());

        verify(memberService).register(refEq(memberSaveDto));
    }
}