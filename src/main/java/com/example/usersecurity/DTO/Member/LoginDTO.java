package com.example.usersecurity.DTO.Member;

import com.example.usersecurity.Constant.Level;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

//로그인용
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO implements UserDetails {
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

    //사용자 이름 출력 메소드
    public String getDisplayUsername() {
        return username;
    }

    //사용자 아이디 출력 메소드
    @Override
    public String getUsername() {
        return userid;
    }

    //비밀번호 출력 메소드
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (level != null) {
            switch (level) {
                case ADMIN:
                    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
                case OPERATOR:
                    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERATOR"));
                case USER:
                    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            }
        }
        return Collections.emptyList(); //사용자가 만든 권한을 security에 등록
    }

    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 차단 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 증명 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
