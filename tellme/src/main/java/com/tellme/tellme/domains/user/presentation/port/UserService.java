package com.tellme.tellme.domains.user.presentation.port;

import com.tellme.tellme.domains.user.domain.User;
import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.user.presentation.UserDto;

public interface UserService {
    boolean checkByEmail(String email);
    User getByEmail(String email);
    UserDto.UserInfo getInfo(User user);


}
