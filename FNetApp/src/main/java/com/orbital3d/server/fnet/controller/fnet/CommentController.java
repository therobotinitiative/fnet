package com.orbital3d.server.fnet.controller.fnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Comment;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.CommentService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.UserService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

/**
 * Controller for {@link CommentController} related operations.
 * 
 * @author msren
 *
 */
@RestController
@RequestMapping("/fnet/comment")
public class CommentController {
	/**
	 * DTO class for comment data.
	 * 
	 * @author msiren
	 *
	 */
	// Getters used by framework
	@SuppressWarnings("unused")
	private static final class CommentDTO {
		private String comment;
		private Date timestamp;
		private String userName;
		private Long userId;
		private Long commentId;

		private CommentDTO(String comment, Date timestamp, String userName, Long userId, Long commentId) {
			this.comment = comment;
			this.timestamp = timestamp;
			this.userName = userName;
			this.userId = userId;
			this.commentId = commentId;
		}

		public String getComment() {
			return comment;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public String getUserName() {
			return userName;
		}

		public Long getUserId() {
			return userId;
		}

		public Long getCommentId() {
			return commentId;
		}

		static CommentDTO of(String comment, Date timestamp, String userName, Long userId, Long commentId) {
			return new CommentDTO(comment, timestamp, userName, userId, commentId);
		}
	}

	/**
	 * DTO class for adding comment.
	 * 
	 * @author msiren
	 *
	 */
	// Getters used by framework
	@SuppressWarnings("unused")
	private static class AddCommentDTO {
		private Long parentId;
		private String comment;

		private AddCommentDTO(Long parentId, String comment) {
			this.parentId = parentId;
			this.comment = comment;
		}

		private AddCommentDTO() {
			// Default
		}

		public Long getParentId() {
			return parentId;
		}

		public String getComment() {
			return comment;
		}

		static AddCommentDTO of(Long parentId, String comment) {
			return new AddCommentDTO(parentId, comment);
		}
	}

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	/**
	 * @param parentId {@link CommentController} parent id
	 * @return {@link List} of {@link CommentDTO}s
	 */
	@GetMapping("/{parentId}")
	protected List<CommentDTO> getComments(@PathVariable Long parentId) {
		List<CommentDTO> commentDtos = new ArrayList<>();
		commentService.getByParent(parentId)
				.forEach(comment -> commentDtos.add(CommentDTO.of(comment.getComment(), comment.getTimestamp(),
						userService.getById(comment.getUserId()).get().getUserName(), comment.getUserId(),
						comment.getCommentId())));
		return commentDtos;
	}

	/**
	 * @param addCommentDto {@link CommentDTO} to add
	 * @return Added {@link Comment}
	 */
	@PutMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@RequiresPermission(FnetPermissions.Comment.CREATE)
	protected CommentDTO addComment(@RequestBody AddCommentDTO addCommentDto) {
		Comment comment = Comment.of(null, addCommentDto.getParentId(), sessionService.getCurrentGroup().getGroupId(),
				sessionService.getCurrentUser().getUserId(), addCommentDto.getComment(), new Date());
		comment = commentService.save(comment);
		return CommentDTO.of(comment.getComment(), comment.getTimestamp(),
				userService.getById(comment.getUserId()).get().getUserName(), addCommentDto.getParentId(),
				comment.getCommentId());
	}

	/**
	 * @param commentId Comment id to delete
	 */
	@DeleteMapping("/{commentId}")
	@Transactional
	@RequiresPermission(FnetPermissions.Comment.DELETE)
	protected void delete(@PathVariable("commentId") Long commentId) {
		Comment comment = commentService.getById(commentId).get();
		if (comment.getUserId().equals(sessionService.getCurrentUser().getUserId())
				&& comment.getGroupId().equals(sessionService.getCurrentGroup().getGroupId())) {
			commentService.delete(comment);
		}
	}
}
