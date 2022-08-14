package com.orbital3d.server.fnet.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.orbital3d.server.fnet.database.entity.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	Optional<User> findByUserId(Long userId);

	Optional<User> findByUserName(String userName);

	Optional<String> findUserNameByUserId(Long serId);
}
