package com.example.usersecurity.Service.Member;

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
        if (memberEntity.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        String password = passwordEncoder.encode(memberDTO.getPassword()); //유저가 입력한 비밀번호를 암호화처리
        MemberEntity save = modelMapper.map(memberDTO, MemberEntity.class);
        save.setPassword(password);

        memberRepository.save(save);
    }
}
