package com.orbital3d.server.fnet.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.database.repository.ItemRepository;
import com.orbital3d.server.fnet.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public ItemRepository getRepository() {
		return itemRepository;
	}

	@Override
	public Item createRoot(Group groupEntity, Long userId) {
		return itemRepository.save(Item.of(null, Long.valueOf(0), "ROOT", Item.ItemType.ROOT, new Date(),
				groupEntity.getGroupId(), userId));
	}

	@Override
	public Iterable<Item> findLatest(int limit, ItemType[] itemTypes, Group group) {
		if (itemTypes == null || itemTypes.length == 0) {
			throw new IllegalArgumentException("item types must be set");
		}
		if (limit < 1) {
			throw new IllegalArgumentException("limit must be greater than 0");
		}
		return itemRepository.findLatestLimited(group.getGroupId(), itemTypes, Pageable.ofSize(limit));
	}

	@Override
	public Item findRoot(Group group) {
		return itemRepository.findRoot(group.getGroupId()).get();
	}

	@Override
	public Iterable<Item> findByParent(Item parent, Group group) {
		if (parent == null) {
			parent = itemRepository.findRoot(group.getGroupId()).get();
		}
		return itemRepository.findByParentIdAndGroupIdOrderByName(parent.getItemId(), group.getGroupId());
	}

	@Override
	public Optional<Item> findById(Long itemId) {
		return itemRepository.findById(itemId);
	}
}
