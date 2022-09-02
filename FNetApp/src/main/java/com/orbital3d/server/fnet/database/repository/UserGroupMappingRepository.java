package com.orbital3d.server.fnet.database.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.orbital3d.server.fnet.database.entity.UserGroupMapping;

public interface UserGroupMappingRepository extends CrudRepository<UserGroupMapping, Long> {
	List<UserGroupMapping> findByUserId(Long userId);

	@Transactional
	void deleteByUserId(Long userId);

	UserGroupMapping userIdAndGroupId(Long userId, Long groupId);

	@Query("SELECT CASE WHEN(COUNT(*) > 0) THEN TRUE ELSE FALSE END FROM UserGroupMapping ugm WHERE ugm.userId=:userId AND ugm.groupId=:groupId")
	boolean userInGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

	List<UserGroupMapping> findAllGroupIdByUserId(Long userId);
}
