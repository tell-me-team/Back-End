package com.tellme.tellme.domains.user.application;


import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.enums.UserRole;
import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.domains.auth.application.KakaoOauth;
import com.tellme.tellme.domains.user.domain.User;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.user.presentation.UserDto.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;



import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserEntityServiceTest {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private KakaoOauth kakaoOauth;

    @Test
    void 주어진_email_에_해당하는_유저가_있는지_판별할_수_있다() {
        //given
        String existEmail = "email1@naver.com";
        String unExistEmail = "email3@naver.com";

        //when
        boolean result1 = userServiceImpl.checkByEmail(existEmail);
        boolean result2 = userServiceImpl.checkByEmail(unExistEmail);

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void 주어진_email_에_해당하는_유저를_조회할_수_있다() {
        //given
        String email = "email1@naver.com";

        //when
        User result = userServiceImpl.getByEmail(email);

        //then
        assertThat(result.getEmail()).isEqualTo("email1@naver.com");
        assertThat(result.getNickname()).isEqualTo("nickname1");
    }

    @Test
    void 주어진_email_에_해당하는_유저가_없다면_에러를_던진다() {
        //given
        String email = "email3@naver.com";

        //when
        //then
        assertThatThrownBy(() -> {
            User result = userServiceImpl.getByEmail(email);
        }).isInstanceOf(BaseException.class);
    }


    @Test
    void 내_유저_정보를_조회할_수_있다() {
        //given
        User user = User.builder()
                .id(1)
                .email("email1@naver.com")
                .password("NONE")
                .nickname("nickname1")
                .picture("http://test-user-picture1.com")
                .socialType("kakao")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        //when
        UserInfo result = userServiceImpl.getInfo(user);

        //then
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getProfileImage()).isEqualTo("http://test-user-picture1.com");
        assertThat(result.getMyCompleteSurveyList().size()).isEqualTo(0);
    }
}
