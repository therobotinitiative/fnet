package com.orbital3d.server.fnet.controller;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	 * DTO class for transferring items and the parent name.
	 * 
	 * @author msiren
	 *
	 */
	@AllArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
	@Getter
	private static final class ItemParentDTO {
		private String name;
		List<ItemDTO> items;
	}

	/**
	 * DTO class for adding item.
	 * 
	 * @author msiren
	 *
	 */
	@NoArgsConstructor
	@Getter
	private static final class AddItemDTO {
		private String itemName;
		private Long parentId;
	}

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ItemService itemService;

	/**
	 * @param parentId {@link Item}s parent id
	 * @return {@link ItemParentDTO}
	 */
	@GetMapping("/{parentId}")
	protected ItemParentDTO getItems(@PathVariable(required = false) Long parentId) {
		if (parentId == null) {
			parentId = itemService.findRoot(sessionService.getCurrentGroup()).getItemId();
		}
		List<ItemDTO> itemDtos = new LinkedList<>();
		Item parent = itemService.getById(parentId).get();
		itemService.findByParent(parent, sessionService.getCurrentGroup()).forEach(item -> {
			itemDtos.add(ItemDTO.of(item, 0));
		});
		return ItemParentDTO.of(parent.getName(), itemDtos);
	}

	@PostMapping(value = "/folder")
	@Transactional
	@RequiresPermission(FnetPermissions.Folder.CREATE)
	protected ItemDTO addItem(@RequestBody AddItemDTO addI) {
		return ItemDTO.of(
				itemService.save(Item.of(addI.parentId, addI.getItemName(), ItemType.FOLDER,
						sessionService.getCurrentGroup().getGroupId(), sessionService.getCurrentUser().getUserId())),
				0);
	}

	@DeleteMapping("/{itemId}")
	@Transactional
	@RequiresPermission(FnetPermissions.Folder.DELETE)
	protected void deleteFolder(@PathVariable Long itemId) {
		throw new UnsupportedOperationException("folder deletion not supported yet");
	}
}
