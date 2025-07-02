package com.example.usersecurity.Entity.Member;

import com.example.usersecurity.Constant.Level;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long pid; //번호

    @Column(length = 50, nullable = false, unique = true)
    private String userid; //사용자 아이디

    @Column(nullable = false)
    private String password; //비밀번호

    @Column(length = 30, nullable = false)
    private String username; //사용자 이름

    @Column(length = 30)
    private String usertel; //전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;
}
