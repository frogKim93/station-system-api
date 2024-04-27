package com.frogkim93.stationsystemapi.repository;

import com.frogkim93.stationsystemapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByMemberID(String memberID);

    @Query(value = "SELECT HEX(AES_ENCRYPT(?1, 'DEV-KEY')) FROM DUAL", nativeQuery = true)
    String getEncryptedText(String text);
}
