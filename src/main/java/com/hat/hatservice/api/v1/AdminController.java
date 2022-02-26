package com.hat.hatservice.api.v1;

import com.hat.hatservice.api.dto.PermissionRequest;
import com.hat.hatservice.api.dto.PermissionResponse;
import com.hat.hatservice.api.dto.StakeResponse;
import com.hat.hatservice.api.dto.StakeSettingsRequest;
import com.hat.hatservice.api.dto.StakeSettingsResponse;
import com.hat.hatservice.api.dto.TransactionsResponse;
import com.hat.hatservice.api.dto.UserPermissionRequest;
import com.hat.hatservice.api.dto.UserPermissionResponse;
import com.hat.hatservice.api.dto.UserResponse;
import com.hat.hatservice.api.dto.WithdrawalResponse;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.service.RolePermissionService;
import com.hat.hatservice.service.StakeService;
import com.hat.hatservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hat/admin")
public class AdminController {
	private final StakeService stakeService;
	private final RolePermissionService rolePermissionService;
	private final UserService userService;

	public AdminController(StakeService stakeService, RolePermissionService rolePermissionService, UserService userService) {
		this.stakeService = stakeService;
		this.rolePermissionService = rolePermissionService;
		this.userService = userService;
	}

	@GetMapping(value = "/stakes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<StakeResponse> findAllStakes() {
		return stakeService.findAllStakes();
	}

	@PostMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public StakeSettingsResponse create(@RequestBody StakeSettingsRequest request) throws Exception {
		return stakeService.createStakeSettings(request);
	}

	@PutMapping(value = "/settings/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public void update(@RequestBody StakeSettingsRequest request, @PathVariable("id") UUID id) throws NotFoundException {
		stakeService.updateStakeSettings(request, id);
	}

	@PostMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public PermissionResponse createPermission(@RequestBody PermissionRequest request) throws Exception {
		return rolePermissionService.createPermission(request);
	}

	@GetMapping(value = "/permission/{permission_name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public UUID findPermissionId(@PathVariable("permission_name") String permissionName) throws NotFoundException {
		return rolePermissionService.findPermission(permissionName);
	}

	@GetMapping(value = "/haspermission/{id}/{permission_name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public Boolean hasPermission(@PathVariable("id") UUID id, @PathVariable("permission_name") String permissionName) {
		return rolePermissionService.hasPermission(id, permissionName);
	}

	@PostMapping(value = "/userpermission", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void assignUserPermission(@RequestBody UserPermissionRequest request) {
		rolePermissionService.assignUserPermission(request);
	}

	@PostMapping(value = "/updaterole/{role_name}/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void updateRole(@PathVariable("role_name") String roleName, @PathVariable("user_id") UUID userId) throws NotFoundException {
		userService.updateRole(roleName, userId);
	}

	@GetMapping(value = "/userpermissions/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public UserPermissionResponse findUserPermissions(@PathVariable("user_id") UUID userId) {
		return rolePermissionService.findUserPermissions(userId);
	}

	@GetMapping(value = "/det", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public UserResponse findUserPermissions() throws NotFoundException {
		return userService.getLoggedUserDetails();
	}

	@GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<TransactionsResponse> getAllTransactions() {
		return userService.getAllTransactions();
	}

	@GetMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<WithdrawalResponse> getWithdrawalRequestAll() {
		return userService.getWithdrawalRequestAll();
	}
}
