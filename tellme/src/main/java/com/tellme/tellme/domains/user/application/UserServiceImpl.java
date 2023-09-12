package com.tellme.tellme.domains.user.application;

import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.common.exception.ErrorStatus;
import com.tellme.tellme.domains.survey.entity.SurveyCompletion;
import com.tellme.tellme.domains.user.application.port.UserRepository;
import com.tellme.tellme.domains.user.domain.User;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.user.presentation.port.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tellme.tellme.domains.user.presentation.UserDto.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean checkByEmail(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent()) return true;
        return false;
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BaseException(ErrorStatus.RESOURCE_NOT_VALID));
    }

    @Transactional(readOnly = true)
    public UserInfo getInfo(User user) {
        List<SurveyCompletion> userSurveyCompletionList = userRepository.findByUniqueId(Integer.toString(user.getId()));
        List<Integer> userCompleteSurveyIdList = userSurveyCompletionList.stream().map(s -> s.getSurvey().getId()).collect(Collectors.toList());

        return UserInfo.builder()
                .userId(user.getId())
                .profileImage(user.getPicture())
                .MyCompleteSurveyList(userCompleteSurveyIdList)
                .build();
    }
}
