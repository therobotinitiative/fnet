package com.orbital3d.server.fnet.database.entity;

import java.util.Date;

import javax.persistence.Entity;
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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class Comment implements ServiceItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@NotNull
	private Long itemId;

	@NotNull
	private Long groupId;

	@NotNull
	private Long userId;

	@NotNull
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(7, 9, this);
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
