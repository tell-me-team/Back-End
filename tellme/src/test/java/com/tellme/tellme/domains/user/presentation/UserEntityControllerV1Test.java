package com.tellme.tellme.domains.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.enums.UserRole;
import com.tellme.tellme.domains.auth.application.KakaoOauth;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserEntityControllerV1Test {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private KakaoOauth kakaoOauth;



    @BeforeEach
    void setUp() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .email("email1@naver.com")
                .password("NONE")
                .nickname("nickname1")
                .picture("http://test-user-picture1.com")
                .socialType("kakao")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(Integer.toString(userEntity.getId()));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    @Test
    void 사용자는_내_유저_정보를_조회할_수_있다() throws Exception {
        //given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //when
        //then
        mockMvc.perform(get("/v1/users/info")
                        .content(objectMapper.writeValueAsString(authentication)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.profileImage").value("http://test-user-picture1.com"));


    }

}
