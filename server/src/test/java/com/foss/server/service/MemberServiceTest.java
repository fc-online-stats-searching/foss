package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.api.dto.user.UserApiResponseDto;
import com.foss.server.dao.MemberRepository;
import com.foss.server.domain.member.Member;
import com.foss.server.dto.member.MemberInfoResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Transactional
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private NexonApiClient nexonApiClient;

    @DisplayName("새로운 멤버인 경우 저장한다.")
    @Test
    void newMemberSave() {
        //given
        final String ouid = "testOuid";
        final int level = 1;
        final String nickname = "testNickname";
        UserApiResponseDto userApiResponseDto = new UserApiResponseDto(ouid, nickname, level);
        given(nexonApiClient.requestUserInfo(ouid)).willReturn(userApiResponseDto);
        given(memberRepository.findByAccessId(ouid)).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(new Member(nickname, ouid, level, any(LocalDateTime.class)));

        //when
        MemberInfoResponseDto result = memberService.refreshMember(ouid);

        //then
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(memberRepository).save(any(Member.class));
        assertEquals(ouid, result.getOuid());
        assertEquals(nickname, result.getNickname());
        assertEquals(level, result.getLevel());
    }

    @DisplayName("기존 멤버인 경우 업데이트한다.")
    @Test
    void updateMember() {
        //given
        final String ouid = "existingOuid";
        final int newLevel = 2;
        final String newNickname = "updatedNickname";

        UserApiResponseDto updatedUserInfo = new UserApiResponseDto(ouid, newNickname, newLevel);
        given(nexonApiClient.requestUserInfo(ouid)).willReturn(updatedUserInfo);

        Member existingMember = new Member("oldNickname", ouid, 1, LocalDateTime.now());
        given(memberRepository.findByAccessId(ouid)).willReturn(Optional.of(existingMember));
        Member updatedMember = new Member(newNickname, ouid, newLevel, LocalDateTime.now());
        given(memberRepository.save(any(Member.class))).willReturn(updatedMember);

        //when
        MemberInfoResponseDto result = memberService.refreshMember(ouid);

        //then
        verify(memberRepository, times(1)).save(any(Member.class));
        assertEquals(ouid, result.getOuid());
        assertEquals(newNickname, result.getNickname());
        assertEquals(newLevel, result.getLevel());
    }
}
