package com.springinaction.txexample;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.*;
import static javax.persistence.GenerationType.*;
import static org.apache.commons.lang.builder.EqualsBuilder.*;
import static org.apache.commons.lang.builder.HashCodeBuilder.*;
import static org.apache.commons.lang.builder.ToStringBuilder.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="spitter")
@XmlRootElement
public class Spitter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@Size(min=3, max=20, message="Username must be between 3 and 20 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="Username must be alphanumber with no spaces")
	private String username;
	
	@Size(min=6, max=20, message="Password must be between 6 and 20 characters long.")
	private String password;

	@Size(min=3, max=50, message="Fullname must be between 3 and 50 characters long.")
	private String fullName;
	
	@Pattern(regexp="[a-zA-Z0-9._+-%]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", message="Invalid email address")
	private String email;
	
	private boolean updateByEmail;
	
	@Id
	@GeneratedValue(strategy=AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="username", unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="fullname")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="update_by_email")
	public boolean isUpdateByEmail() {
		return updateByEmail;
	}

	public void setUpdateByEmail(boolean updateByEmail) {
		this.updateByEmail = updateByEmail;
	}

	@Transient
	@JsonIgnore
	public List<Spittle> recentSpittles() {
		Spittle spittle = new Spittle();
		spittle.setId(999L);;
		spittle.setSpitter(this);
		spittle.setText("TEST SPITTLE 99#");
		spittle.setWhen(new Date());
		return asList(spittle);
	}

	@Override
	public boolean equals(Object obj) {
			return reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
			return reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return reflectionToString(this);
	}
}
