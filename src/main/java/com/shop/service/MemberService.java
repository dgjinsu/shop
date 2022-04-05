package com.shop.service;


import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  //로직을 처리하다가 에러가 발생하였다면, 변겨된 데이터를 로직을 수행하기 이전으로 콜백
public class MemberService implements UserDetailsService {
    // UserDetailService 인터페이스 : DB 에서 회원 정보를 가져오는 역할 담당
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        Member save = memberRepository.save(member);
        return save;
    }

    // boolean existMember = memberRepository.existsById(request.getEmail());
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    //사용자의 정보와 권한을 갖는 UserDetails 인터페이스를 반환한다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }
        return User.builder().username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
