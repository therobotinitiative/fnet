package com.orbital3d.server.fnet.controller;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
	@AllArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
	@Getter
	private static final class ItemDTO {
		private Item item;
		private int commentCount;
	}

	/**
	 * DTO class for adding item.
	 * 
	 * @author msiren
	 *
	 */
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	private static final class AddItemDTO {
		private String itemName;
		private ItemType itemType;
		private Long parentId;
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

	@PostMapping(value = "/add")
	@Transactional
	protected Item addItem(@RequestBody AddItemDTO addI) {
		return itemService.save(Item.of(addI.parentId, addI.getItemName(), addI.getItemType(),
				sessionService.getCurrentGroup().getGroupId(), sessionService.getCurrentUser().getUserId()));
	}
}
