package com.orbital3d.server.fnet.service;

import com.orbital3d.server.fnet.database.entity.Comment;
import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.repository.CommentRepository;

/**
 * Comment service.
 * 
 * @author msiren
 *
 */
public interface CommentService extends CrudService<Comment, CommentRepository> {
	/**
	 * @param group {@link Group} to limit the search
	 * @param limit Maximum number of results
	 * @return {@link Iterable} of {@link Comment}s order by time stamp
	 * @throws IllegalArgumentException If group is null or limit is less than 1
	 */
	Iterable<Comment> getLatest(Group group, int limit);

	/**
	 * @param parentId {@link Comment} parent id
	 * @return {@link Comment}s belonging to the parent id
	 */
	Iterable<Comment> getByParent(Long parentId);
}
