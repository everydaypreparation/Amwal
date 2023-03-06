package com.advantal.adminRoleModuleService.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String mobile;

	private String email;

	private String password;

	private Date entryDate;

	private Date updationDate;

	private Short status;
	
//	private Long role_id_fk;

	@OneToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id_fk", referencedColumnName = "id")
	private Role role;

}
