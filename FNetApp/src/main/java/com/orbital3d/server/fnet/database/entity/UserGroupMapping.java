package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.database.entity.composite.UserGroupId;
import com.orbital3d.server.fnet.service.item.ServiceItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_group_mapping")
@IdClass(UserGroupId.class)
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class UserGroupMapping implements ServiceItem {
	@Id
	private Long userId;

	@Id
	private Long groupId;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(3, 7, this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
