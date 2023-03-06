package com.advantal.adminRoleModuleService.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advantal.adminRoleModuleService.requestpayload.AdminBlockRequestPayload;
import com.advantal.adminRoleModuleService.requestpayload.AdminRequestPayload;
import com.advantal.adminRoleModuleService.service.AdminService;
import com.advantal.adminRoleModuleService.utils.Constant;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/add_admin")
	@ApiOperation(value = "We can Add and update admin details !!")
	public Map<String, Object> addAdmin(@RequestBody @Valid AdminRequestPayload adminRequestPayload) {
		Map<String, Object> map = new HashMap<>();
		if (adminRequestPayload != null) {
			return adminService.addAdmin(adminRequestPayload);
		} else {
			map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
			map.put(Constant.MESSAGE, Constant.BAD_REQUEST_MESSAGE);
			log.info("Bad request! status - {}", Constant.BAD_REQUEST);
			return map;
		}
	}
	
	@PostMapping("/admin_login")
	@ApiOperation(value = "Admin login !!")
	public Map<String, Object> adminLogin(@RequestParam String email,@RequestParam String password) {
		Map<String, Object> map = new HashMap<>();
		if (!email.isBlank() && !password.isBlank()) {
			return adminService.adminLogin(email,password);
		} else {
			map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
			map.put(Constant.MESSAGE, Constant.BAD_REQUEST_MESSAGE);
			log.info("Bad request! status - {}", Constant.BAD_REQUEST);
			return map;
		}
	}
	


    @PostMapping("/change_password")
  public Map<String, Object> changePassword(@RequestParam ("email")String email,@RequestParam ("oldPassword")String oldPassword,@RequestParam("password") String password)
 // public Map<String, Object> changePassword(@RequestParam ("email")String email,@RequestParam("password") String password)
  {
//      return adminService.changePassword(email, oldPassword, password);
        return adminService.changePassword(email,oldPassword, password);
      }


	@PostMapping("/get_all_admin")
	@ApiOperation(value = "Get admin list by search or direct !!")
	public Map<String, Object> getAllAdmin(@RequestParam Integer pageIndex, @RequestParam Integer pageSize,
			@RequestParam(required = false) String searchText) {
       Map<String, Object> map=new HashMap<>();
       if (pageIndex!= null && pageSize != null) {
			return adminService.getAllAdmin(pageIndex, pageSize, searchText);
		} else {
			map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
			map.put(Constant.MESSAGE, Constant.BAD_REQUEST_MESSAGE);
			return map;
		}
	}
	
	@PostMapping("/block_admin")
	@ApiOperation(value = "To block/unblock admin !! send status value, 2 for block and 1 for unblock admin")
	public Map<String, Object> blockAdmin(@RequestBody AdminBlockRequestPayload adminBlockRequestPayload){
		Map<String, Object> map=new HashMap<>();
		if(adminBlockRequestPayload!=null) {
			return adminService.blockAdmin(adminBlockRequestPayload);
		}else {
			map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
			map.put(Constant.MESSAGE, Constant.BAD_REQUEST_MESSAGE);
			log.info("Bad request! status - {}", Constant.BAD_REQUEST);
			return map;
		}
	}

	
	@PostMapping("/delete_admin")
	@ApiOperation(value = "Delete admin !!")
	public Map<String, Object> deleteAdmin(@RequestParam Long id) {
			return adminService.deleteAdmin(id);
	}
	

}
