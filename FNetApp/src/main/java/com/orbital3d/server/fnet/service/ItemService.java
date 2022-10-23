package com.orbital3d.server.fnet.service;

import java.util.Optional;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.database.repository.ItemRepository;

public interface ItemService extends CrudService<Item, ItemRepository> {
	/**
	 * @param groupEntity {@link GroupEntity} for which the root folder is created
	 * @param userId      User id of the root folder creator
	 * @return Root folder {@link Item}
	 */
	Item createRoot(Group groupEntity, Long userId);

	Item findRoot(Group group);

	Optional<Item> findById(Long itemId);

	/**
	 * @param limit     Number of latest items
	 * @param itemTypes Array of item types
	 * @param group     Group to limit the search
	 * @return {@link List} of latest {@link Item}s
	 * @throws IllegalArgumentException If item type array is null or empty; if the
	 *                                  amount is 0 or negative
	 */
	Iterable<Item> findLatest(int limit, ItemType[] itemTypes, Group group);

	Iterable<Item> findByParent(Item parent, Group group);
}
