package com.orbital3d.server.fnet.controller.fnet;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Comment;
import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.service.CommentService;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.SettingsService;
import com.orbital3d.server.fnet.service.UserDataService;
import com.orbital3d.server.fnet.service.UserService;
import com.orbital3d.server.fnet.service.item.SesssionKey;
import com.orbital3d.web.security.weblectricfence.exception.AuthorizationException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Controller for Fnet related operations.
 * 
 * @author msiren
 *
 */
@RestController
@RequestMapping("/fnet")
//@EnableScheduling
public class Fnet {
	/**
	 * DTO class for latest item.
	 * 
	 * @author msiren
	 *
	 */
	@AllArgsConstructor(staticName = "of")
	@Getter
	private final static class LatestItem {
		private String name;
		private String parentName;
		private Long parentId;
		private Date timestamp;
	}

	/**
	 * DTO class for latest comments.
	 * 
	 * @author msiren
	 *
	 */
	@AllArgsConstructor(staticName = "of")
	@Getter
	private final static class LatestComment {
		private String comment;
		private String userName;
		private String itemName;
		private Long itemId;
		private Date timestamp;
	}

	/**
	 * DTO class for latest information.
	 * 
	 * @author msiren
	 *
	 */
	@AllArgsConstructor(staticName = "of")
	@Getter
	private final static class LatestDTO {
		private List<LatestItem> items;
		private int numberOfItems;
		private List<LatestComment> comments;
		private int numberOfComments;
	}

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private SettingsService setttingsService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserDataService userDataService;

	@Autowired
	private UserService userService;

	@NoArgsConstructor
	@AllArgsConstructor(staticName = "of")
	@Getter
	private static class EventDTO {
		private String eventType;
		private String message;
	}

	@AllArgsConstructor(staticName = "of")
	@Getter
	private static class dummy {
		String message;
	}

	@AllArgsConstructor(staticName = "of")
	@Getter
	private static class ActiveGroupDTO {
		private Long rootView;
	}

	/**
	 * @param limit Limit number of items
	 * @return {@link LatestDTO} containing latest items of interest
	 */
	@GetMapping("/latest/{limit}")
	protected LatestDTO getLatest(@PathVariable(required = false) Integer limit) {
		// If limit not set use default
		if (limit == null) {
			limit = setttingsService.latestDefaultLimit();
		}
		// Latest comments
		List<Item> latestItems = (List<Item>) itemService.findLatest(limit,
				new ItemType[] { ItemType.AUDIO, ItemType.FILE, ItemType.IMAGE, ItemType.VIDEO },
				sessionService.getCurrentGroup());
		List<LatestItem> convertedItems = StreamSupport.stream(latestItems.spliterator(), false).map(item -> {
			return LatestItem.of(item.getName(), itemService.findById(item.getParentId()).get().getName(),
					item.getParentId(), item.getTimestamp());
		}).collect(Collectors.toList());
		List<Comment> latestComments = (List<Comment>) commentService.getLatest(sessionService.getCurrentGroup(),
				limit);
		List<LatestComment> convertedCommments = StreamSupport.stream(latestComments.spliterator(), false)
				.map(comment -> {
					return LatestComment.of(comment.getComment(),
							userService.getById(comment.getUserId()).get().getUserName(),
							itemService.findById(comment.getItemId()).get().getName(), comment.getItemId(),
							comment.getTimestamp());
				}).collect(Collectors.toList());
		return LatestDTO.of(convertedItems, calculateNewItems(latestItems), convertedCommments,
				calculateNewComments(latestComments));
	}

	/**
	 * Poll event.
	 * 
	 * @return New {@link FnetEvent} or null if no event comes available in the
	 *         given time period
	 */
	@GetMapping("/event")
	protected dummy pollEvent() {
		// Dummy answer with randomized delay for testing purposes only
		try {
			Thread.sleep((long) ((new Random()).nextFloat() * 10000) + 1000);
		} catch (InterruptedException e) {
			// silent
		}
		return dummy.of("dummy hello");
	}

	/**
	 * Receive event from the client (front end).
	 * 
	 * @param eventDto
	 */
	@PostMapping("/event")
	protected void receiveEvent(@RequestBody EventDTO eventDto) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Change currently active group.
	 * 
	 * @param groupId New active group id
	 * @return Group root id
	 * @throws {@link AuthorizationException} if current user does not belong to
	 *                selected group
	 */
	@PutMapping("/activegroup/{groupId}")
	@Transactional
	protected ActiveGroupDTO changeActiveGroup(@PathVariable Long groupId) {
		Group group = Group.of(groupId);
		if (groupService.isUserInGroup(sessionService.getCurrentUser(), group)) {
			sessionService.setCurrentGroup(group);
			userDataService.updateActiveGroup(sessionService.getCurrentUser(), groupId);
			return ActiveGroupDTO.of(itemService.findRoot(group).getGroupId());
		}
		throw new AuthorizationException("Not in the selected group");
	}

	private int calculateNewItems(List<Item> itemList) {
		Date lastLogin = (Date) sessionService.get(SesssionKey.LAST_LOGIN_TIME);
		int itemCount = 0;
		for (Item item : itemList) {
			if (item.getTimestamp().after(lastLogin)) {
				itemCount++;
			}
		}
		return itemCount;
	}

	private int calculateNewComments(List<Comment> commentList) {
		Date lastLogin = (Date) sessionService.get(SesssionKey.LAST_LOGIN_TIME);
		int commentCount = 0;
		for (Comment comment : commentList) {
			if (comment.getTimestamp().after(lastLogin)) {
				commentCount++;
			}
		}
		return commentCount;
	}
}
