package com.hansoleee.hsspringsecurity.member.dto;

import com.hansoleee.hsspringsecurity.member.domain.Member;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberSaveDto {

    private String username;
    private String password;
    private String email;
    private String roleEnglishName;

    public Member toMemberEntity(String encodedPassword) {
        return new Member(username, encodedPassword, email);
    }
}
