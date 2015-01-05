package com.springinaction.txexample;

import static javax.persistence.GenerationType.*;
import static org.apache.commons.lang.builder.EqualsBuilder.*;
import static org.apache.commons.lang.builder.HashCodeBuilder.*;
import static org.apache.commons.lang.builder.ToStringBuilder.*;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="spittle")
public class Spittle implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Spittle() {
		this.spitter = new Spitter();
		this.spitter.setId(1L);
	}

	private long id;
	private Spitter spitter;
	private String text;
	@DateTimeFormat(pattern="hh:mm MM DD, yyyy")
	private Date when;

	@Id
	@GeneratedValue(strategy=AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="spitter_id")
	public Spitter getSpitter() {
		return spitter;
	}

	public void setSpitter(Spitter spitter) {
		this.spitter = spitter;
	}

	@Column(name="spittleText")
	@NotNull
	@Size(min=1, max=140)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name="postedTime")
	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
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
