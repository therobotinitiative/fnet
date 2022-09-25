package com.orbital3d.server.fnet.database.repository;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.orbital3d.server.fnet.database.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findAllByItemIdAndGroupId(Long itemId, Long groupId);

	List<Comment> findAllByItemIdAndGroupIdOrderByTimestampDesc(Long itemId, Long groupId);

	@Transactional(TxType.SUPPORTS)
	void deleteByCommentIdAndGroupId(Long commentId, Long groupId);

	int countByItemIdAndGroupId(Long itemId, Long groupId);

	List<Comment> findTop5ByGroupIdOrderByTimestampDesc(Long groupId);

	@Query("SELECT itemId FROM Comment ce WHERE ce.commentId=:commentId")
	Long findItemIdByCommentId(@Param("commentId") Long commentId);

	@Query("FROM Comment ce WHERE ce.groupId=:groupId ORDER BY ce.timestamp DESC")
	List<Comment> findLatestLimited(@Param("groupId") Long groupId, Pageable pageable);

	Iterable<Comment> findByItemIdOrderByTimestampDesc(Long parentId);
}
