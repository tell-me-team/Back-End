package com.tellme.tellme.domains.survey.presentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tellme.tellme.common.enums.UserRole;
import com.tellme.tellme.domains.auth.application.KakaoOauth;
import com.tellme.tellme.domains.survey.application.SurveyService;
import com.tellme.tellme.domains.user.domain.User;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/survey-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserEntitySurveyControllerV1Test {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockBean
    private KakaoOauth kakaoOauth;
    @Autowired
    private MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private UserDetailsService userDetailsService;


    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(4)
                .email("email2@naver.com")
                .password("NONE")
                .nickname("김태영1")
                .picture("http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg")
                .socialType("KAKAO")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(Integer.toString(user.getId()));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    @Test
    void 설문_결과_조회() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // when
        // then
        mockMvc.perform(
                get("/v1/users/survey-results/4/1")
                        .content(objectMapper.writeValueAsString(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.nickname").value("김태영1"))
                .andExpect(jsonPath("$.data.type").exists())
                .andExpect(jsonPath("$.data.selfKeywords").exists());

    }


}
