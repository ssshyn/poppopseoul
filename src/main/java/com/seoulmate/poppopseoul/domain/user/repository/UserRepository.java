package com.seoulmate.poppopseoul.domain.user.repository;

import com.seoulmate.poppopseoul.common.enumeration.LoginType;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndLoginType(String email, LoginType loginType);
    Optional<User> findByNickname(String nickname);
}
