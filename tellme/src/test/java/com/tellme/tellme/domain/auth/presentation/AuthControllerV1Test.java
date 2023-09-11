package com.tellme.tellme.domain.auth.presentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tellme.tellme.domain.auth.application.KakaoOauth;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/auth-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class AuthControllerV1Test {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @MockBean
    private KakaoOauth kakaoOauth;

    @Test
    void 소셜_회원가입_및_로그인을_요청할_수_있고_응답으로_토큰값을_내려받을_수_있다() throws Exception {
        // given
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        AuthDto.KakaoOAuthToken kakaoOAuthToken = new AuthDto.KakaoOAuthToken("","","",1,"","");
        AuthDto.KakaoUser kakaoUser = AuthDto.KakaoUser.builder()
                .properties(new AuthDto.properties("nickname3", "http://test-user-picture3.com", "http://test-user-picture1.com"))
                .kakao_account(new AuthDto.kakao_account("email3@naver.com"))
                .build();

        BDDMockito.willReturn(responseEntity).given(kakaoOauth).requestAccessToken(any(String.class));
        BDDMockito.willReturn(kakaoOAuthToken).given(kakaoOauth).getAccessToken(any(ResponseEntity.class));
        BDDMockito.willReturn(responseEntity).given(kakaoOauth).requestUserInfo(any(AuthDto.KakaoOAuthToken.class));
        BDDMockito.willReturn(kakaoUser).given(kakaoOauth).getUserInfo(any(ResponseEntity.class));

        // when
        // then
        mockMvc.perform(get("/v1/auth/KAKAO/login") // FIXME. POST 요청으로 수정
                .param("code", "test-code"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userPicture").value("http://test-user-picture3.com"))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists());
    }
}
