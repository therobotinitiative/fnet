package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.orbital3d.server.fnet.database.entity.composite.PermissionId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permission")
@IdClass(PermissionId.class)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC, staticName = "of")
@Getter
public class PermissionEntity {
	@Id
	private Long userId;

	@Id
	private String permission;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(137, 13, this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(obj, this, false);
	}
}
