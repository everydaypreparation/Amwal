package com.advantal.adminRoleModuleService.service;

import java.util.Map;

import com.advantal.adminRoleModuleService.requestpayload.RoleBlockRequestPayload;
import com.advantal.adminRoleModuleService.requestpayload.RoleRequestPayload;

public interface RoleService {

	Map<String, Object> addRole(RoleRequestPayload roleRequestPayload);

	Map<String, Object> getAllRole(Integer pageIndex, Integer pageSize, String searchText);

	Map<String, Object> blockRole(RoleBlockRequestPayload roleBlockRequestPayload);	


}
