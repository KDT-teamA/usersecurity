package com.example.usersecurity.DTO.Member;

import com.example.usersecurity.Constant.Level;
import lombok.*;

//회원가입용
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private Integer pid; //번호

    private String userid; //사용자 아이디

    private String password; //비밀번호

    private String username; //사용자 이름

    private String usertel; //전화번호

    private Level level;

    private String userLevelDescription;

    public void setUserLevel(Level userLevel) {
        this.level = userLevel;
        this.userLevelDescription = userLevel == null ? null : userLevel.getDescription();
    }
}
