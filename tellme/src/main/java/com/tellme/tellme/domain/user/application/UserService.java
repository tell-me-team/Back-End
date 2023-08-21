package com.tellme.tellme.domain.user.application;

//import com.tellme.tellme.common.auth.JwtTokenProvider;
import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.common.exception.ErrorStatus;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.survey.persistence.SurveyCompletionRepository;
import com.tellme.tellme.domain.user.entity.User;
import com.tellme.tellme.domain.user.persistence.UserRepository;
import com.tellme.tellme.domain.user.presentation.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tellme.tellme.domain.user.presentation.UserDto.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SurveyCompletionRepository surveyCompletionRepository;

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> result = userRepository.findByEmailAndDeletedIsNull(email);
        if (result.isPresent()) return true;
        return false;
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedIsNull(email).orElseThrow(() -> new BaseException(ErrorStatus.RESOURCE_NOT_VALID));
    }

    @Transactional(readOnly = true)
    public UserInfo info(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        List<SurveyCompletion> userSurveyCompletionList = surveyCompletionRepository.findByUniqueId(Integer.toString(user.getId()));
        List<Integer> userCompleteSurveyIdList = userSurveyCompletionList.stream().map(s -> s.getSurvey().getId()).collect(Collectors.toList());

        return UserInfo.builder()
                .userId(user.getId())
                .profileImage(user.getPicture())
                .MyCompleteSurveyList(userCompleteSurveyIdList)
                .build();
    }
}
