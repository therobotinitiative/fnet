package com.orbital3d.server.fnet.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.repository.ItemRepository;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private SessionService sessionService;

	@Override
	public ItemRepository getRepository() {
		return itemRepository;
	}

	@Override
	public Item createRoot(Group groupEntity) {
		return itemRepository.save(Item.of(null, Long.valueOf(0), "ROOT", Item.ItemType.ROOT, new Date(),
				sessionService.getCurrentGroup().getGroupId(), sessionService.getCurrentUser().getUserId()));
	}

}
