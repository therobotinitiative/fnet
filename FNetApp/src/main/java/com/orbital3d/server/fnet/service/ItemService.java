package com.orbital3d.server.fnet.service;

import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.repository.ItemRepository;

public interface ItemService extends CrudService<Item, ItemRepository> {
	Item createRoot(Group groupEntity);
}
