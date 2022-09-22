package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vfs")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class VFSEntity implements ServiceItem {
	@Id
	@NotNull
	private Long parentId;

	@NotNull
	private String virtualName;

	@NotNull
	private String originalName;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(77, 43, this);
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
