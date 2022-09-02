package com.orbital3d.server.fnet.database.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;

public interface ItemRepository extends CrudRepository<Item, Long> {
	Optional<Item> findByItemId(Long itemId);

	// TODO: might require query annotation!
//	Optional<Item> findParentItem(Long itemId);

	List<Item> findByParentIdAndGroupIdOrderByName(Long parentId, Long groupId);

	Optional<Item> findByItemIdAndGroupId(Long itemId, Long groupId);

	List<Item> findTop5ByGroupIdAndItemTypeNotOrderByTimestampDesc(Long groupId, String itemType);

	@Query("SELECT name FROM Item ie WHERE ie.itemId=:itemId AND ie.groupId=:groupId")
	String findNameByItemIdAndGroupId(@Param("itemId") Long itemId, @Param("groupId") Long groupId);

	void deleteByItemIdAndGroupId(Long itemId, Long groupId);

	@Query("FROM Item ie WHERE ie.groupId=:groupId AND ie.itemType IN :itemTypes ORDER BY ie.timestamp DESC")
	List<Item> findLatestLimited(@Param("groupId") Long groupId, @Param("itemTypes") ItemType[] itemTypes,
			Pageable pageable);

	@Query("FROM Item it WHERE it.groupId=:groupId AND it.itemType='ROOT'")
	Optional<Item> findRoot(@Param("groupId") Long groupId);
}
