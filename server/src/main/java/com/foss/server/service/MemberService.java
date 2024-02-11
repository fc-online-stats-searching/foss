package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.dao.MemberRepository;
import com.foss.server.domain.member.Member;
import com.foss.server.dto.api.user.UserApiResponseDto;
import com.foss.server.dto.member.MemberInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final NexonApiClient nexonApiClient;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberInfoResponseDto refreshMember(String nickname) {

        String ouid = nexonApiClient.requestUserOuid(nickname);
        UserApiResponseDto userApiResponseDto = nexonApiClient.requestUserInfo(ouid);

        Optional<Member> member = memberRepository.findByAccessId(userApiResponseDto.getOuid());

        //새로운 유저인 경우
        if (!member.isPresent()) {
            Member newMember = Member.builder()
                    .accessId(userApiResponseDto.getOuid())
                    .level(userApiResponseDto.getLevel())
                    .username(userApiResponseDto.getNickname())
                    .renewal(LocalDateTime.now())
                    .build();

            Member saveMember = memberRepository.save(newMember);
            return MemberInfoResponseDto.from(saveMember);
        }

        //기존 유저인 경우
        Member currentMember = member.get();

        currentMember.updateMember(
                userApiResponseDto.getNickname(),
                userApiResponseDto.getLevel(),
                LocalDateTime.now()
        );

        Member updateMember = memberRepository.save(currentMember);
        return MemberInfoResponseDto.from(updateMember);
    }

}
