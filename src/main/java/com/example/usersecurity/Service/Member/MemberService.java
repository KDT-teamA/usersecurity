package com.example.usersecurity.Service.Member;

import com.example.usersecurity.Constant.Level;
import com.example.usersecurity.DTO.Member.MemberDTO;
import com.example.usersecurity.Entity.Member.MemberEntity;
import com.example.usersecurity.Repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {
        //사용자 아이디를 이용해서 사용자 정보를 데이터베이스로부터 읽어와 로그인을 처리하는 메소드
        //security 에서 전달받은 값과 폼에서 입력한 아이디, 비밀번호를 비교해서 로그인처리
        Optional<MemberEntity> memberEntity = memberRepository.findByUserid(loginid);

        if (memberEntity.isPresent()) {
            return User.withUsername(memberEntity.get().getUserid())
                    .password(memberEntity.get().getPassword())
                    .roles(memberEntity.get().getLevel().name())
                    .build();
        } else {
            throw new UsernameNotFoundException(loginid);
        }
    }

    //회원가입처리
    public void saveUser(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByUserid(memberDTO.getUserid());
        if (memberEntity.isPresent()) { //이미 가입된 아이디가 존재하면
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        String password = passwordEncoder.encode(memberDTO.getPassword()); //유저가 입력한 비밀번호를 암호화처리
        MemberEntity save = modelMapper.map(memberDTO, MemberEntity.class);
        save.setPassword(password); //암호화한 비밀번호를 저장
        save.setLevel(Level.USER); //가입 시 기본 권한은 USER(사용자)로

        memberRepository.save(save);
    }
}
/*
SecurityConfig : 맵핑별 권한 부여 설정
                 로그인 설정, 로그아웃 설정

~SuccessHandler : 로그인 설공 시 설정 (섹션설정 - 클라이언트 정보 저장, 유효시간 설정)

~EntryPoint : 로그인 실패 시 설정 (생략 가능)

Service 에서 사용자가 로그인처리를 재구성(implements UserDetailsService)
    해당 서비스는 security의 로그인 처리를 위한 사용자 서비스
    아이디로 존재여부확인 후 아이디, 비밀번호, 권한을 security에 전달해서 비교
*/