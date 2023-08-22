package com.tellme.tellme.domain.survey.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.enums.UserRole;
import com.tellme.tellme.domain.auth.application.KakaoOauth;
import com.tellme.tellme.domain.survey.application.SurveyService;
import com.tellme.tellme.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/survey-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
class SurveyControllerV1Test {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private JwtTokenProvider jwtTokenProvider;
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
                .id(3)
                .email("taeyoung6873@gmail.com")
                .password("NONE")
                .nickname("김태영")
                .picture("http://k.kakaocdn.net/dn/bomDTm/btso6bKwAAw/NW0E3uMlmIwMFnIKRluapK/img_640x640.jpg")
                .socialType("KAKAO")
                .roles(List.of(UserRole.USER.toString()))
                .build();

        UserDetails userDetails = userDetailsService.loadUserByUsername(Integer.toString(user.getId()));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    @Test
    void 설문질문_리스트_조회() throws Exception {
        // given
        int surveyId = 1;
        // when
        // then
        ResultActions resultActions = mockMvc.perform(get("/v1/survey/questions/" + surveyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].question").value("무더운 여름, 휴일을 보내는 모습은?"))
                .andExpect(jsonPath("$.data[0].answerA").value("시원한 집에서 혼자 넷플릭스"))
                .andExpect(jsonPath("$.data[0].answerB").value("시원한 영화관에서 친구들과 영화"));

    }

    @Test
    void 설문_short_url_로_설문아이디_유저아이디_정보_조회() throws Exception {
        // give
        String shortUrl = "MQ==";

        // when
        // then
        mockMvc.perform(get("/v1/survey/" + shortUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userId").value(4))
                .andExpect(jsonPath("$.data.surveyId").value(1))
                .andExpect(jsonPath("$.data.nickname").value("김태영1"));

    }

    @Test
    void 설문지_작성() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<SurveyDto.AnswerContent> list = new ArrayList<>();
        int i = 1;
        while (i <= 10) {
            SurveyDto.AnswerContent answerContent = SurveyDto.AnswerContent.builder()
                    .question(1)
                    .answer('A')
                    .build();
            list.add(answerContent);
            i++;
        }

        SurveyDto.Answer answer = SurveyDto.Answer.builder()
                .answerContentList(list)
                .build();

        // when
        // then
        mockMvc.perform(post("/v1/survey/1/3/answers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(authentication))
                        .content(objectMapper.writeValueAsString(answer))
                        .requestAttr("javax.servlet.http.HttpServletRequest", mockHttpServletRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.type").value("닥터 스트레인지"))
                .andExpect(jsonPath("$.data.typeNumber").value(5));
    }

}