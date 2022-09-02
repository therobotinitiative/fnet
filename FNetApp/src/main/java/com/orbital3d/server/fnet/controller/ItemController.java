package com.orbital3d.server.fnet.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;

/**
 * Controller for {@link Item} related operations.
 * 
 * @author msiren
 *
 */
@RestController
@RequestMapping("/fnet/item")
public class ItemController {
	/**
	 * DTO class for {@link Item} and related information.
	 * 
	 * @author msiren
	 *
	 */
	// Getters used by framework
	@SuppressWarnings("unused")
	private static final class ItemDTO {
		private Item item;
		private int commentCount;

		private ItemDTO(Item item, int commentCount) {
			this.item = item;
			this.commentCount = commentCount;
		}

		public Item getItem() {
			return item;
		}

		public int getCommentCount() {
			return commentCount;
		}

		static ItemDTO of(Item item, int commentCount) {
			return new ItemDTO(item, commentCount);
		}
	}

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ItemService itemService;

	/**
	 * @param parentId {@link Item}s parent id
	 * @return {@link List} of {@link ItemDTO}s
	 */
	@GetMapping("/{parentId}")
	protected Iterable<ItemDTO> getItems(@PathVariable(required = false) Long parentId) {
		if (parentId == null) {
			parentId = itemService.findRoot(sessionService.getCurrentGroup()).getItemId();
		}
		List<ItemDTO> itemDtos = new LinkedList<>();
		itemService.findByParent(itemService.getById(parentId).get(), sessionService.getCurrentGroup())
				.forEach(item -> {
					itemDtos.add(ItemDTO.of(item, 0));
				});
		return itemDtos;
	}
}
