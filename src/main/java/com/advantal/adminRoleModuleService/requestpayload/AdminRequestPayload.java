package com.advantal.adminRoleModuleService.requestpayload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class AdminRequestPayload {
	@NotNull(message = "Id can't be null !!")
	private Long id;
	
	@NotEmpty(message = "Name can't be empty !!")
	private String name;
	
	@NotEmpty(message = "mobile no can't be empty !!")
	private String mobile;
	
	@NotEmpty(message = "email can't be empty !!")
	private String email;
	
	@NotEmpty(message = "password can't be empty !!")
	private String password;
	
	@NotNull(message = "Role Id can't be empty !!")
	private Long roleId;

	

}
