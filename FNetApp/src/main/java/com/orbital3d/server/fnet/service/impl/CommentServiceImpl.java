package com.orbital3d.server.fnet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Comment;
import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.repository.CommentRepository;
import com.orbital3d.server.fnet.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public CommentRepository getRepository() {
		return commentRepository;
	}

	@Override
	public Iterable<Comment> getLatest(Group group, int limit) {
		if (limit < 1) {
			throw new IllegalArgumentException("limit must be greater than 0");
		}
		if (group == null) {
			throw new IllegalArgumentException("group must be set");
		}
		return commentRepository.findLatestLimited(group.getGroupId(), Pageable.ofSize(limit));
	}

	@Override
	public Iterable<Comment> getByParent(Long parentId) {
		return commentRepository.findByItemId(parentId);
	}

}
