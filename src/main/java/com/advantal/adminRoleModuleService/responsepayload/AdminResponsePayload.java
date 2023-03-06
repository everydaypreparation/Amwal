package com.advantal.adminRoleModuleService.responsepayload;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponsePayload {

	private Long id;

	private String name;

	private String mobile;

	private String email;

	private String password;

	private String entryDate;

	private String updationDate;

	private Short status;

	private RoleResponsePayload roleResponsePayload;

}
