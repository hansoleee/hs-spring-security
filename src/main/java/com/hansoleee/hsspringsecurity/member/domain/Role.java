package com.hansoleee.hsspringsecurity.member.domain;

import com.hansoleee.hsspringsecurity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tc_role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(of = "code", callSuper = false)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "role_code")
    private Long code;

    private String englishName;
    private String koreanName;

    public Role(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }
}
