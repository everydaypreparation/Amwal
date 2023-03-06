package com.advantal.adminRoleModuleService.responsepayload;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
public class RoleModuleMappingResponse{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long role_id_fk;
	
	private Long moduleId;
		
	private String moduleName;
	
	private String parentModuleName;
	
	private String moduleCode;

	private Short addAction;

	private Short updateAction;

	private Short deleteAction;

	private Short viewAction;
	
	private Short downloadAction;
	
	private String entryDate;
	
	private String updationDate;
	
	private Short status;

}
