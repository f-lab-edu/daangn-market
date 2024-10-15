package com.limikju.daangn_market.repository;

import com.limikju.daangn_market.domain.Member;
import com.limikju.daangn_market.domain.dto.MemberDto;
import com.limikju.daangn_market.domain.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 레포지토리 삽입")
    @Transactional
    void memberJoinTest() {
        MemberDto memberDto = MemberDto.builder()
                .email(UUID.randomUUID().toString() + "@gmail.com")
                .password("1234")
                .name("limikju")
                .phone("010-1234-5678")
                .address("서울시 강남구")
                .role(Role.USER)
                .build();
        memberRepository.join(memberDto);
    }

    @Test
    @DisplayName("회원 레포지토리 삽입 email 중복 실패")
    @Transactional
    void memberJoinEmailDuplicateTest() {
        // given
        String email = UUID.randomUUID().toString() + "@gmail.com";

        // when
        memberRepository.join(MemberDto.builder()
                .email(email)
                .password("1234")
                .name("limikju")
                .phone("010-1234-5678")
                .address("서울시 강남구")
                .role(Role.USER)
                .build());

        // then
        assertThrows(Exception.class, () -> {
            memberRepository.join(MemberDto.builder()
                    .email(email)
                    .password("1234")
                    .name("limikju")
                    .phone("010-1234-5678")
                    .address("서울시 강남구")
                    .role(Role.USER)
                    .build());
        });
    }


    @Test
    @DisplayName("회원 조회")
    @Transactional
    void findByMemberTest() {
        // given
        String email = UUID.randomUUID().toString() + "@gmail.com";
        MemberDto memberDto = MemberDto.builder()
                .email(email)
                .password("1234")
                .name("limikju")
                .phone("010-1234-5678")
                .address("서울시 강남구")
                .role(Role.USER)
                .build();

        // when
        memberRepository.join(memberDto);

        // then
        Member member = memberRepository.findByEmail(email);
        assertThat(member.getEmail()).isEqualTo(memberDto.getEmail());
        assertThat(member.getPassword()).isEqualTo(memberDto.getPassword());
        assertThat(member.getName()).isEqualTo(memberDto.getName());
        assertThat(member.getPhone()).isEqualTo(memberDto.getPhone());
        assertThat(member.getAddress()).isEqualTo(memberDto.getAddress());
        assertThat(member.getRole()).isEqualTo(memberDto.getRole());
    }
}