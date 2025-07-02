package com.example.usersecurity.Repository.Member;

import com.example.usersecurity.Entity.Member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    //사용자 id로 조회
    Optional<MemberEntity> findByUserid(String userid);
}
