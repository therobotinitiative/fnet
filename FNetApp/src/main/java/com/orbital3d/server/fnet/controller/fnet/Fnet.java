package com.orbital3d.server.fnet.controller.fnet;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Comment;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.service.CommentService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.SettingsService;

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
	 * DTO class for latest information.
	 * 
	 * @author msiren
	 *
	 */
	@Getter
	private final static class LatestDTO {
		private List<Item> items;
		private int numberOfItems;
		private List<Comment> comments;
		private int numberOfComments;

		private LatestDTO(List<Item> items, List<Comment> comments) {
			this.items = items;
			this.numberOfItems = items.size();
			this.comments = comments;
			this.numberOfComments = comments.size();
		}

		/**
		 * @param items    {@link List} of {@link Items}s
		 * @param comments {@link List} of {@link Comment}s
		 * @return New instance
		 * @throws IllegalArgumentException If items or comments are null
		 */
		private static LatestDTO of(List<Item> items, List<Comment> comments) {
			if (items == null || comments == null) {
				throw new IllegalArgumentException("Parameter must not be null");
			}
			return new LatestDTO(items, comments);
		}
	}

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private SettingsService setttingsService;

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
		return LatestDTO.of(
				(List<Item>) itemService.findLatest(limit,
						new ItemType[] { ItemType.AUDIO, ItemType.FILE, ItemType.IMAGE, ItemType.VIDEO },
						sessionService.getCurrentGroup()),
				(List<Comment>) commentService.getLatest(sessionService.getCurrentGroup(), limit));
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
}
