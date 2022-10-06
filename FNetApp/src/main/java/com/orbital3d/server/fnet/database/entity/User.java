package com.orbital3d.server.fnet.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.orbital3d.server.fnet.service.item.ServiceItem;
import com.orbital3d.web.security.weblectricfence.type.UserIdentity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class User implements ServiceItem, UserIdentity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotNull
	private String userName;

	@NotNull
	private String password;

	@NotNull
	private String salt;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(197, 37, this, false);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

	@Override
	public String toString() {
		return "[" + this.userId + "](" + this.userName + ")";
	}

	public static User of(String userName, String password, String salt) {
		return new User(null, userName, password, salt);
	}

	public static User of(Long userId) {
		return new User(userId, null, null, null);
	}
}
