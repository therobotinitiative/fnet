package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_group")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class Group implements ServiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId;

	private String name;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(31, 27, this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public static Group of(Long groupId) {
		return new Group(groupId, null);
	}

	public static Group of(String name) {
		return new Group(null, name);
	}
}
