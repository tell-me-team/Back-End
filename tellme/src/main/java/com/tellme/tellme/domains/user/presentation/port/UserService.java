package com.tellme.tellme.domains.user.presentation.port;

import com.tellme.tellme.domains.user.infrastructure.UserEntity;
import com.tellme.tellme.domains.user.presentation.UserDto;

public interface UserService {
    boolean checkByEmail(String email);
    UserEntity getByEmail(String email);
    UserDto.UserInfo getInfo(UserEntity userEntity);


}
