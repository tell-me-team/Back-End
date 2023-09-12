package com.tellme.tellme;

import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.domains.auth.application.KakaoOauth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TellmeApplicationTests {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private KakaoOauth kakaoOauth;

	@Test
	void contextLoads() {
	}

}
