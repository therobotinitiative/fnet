package com.orbital3d.server.fnet.controller.fnet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.service.CommentService;
import com.orbital3d.server.fnet.service.UserService;

/**
 * Controller for {@link Comment} related operations.
 * 
 * @author msren
 *
 */
@RestController
@RequestMapping("/fnet/comment")
public class Comment {
	/**
	 * DTO class for xomment data.
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

		private CommentDTO(String comment, Date timestamp, String userName, Long userId) {
			this.comment = comment;
			this.timestamp = timestamp;
			this.userName = userName;
			this.userId = userId;
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

		static CommentDTO of(String comment, Date timestamp, String userName, Long userId) {
			return new CommentDTO(comment, timestamp, userName, userId);
		}
	}

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	/**
	 * @param parentId {@link Comment} parent id
	 * @return {@link List} of {@link CommentDTO}s
	 */
	@GetMapping("/{parentId}")
	protected List<CommentDTO> getComments(@PathVariable Long parentId) {
		List<CommentDTO> commentDtos = new ArrayList<>();
		commentService.getByParent(parentId)
				.forEach(comment -> commentDtos.add(CommentDTO.of(comment.getComment(), comment.getTimestamp(),
						userService.getById(comment.getUserId()).get().getUserName(), comment.getUserId())));
		return commentDtos;
	}
}
