package com.tellme.tellme.domain.user.infrastructure;

import com.tellme.tellme.domain.user.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
public class UserEntityJpaRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    void findByEmailAndDeletedIsNull_로_유저_데이터를_찾아올_수_있다() {
        //given
        String email = "email1@naver.com";

        //when
        UserEntity result = userEntityRepository.findByEmailAndDeletedIsNull(email).get();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("email1@naver.com");
        assertThat(result.getDeleted()).isNull();
    }

    @Test
    void findByEmailAndDeletedIsNull_로_deleted_가_null_이_아닌_유저_데이터를_찾아올_수_없다() {
        //given
        String email = "email2@naver.com";

        //when
        Optional<UserEntity> result = userEntityRepository.findByEmailAndDeletedIsNull(email);

        //then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void findByIdAndDeletedIsNull_로_유저_데이터를_찾아올_수_있다() {
        //given
        int id = 1;

        //when
        UserEntity result = userEntityRepository.findByIdAndDeletedIsNull(id).get();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getDeleted()).isNull();
    }

    @Test
    void findByIdAndDeletedIsNull_로_deleted_가_null_이_아닌_유저_데이터를_찾아올_수_없다() {
        //given
        int id = 2;

        //when
        Optional<UserEntity> result = userEntityRepository.findByIdAndDeletedIsNull(id);

        //then
        assertThat(result.isPresent()).isFalse();
    }

}
