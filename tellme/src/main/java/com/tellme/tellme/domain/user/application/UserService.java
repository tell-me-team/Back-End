package com.tellme.tellme.domain.user.application;

import com.tellme.tellme.common.exception.BaseException;
import com.tellme.tellme.common.exception.ErrorStatus;
import com.tellme.tellme.domain.survey.entity.SurveyCompletion;
import com.tellme.tellme.domain.user.application.port.UserRepository;
import com.tellme.tellme.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public boolean checkByEmail(String email) {
        Optional<UserEntity> result = userRepository.findByEmail(email);
        if (result.isPresent()) return true;
        return false;
    }

    @Transactional(readOnly = true)
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BaseException(ErrorStatus.RESOURCE_NOT_VALID));
    }

    @Transactional(readOnly = true)
    public UserInfo getInfo(UserEntity userEntity) {
        List<SurveyCompletion> userSurveyCompletionList = userRepository.findByUniqueId(Integer.toString(userEntity.getId()));
        List<Integer> userCompleteSurveyIdList = userSurveyCompletionList.stream().map(s -> s.getSurvey().getId()).collect(Collectors.toList());

        return UserInfo.builder()
                .userId(userEntity.getId())
                .profileImage(userEntity.getPicture())
                .MyCompleteSurveyList(userCompleteSurveyIdList)
                .build();
    }
}
