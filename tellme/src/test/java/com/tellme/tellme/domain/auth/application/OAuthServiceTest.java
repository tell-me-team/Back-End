package com.tellme.tellme.domain.auth.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tellme.tellme.common.enums.SocialLoginType;
import com.tellme.tellme.domain.auth.presentation.AuthDto;
import com.tellme.tellme.domain.auth.presentation.AuthDto.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/auth-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class OAuthServiceTest {

    @Autowired
    private OAuthService oAuthService;

    @MockBean
    private KakaoOauth kakaoOauth;

    @Test
    void 이미_가입한_유저는_OAuth2_방법으로_로그인을_할_수_있다() throws Exception {
        // given
        SocialLoginType socialLoginType = SocialLoginType.KAKAO;
        String code = "test-access-code";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        KakaoOAuthToken kakaoOAuthToken = new KakaoOAuthToken("","","",1,"","");
        KakaoUser kakaoUser = KakaoUser.builder()
                .properties(new properties("nickname1", "http://test-user-picture1.com", "http://test-user-picture1.com"))
                .kakao_account(new kakao_account("email1@naver.com"))
                .build();

        BDDMockito.willReturn(responseEntity).given(kakaoOauth).requestAccessToken(any(String.class));
        BDDMockito.willReturn(kakaoOAuthToken).given(kakaoOauth).getAccessToken(any(ResponseEntity.class));
        BDDMockito.willReturn(responseEntity).given(kakaoOauth).requestUserInfo(any(KakaoOAuthToken.class));
        BDDMockito.willReturn(kakaoUser).given(kakaoOauth).getUserInfo(any(ResponseEntity.class));

        // when
        GetSocialOAuthRes result = oAuthService.oAuthLoginOrJoin(socialLoginType, code);

        // then
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getUserPicture()).isEqualTo("http://test-user-picture1.com");
        assertThat(result.getRefreshToken()).isNotNull();
        assertThat(result.getAccessToken()).isNotNull();
    }

    @Test
    void 가입하지_않은_유저는_OAuth2_방법으로_회원가입_및_로그인을_할_수_있다() throws Exception {
        // given
        SocialLoginType socialLoginType = SocialLoginType.KAKAO;
        String code = "test-access-code";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        KakaoOAuthToken kakaoOAuthToken = new KakaoOAuthToken("","","",1,"","");
        KakaoUser kakaoUser = KakaoUser.builder()
                .properties(new properties("nickname3", "http://test-user-picture3.com", "http://test-user-picture3.com"))
                .kakao_account(new kakao_account("email3@naver.com"))
                .build();

        BDDMockito.willReturn(responseEntity).given(kakaoOauth).requestAccessToken(any(String.class));
        BDDMockito.willReturn(kakaoOAuthToken).given(kakaoOauth).getAccessToken(any(ResponseEntity.class));
        BDDMockito.willReturn(responseEntity).given(kakaoOauth).requestUserInfo(any(KakaoOAuthToken.class));
        BDDMockito.willReturn(kakaoUser).given(kakaoOauth).getUserInfo(any(ResponseEntity.class));

        // when
        GetSocialOAuthRes result = oAuthService.oAuthLoginOrJoin(socialLoginType, code);

        // then
        assertThat(result.getUserId()).isEqualTo(3);
        assertThat(result.getUserPicture()).isEqualTo("http://test-user-picture3.com");
        assertThat(result.getRefreshToken()).isNotNull();
        assertThat(result.getAccessToken()).isNotNull();
    }
}
