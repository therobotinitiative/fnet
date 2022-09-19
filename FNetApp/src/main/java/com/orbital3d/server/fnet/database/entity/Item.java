package com.orbital3d.server.fnet.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;

@Entity
@Table(name = "item")
public class Item implements ServiceItem {
	public static enum ItemType {
		ROOT, FOLDER, FILE, AUDIO, IMAGE, VIDEO,
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;

	@NotNull
	private Long parentId;

	@NotNull
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ItemType itemType;

	@Column(name = "timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@NotNull
	private Long groupId;

	@NotNull
	private Long userId;

	protected Item() {
		// For JPA
	}

	private Item(Long itemId, Long parentId, String name, ItemType itemType, Date timestamp, Long groupId,
			Long userId) {
		super();
		this.itemId = itemId;
		this.parentId = parentId;
		this.name = name;
		this.itemType = itemType;
		this.timestamp = timestamp;
		this.groupId = groupId;
		this.userId = userId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(357, 63, this, false);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static Item of(Long itemId, Long parentId, String name, ItemType itemType, Date timestamp, Long groupId,
			Long userId) {
		return new Item(itemId, parentId, name, itemType, timestamp, groupId, userId);
	}

	public static Item of(Long parentId, String name, ItemType itemType, Long groupId, Long userId) {
		return new Item(null, parentId, name, itemType, new Date(), groupId, userId);
	}
}
