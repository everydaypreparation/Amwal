package com.advantal.adminRoleModuleService.service;

import java.util.Map;



import com.advantal.adminRoleModuleService.requestpayload.AdminBlockRequestPayload;
import com.advantal.adminRoleModuleService.requestpayload.AdminRequestPayload;

public interface AdminService {

	Map<String, Object> addAdmin(AdminRequestPayload adminRequestPayload);

	Map<String, Object> adminLogin(String email, String password);


	Map<String, Object> changePassword(String email, String oldPassword, String password);

	Map<String, Object> getAllAdmin(Integer pageIndex, Integer pageSize, String searchText);

	Map<String, Object> blockAdmin(AdminBlockRequestPayload adminBlockRequestPayload);

	Map<String, Object> deleteAdmin(Long id);

	
}
