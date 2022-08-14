package com.orbital3d.server.fnet.database.repository;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.orbital3d.server.fnet.database.entity.UserData;

public interface UserDataRepository extends CrudRepository<UserData, Long> {
	/**
	 * Update last login time and last login IP.
	 * 
	 * @param userId      User id
	 * @param lastLoginIp Last login IP
	 */
	@Transactional(TxType.SUPPORTS)
	@Modifying
	@Query("UPDATE UserData ud SET ud.lastLogin=current_timestamp, ud.lastLoginIp=:lastLoginIp WHERE ud.userId=:userId")
	void updateLastLogin(@Param("userId") Long userId, @Param("lastLoginIp") String lastLoginIp);

	@Transactional(TxType.SUPPORTS)
	@Modifying
	@Query("UPDATE UserData ud SET ud.lastActiveGroup=:lastActiveGroup WHERE ud.userId=:userId")
	void updateLastActiveGroup(@Param("userId") Long userId, @Param("lastActiveGroup") Long lastActiveGroup);

	@Transactional(TxType.SUPPORTS)
	@Modifying
	@Query("UPDATE UserData ud SET ud.passwordChanged=current_timestamp WHERE ud.userId=:userId")
	void updatePasswordChanged(@Param("userId") Long userId);

	Optional<UserData> findByUserId(Long userId);

}
