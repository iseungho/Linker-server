package org.zerock.apiserver.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.apiserver.service.MemberService;

@Component
public class MemberInitializer implements CommandLineRunner {
    private final MemberService memberService;

    public MemberInitializer(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void run(String... args) throws Exception {
        memberService.initializeDefaultMember();
    }
}
