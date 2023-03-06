package com.advantal.adminRoleModuleService.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.advantal.adminRoleModuleService.models.Admin;
import com.advantal.adminRoleModuleService.models.Role;
import com.advantal.adminRoleModuleService.models.RoleModuleMapping;
import com.advantal.adminRoleModuleService.repository.AdminRepository;
import com.advantal.adminRoleModuleService.repository.RoleRepository;
import com.advantal.adminRoleModuleService.requestpayload.AdminBlockRequestPayload;
import com.advantal.adminRoleModuleService.requestpayload.AdminRequestPayload;
import com.advantal.adminRoleModuleService.responsepayload.AdminResponsePage;
import com.advantal.adminRoleModuleService.responsepayload.AdminResponsePayload;
import com.advantal.adminRoleModuleService.responsepayload.RoleModuleMappingResponse;
import com.advantal.adminRoleModuleService.responsepayload.RoleResponsePayload;
import com.advantal.adminRoleModuleService.service.AdminService;
import com.advantal.adminRoleModuleService.utils.Constant;
import com.advantal.adminRoleModuleService.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CheckExist checkExist;

	@Override
	public Map<String, Object> addAdmin(AdminRequestPayload adminRequestPayload) {
		Map<String, Object> map = new HashMap<>();
		Admin admin = new Admin();
		Role role = null;
		try {
			if (adminRequestPayload.getId() != 0) {
				/* ----------- Here update admin details ----------- */
				admin = adminRepository.findByIdAndStatus(adminRequestPayload.getId(), Constant.ONE);
				if (admin != null) {
					log.info("Admin found! status - {}", admin);
					role = roleRepository.findByIdAndStatus(adminRequestPayload.getRoleId(), Constant.ONE);
					if (role != null) {
						log.info("Role found! status - {}", admin);
						admin.setRole(role);
					} else {
						map.put(Constant.RESPONSE_CODE, Constant.NOT_FOUND);
						map.put(Constant.MESSAGE, Constant.ROLE_NOT_FOUND_MESSAGE);
						log.info("Given role not found into the database! status - {}", Constant.NOT_FOUND);
						return map;
					}

					if (checkExist.isExistMobileAndId(adminRequestPayload.getMobile(), adminRequestPayload.getId())) {
						admin.setMobile(adminRequestPayload.getMobile());
					} else if (!checkExist.isExistMobile(adminRequestPayload.getMobile())) {
						admin.setMobile(adminRequestPayload.getMobile());
					} else {
						map.put(Constant.RESPONSE_CODE, Constant.CONFLICT);
						map.put(Constant.MESSAGE, Constant.MOBILE_EXISTS_MESSAGE);
						log.info("Please provide another mobile, as this mobile already exist!! status - {}",
								Constant.CONFLICT);
						return map;
					}
					admin.setEmail(admin.getEmail());
					admin.setName(adminRequestPayload.getName());
					admin.setUpdationDate(new Date());
					admin.setPassword(adminRequestPayload.getPassword());
					admin.setStatus(Constant.ONE);
					admin = adminRepository.save(admin);

					AdminResponsePayload adminResponsePayload = new AdminResponsePayload();
					BeanUtils.copyProperties(admin, adminResponsePayload);

					RoleResponsePayload roleResponsePayload = new RoleResponsePayload();
					BeanUtils.copyProperties(admin.getRole(), roleResponsePayload);
					roleResponsePayload
							.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getRole().getEntryDate()));
					roleResponsePayload
							.setUpdationDate(DateUtil.convertDateToStringDateTime(admin.getRole().getUpdationDate()));

					List<RoleModuleMappingResponse> roleModuleMappingResponseList = new ArrayList<>();
					for (RoleModuleMapping roleModuleMapping : admin.getRole().getRoleModuleMappingList()) {
						RoleModuleMappingResponse roleModuleMapResponse = new RoleModuleMappingResponse();
						BeanUtils.copyProperties(roleModuleMapping, roleModuleMapResponse);
						roleModuleMapResponse
								.setEntryDate(DateUtil.convertDateToStringDateTime(roleModuleMapping.getEntryDate()));
						roleModuleMapResponse.setUpdationDate(
								DateUtil.convertDateToStringDateTime(roleModuleMapping.getUpdationDate()));
						roleModuleMappingResponseList.add(roleModuleMapResponse);
					}
					roleResponsePayload.setRoleModuleMappingResponseList(roleModuleMappingResponseList);

					adminResponsePayload.setRoleResponsePayload(roleResponsePayload);
					adminResponsePayload.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getEntryDate()));
					adminResponsePayload.setUpdationDate(DateUtil.convertDateToStringDateTime(admin.getUpdationDate()));

					map.put(Constant.RESPONSE_CODE, Constant.OK);
					map.put(Constant.MESSAGE, Constant.ADMIN_UPDATED_SUCCESS_MESSAGE);
					map.put(Constant.DATA, adminResponsePayload);
					log.info("Admin updated successfully! status - {}", adminResponsePayload);
				} else {
					map.put(Constant.RESPONSE_CODE, Constant.NOT_FOUND);
					map.put(Constant.MESSAGE, Constant.ROLE_NOT_FOUND_MESSAGE);
					log.info("Given role not found into the database! status - {}", Constant.NOT_FOUND);
				}
			} else {
				/* ----------- Here creating admin details ----------- */
				role = roleRepository.findByIdAndStatus(adminRequestPayload.getRoleId(), Constant.ONE);
				if (role != null) {

					if (checkExist.isExistEmailAndStatus(adminRequestPayload.getEmail())) {
						map.put(Constant.RESPONSE_CODE, Constant.CONFLICT);
						map.put(Constant.MESSAGE, Constant.EMAIL_EXISTS_MESSAGE);
						log.info("Please provide another email, as this email already exist!! status - {}",
								Constant.CONFLICT);
						return map;
					} else {
						admin.setEmail(adminRequestPayload.getEmail());
					}

					if (checkExist.isExistMobile(adminRequestPayload.getMobile())) {
						map.put(Constant.RESPONSE_CODE, Constant.CONFLICT);
						map.put(Constant.MESSAGE, Constant.MOBILE_EXISTS_MESSAGE);
						log.info("Please provide another mobile, as this mobile already exist!! status - {}",
								Constant.CONFLICT);
						return map;
					} else {
						admin.setMobile(adminRequestPayload.getMobile());
					}
					admin.setName(adminRequestPayload.getName());
					admin.setEntryDate(new Date());
					admin.setPassword(adminRequestPayload.getPassword());
					admin.setStatus(Constant.ONE);
					admin.setRole(role);
					admin = adminRepository.save(admin);

					AdminResponsePayload adminResponsePayload = new AdminResponsePayload();
					BeanUtils.copyProperties(admin, adminResponsePayload);

					RoleResponsePayload roleResponsePayload = new RoleResponsePayload();
					BeanUtils.copyProperties(admin.getRole(), roleResponsePayload);
					roleResponsePayload
							.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getRole().getEntryDate()));
					roleResponsePayload
							.setUpdationDate(DateUtil.convertDateToStringDateTime(admin.getRole().getUpdationDate()));

					List<RoleModuleMappingResponse> roleModuleMappingResponseList = new ArrayList<>();
					for (RoleModuleMapping roleModuleMapping : admin.getRole().getRoleModuleMappingList()) {
						RoleModuleMappingResponse roleModuleMapResponse = new RoleModuleMappingResponse();
						BeanUtils.copyProperties(roleModuleMapping, roleModuleMapResponse);
						roleModuleMapResponse
								.setEntryDate(DateUtil.convertDateToStringDateTime(roleModuleMapping.getEntryDate()));
						roleModuleMapResponse.setUpdationDate(
								DateUtil.convertDateToStringDateTime(roleModuleMapping.getUpdationDate()));
						roleModuleMappingResponseList.add(roleModuleMapResponse);
					}
					roleResponsePayload.setRoleModuleMappingResponseList(roleModuleMappingResponseList);

					adminResponsePayload.setRoleResponsePayload(roleResponsePayload);
					adminResponsePayload.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getEntryDate()));
					map.put(Constant.DATA, adminResponsePayload);
					map.put(Constant.RESPONSE_CODE, Constant.OK);
					map.put(Constant.MESSAGE, Constant.ADMIN_ADDED_SUCCESS_MESSAGE);
					log.info("Admin registered successfully! status - {}", adminResponsePayload);
				} else {
					map.put(Constant.RESPONSE_CODE, Constant.NOT_FOUND);
					map.put(Constant.MESSAGE, Constant.ROLE_NOT_FOUND_MESSAGE);
					log.info("Given role not found into the database! status - {}", Constant.NOT_FOUND);
				}
			}
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			map.put(Constant.RESPONSE_CODE, Constant.DB_CONNECTION_ERROR);
			map.put(Constant.MESSAGE, Constant.NO_SERVER_CONNECTION);
			log.error("Exception : " + e.getMessage());
		} catch (Exception e) {
			log.error("Exception : " + e.getMessage());
			map.put(Constant.RESPONSE_CODE, Constant.SERVER_ERROR);
			map.put(Constant.MESSAGE, Constant.SERVER_MESSAGE);
		}

		return map;
	}

	@Override
	public Map<String, Object> adminLogin(String email, String password) {
		Map<String, Object> map = new HashMap<>();
		Admin admin = adminRepository.findByEmailAndStatus(email, Constant.ONE);
		try {
			if (admin != null) {
				log.info("Record found! status - {}", Constant.OK);
				if (admin.getPassword().matches(password)) {
					AdminResponsePayload adminResponsePayload = new AdminResponsePayload();
					BeanUtils.copyProperties(admin, adminResponsePayload);

					RoleResponsePayload roleResponsePayload = new RoleResponsePayload();
					BeanUtils.copyProperties(admin.getRole(), roleResponsePayload);
					roleResponsePayload
							.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getRole().getEntryDate()));
					roleResponsePayload
							.setUpdationDate(DateUtil.convertDateToStringDateTime(admin.getRole().getUpdationDate()));

					List<RoleModuleMappingResponse> roleModuleMappingResponseList = new ArrayList<>();
					for (RoleModuleMapping roleModuleMapping : admin.getRole().getRoleModuleMappingList()) {
						RoleModuleMappingResponse roleModuleMapResponse = new RoleModuleMappingResponse();
						BeanUtils.copyProperties(roleModuleMapping, roleModuleMapResponse);
						roleModuleMapResponse
								.setEntryDate(DateUtil.convertDateToStringDateTime(roleModuleMapping.getEntryDate()));
						roleModuleMapResponse.setUpdationDate(
								DateUtil.convertDateToStringDateTime(roleModuleMapping.getUpdationDate()));
						roleModuleMappingResponseList.add(roleModuleMapResponse);
					}
					roleResponsePayload.setRoleModuleMappingResponseList(roleModuleMappingResponseList);

					adminResponsePayload.setRoleResponsePayload(roleResponsePayload);
					adminResponsePayload.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getEntryDate()));
					adminResponsePayload.setUpdationDate(DateUtil.convertDateToStringDateTime(admin.getUpdationDate()));
					map.put(Constant.DATA, adminResponsePayload);
					map.put(Constant.RESPONSE_CODE, Constant.OK);
					map.put(Constant.MESSAGE, Constant.ADMIN_LOGIN_SUCCESSFULLY);
					log.info("Admin login successfully! status - {}", adminResponsePayload);
				} else {
					map.put(Constant.DATA, admin);
					map.put(Constant.RESPONSE_CODE, Constant.NOT_AUTHORIZED);
					map.put(Constant.MESSAGE, Constant.INVALID_CREDENTIAL);
					log.info("Invalid credentials! status - {}", Constant.NOT_AUTHORIZED);
				}
			} else {
				map.put(Constant.DATA, admin);
				map.put(Constant.RESPONSE_CODE, Constant.NOT_AUTHORIZED);
				map.put(Constant.MESSAGE, Constant.INVALID_CREDENTIAL);
				log.info("Invalid credentials! status - {}", Constant.NOT_AUTHORIZED);
			}

		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			map.put(Constant.RESPONSE_CODE, Constant.DB_CONNECTION_ERROR);
			map.put(Constant.MESSAGE, Constant.NO_SERVER_CONNECTION);
			log.error("Exception : " + e.getMessage());
		} catch (Exception e) {
			log.error("Exception : " + e.getMessage());
			map.put(Constant.RESPONSE_CODE, Constant.SERVER_ERROR);
			map.put(Constant.MESSAGE, Constant.SERVER_MESSAGE);
		}
		return map;
	}

	@Override
	public Map<String, Object> changePassword(String email, String oldPassword, String password) {
		Map<String, Object> map = new HashMap<>();
		try {

			Admin admin = adminRepository.findByEmail(email);
			if (admin != null && admin.getPassword() != null) {
				Admin admin1 = null;
				if (admin.getPassword().equals(oldPassword)) {
					admin.setPassword(password);
					admin1 = adminRepository.save(admin);
					map.put(Constant.RESPONSE_CODE, Constant.OK);
					map.put(Constant.MESSAGE, Constant.PASSWORD_UPDATE);
					// log.info("password change sucessfully! status - {}", admin1);
					map.put(Constant.RESPONSE_CODE, admin1);
					log.info("password change sucessfully! status - {}", admin1);
				} else {
					map.put(Constant.RESPONSE_CODE, Constant.FORBIDDEN);
					map.put(Constant.MESSAGE, Constant.PASSWORD_NOT_MATCH);
					log.info("old password not match!! status - {}", Constant.BAD_REQUEST);

					return map;
				}
			}

			else {
				map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
				map.put(Constant.MESSAGE, Constant.FIELD_NOT_EMPTY);
				log.info("email not valid!! status - {}", Constant.BAD_REQUEST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constant.RESPONSE_CODE, Constant.SERVER_ERROR);
			map.put(Constant.MESSAGE, Constant.ERROR_MESSAGE);
			log.error("Exception : " + e.getMessage());
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> blockAdmin(AdminBlockRequestPayload adminBlockRequestPayload) {
		Map<String, Object> map = new HashMap<>();
		try {
			Optional<Admin> admin = Optional.empty();
			Admin oldAdmin = new Admin();
			admin = adminRepository.findById(adminBlockRequestPayload.getId());
			if (admin.isPresent()) {
				log.info("Record found! status - {}", Constant.OK);
				oldAdmin = admin.get();

				/* ------------------- Here unblock the admin ----------------- */
				if (adminBlockRequestPayload.getStatus().equals(Constant.ONE)) {
					if (oldAdmin.getStatus().equals(Constant.ONE)) {
						map.put(Constant.RESPONSE_CODE, Constant.CONFLICT);
						map.put(Constant.MESSAGE, Constant.ALREADY_UNBLOCKED_MESSAGE);
						log.info("Already unblocked! status - {}", Constant.CONFLICT);
						return map;
					} else {
						oldAdmin.setStatus(adminBlockRequestPayload.getStatus());
						oldAdmin.setUpdationDate(new Date());
						oldAdmin = adminRepository.save(oldAdmin);
						map.put(Constant.RESPONSE_CODE, Constant.OK);
						map.put(Constant.MESSAGE, Constant.UNBLOCKED_SUCCESS_MESSAGE);
						log.info("unblocked successfully! status - {}", Constant.OK);
					}
				}

				/* ------------------- Here block the admin ----------------- */
				else if (adminBlockRequestPayload.getStatus().equals(Constant.TWO)) {
					if (oldAdmin.getStatus().equals(Constant.TWO)) {
						map.put(Constant.RESPONSE_CODE, Constant.CONFLICT);
						map.put(Constant.MESSAGE, Constant.ALREADY_UNBLOCKED_MESSAGE);
						log.info("Already unblocked! status - {}", Constant.CONFLICT);
						return map;
					} else {
						oldAdmin.setStatus(adminBlockRequestPayload.getStatus());
						oldAdmin.setUpdationDate(new Date());
						oldAdmin = adminRepository.save(oldAdmin);
						map.put(Constant.RESPONSE_CODE, Constant.OK);
						map.put(Constant.MESSAGE, Constant.BLOCKED_SUCCESS_MESSAGE);
						log.info("Blocked successfully! status - {}", Constant.OK);
					}
				} else {
					map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
					map.put(Constant.MESSAGE, Constant.STATUS_INVALID_MESSAGE);
					log.info("Status value invalid! status - {}", Constant.BAD_REQUEST);
				}

			} else {
				map.put(Constant.RESPONSE_CODE, Constant.NOT_FOUND);
				map.put(Constant.MESSAGE, Constant.RECORD_NOT_FOUND_MESSAGE);
				log.info("Record not found! status - {}", Constant.NOT_FOUND);
			}
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			map.put(Constant.RESPONSE_CODE, Constant.DB_CONNECTION_ERROR);
			map.put(Constant.MESSAGE, Constant.NO_SERVER_CONNECTION);
			log.error("Exception : " + e.getMessage());
		} catch (Exception e) {
			map.put(Constant.RESPONSE_CODE, Constant.SERVER_ERROR);
			map.put(Constant.MESSAGE, Constant.SERVER_MESSAGE);
			log.error("Exception : " + e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteAdmin(Long id) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (id != null) {
				Optional<Admin> admin = Optional.empty();
				Admin oldAdmin = new Admin();
				admin = adminRepository.findById(id);
				if (!admin.isEmpty()) {
					oldAdmin = admin.get();
					if (oldAdmin.getStatus().equals(Constant.ZERO)) {
						map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
						map.put(Constant.MESSAGE, Constant.ALREADY_DELETED_MESSAGE);
						log.info("Already deleted! status - {}", Constant.CONFLICT);
					} else {
						/* ----- Perform delete operation ----- */
						oldAdmin.setStatus(Constant.ZERO);
						oldAdmin.setUpdationDate(new Date());
						oldAdmin = adminRepository.save(oldAdmin);

						map.put(Constant.RESPONSE_CODE, Constant.OK);
						map.put(Constant.MESSAGE, Constant.DELETED_MESSAGE);
						log.info("Deleted successfully! status - {}", Constant.OK);
					}
				} else {
					map.put(Constant.RESPONSE_CODE, Constant.NOT_FOUND);
					map.put(Constant.MESSAGE, Constant.ID_NOT_FOUND_MESSAGE);
					log.info("Given id not found into the database! status - {}", Constant.NOT_FOUND);
				}
			} else {
				map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
				map.put(Constant.MESSAGE, Constant.ID_CAN_NOT_NULL_MESSAGE);
				log.info("Id can not null, it should be valid! status - {}", Constant.BAD_REQUEST);
			}

		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			map.put(Constant.RESPONSE_CODE, Constant.DB_CONNECTION_ERROR);
			map.put(Constant.MESSAGE, Constant.NO_SERVER_CONNECTION);
			log.error("Exception : " + e.getMessage());
		} catch (Exception e) {
			map.put(Constant.RESPONSE_CODE, Constant.SERVER_ERROR);
			map.put(Constant.MESSAGE, Constant.SERVER_MESSAGE);
			log.error("Exception : " + e.getMessage());
		}
		return map;
	}

	public Map<String, Object> getAllAdmin(Integer pageIndex, Integer pageSize, String searchText) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<AdminResponsePayload> adminResponsePayloadList = new ArrayList<>();
			AdminResponsePage adminResponsePage = new AdminResponsePage();
			List<Admin> adminlist = new ArrayList<>();
			Page<Admin> page = null;

			if (pageSize >= 1) {
				Pageable pageable = PageRequest.of(pageIndex, pageSize);
				if (searchText != "" && searchText != null) {
					if (!searchText.equals("0") && !searchText.equals("1") && !searchText.equals("2")) {
						page = adminRepository.findByNameContainingOrEmailContainingOrMobileContaining(searchText,
								searchText, searchText, pageable);
					} else if (searchText.equals("0") || searchText.equals("1") || searchText.equals("2")) {
						Short status = Short.parseShort(searchText);
						page = adminRepository.findAllByStatus(status, pageable);
					}
				} else {
					page = adminRepository.findAllByStatus(Constant.ONE, pageable);
				}
				if (page != null && page.getContent().size() > Constant.ZERO) {
					adminlist = page.getContent();
					for (Admin admin : adminlist) {
						List<RoleModuleMappingResponse> roleModuleMappingResponseList = new ArrayList<>();
						for (RoleModuleMapping roleModuleMapping : admin.getRole().getRoleModuleMappingList()) {
							RoleModuleMappingResponse roleModuleMapResponse = new RoleModuleMappingResponse();
							BeanUtils.copyProperties(roleModuleMapping, roleModuleMapResponse);
							roleModuleMapResponse.setEntryDate(
									DateUtil.convertDateToStringDateTime(roleModuleMapping.getEntryDate()));
							roleModuleMapResponse.setUpdationDate(
									DateUtil.convertDateToStringDateTime(roleModuleMapping.getUpdationDate()));
							roleModuleMappingResponseList.add(roleModuleMapResponse);
						}
						RoleResponsePayload roleResponsePayload = new RoleResponsePayload();
						BeanUtils.copyProperties(admin.getRole(), roleResponsePayload);
						roleResponsePayload.setRoleModuleMappingResponseList(roleModuleMappingResponseList);
						roleResponsePayload
								.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getRole().getEntryDate()));
						roleResponsePayload.setUpdationDate(
								DateUtil.convertDateToStringDateTime(admin.getRole().getUpdationDate()));

						AdminResponsePayload adminResponsePayload = new AdminResponsePayload();
						BeanUtils.copyProperties(admin, adminResponsePayload);
						adminResponsePayload.setRoleResponsePayload(roleResponsePayload);
						adminResponsePayload.setEntryDate(DateUtil.convertDateToStringDateTime(admin.getEntryDate()));
						adminResponsePayload
								.setUpdationDate(DateUtil.convertDateToStringDateTime(admin.getUpdationDate()));
						adminResponsePayloadList.add(adminResponsePayload);

					}
					adminResponsePage.setAdminResponsePayloadList(adminResponsePayloadList);
					adminResponsePage.setPageIndex(page.getNumber());
					adminResponsePage.setPageSize(page.getSize());
					adminResponsePage.setTotalElement(page.getTotalElements());
					adminResponsePage.setTotalPages(page.getTotalPages());
					adminResponsePage.setIsLastPage(page.isLast());
					adminResponsePage.setIsFirstPage(page.isFirst());

					map.put(Constant.RESPONSE_CODE, Constant.OK);
					map.put(Constant.MESSAGE, Constant.RECORD_FOUND_MESSAGE);
					map.put(Constant.DATA, adminResponsePage);
					log.info("Record found! status - {}", adminResponsePage);
				} else {
					map.put(Constant.RESPONSE_CODE, Constant.NOT_FOUND);
					map.put(Constant.MESSAGE, Constant.RECORD_NOT_FOUND_MESSAGE);
					map.put(Constant.DATA, adminResponsePage);
					log.info("Record not found! status - {}", adminResponsePage);
				}
			} else {
				map.put(Constant.RESPONSE_CODE, Constant.BAD_REQUEST);
				map.put(Constant.MESSAGE, Constant.PAGE_SIZE_MESSAGE);
				log.info("Page size can't be less then one! status - {}", pageSize);
			}
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			map.put(Constant.RESPONSE_CODE, Constant.DB_CONNECTION_ERROR);
			map.put(Constant.MESSAGE, Constant.NO_SERVER_CONNECTION);
			log.error("Exception : " + e.getMessage());
		} catch (Exception e) {
			map.put(Constant.RESPONSE_CODE, Constant.SERVER_ERROR);
			map.put(Constant.MESSAGE, Constant.SERVER_MESSAGE);
			log.error("Exception : " + e.getMessage());
		}
		return map;
	}
}
