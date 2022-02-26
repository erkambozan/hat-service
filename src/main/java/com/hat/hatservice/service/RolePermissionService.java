package com.hat.hatservice.service;

import com.hat.hatservice.api.dto.PermissionRequest;
import com.hat.hatservice.api.dto.PermissionResponse;
import com.hat.hatservice.api.dto.UserPermissionRequest;
import com.hat.hatservice.api.dto.UserPermissionResponse;
import com.hat.hatservice.db.Permission;
import com.hat.hatservice.db.PermissionRepository;
import com.hat.hatservice.db.UserPermission;
import com.hat.hatservice.db.UserPermissionRepository;
import com.hat.hatservice.exception.DuplicateException;
import com.hat.hatservice.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RolePermissionService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private final PermissionRepository permissionRepository;
	private final UserPermissionRepository userPermissionRepository;

	public RolePermissionService(PermissionRepository permissionRepository, UserPermissionRepository userPermissionRepository) {
		this.permissionRepository = permissionRepository;
		this.userPermissionRepository = userPermissionRepository;
	}

	public PermissionResponse createPermission(PermissionRequest request) throws  DuplicateException {
		Optional<Permission> permissionOptional = permissionRepository.findByName(request.getPermissionName());

		if (permissionOptional.isEmpty()) {
			Permission permission = permissionRepository.save(new Permission(
					request.getPermissionName()
			));

			return new PermissionResponse(permission.getId(), request.getPermissionName());

		} else {
			throw new DuplicateException("Permission Already Exists");
		}
	}

	public UserPermissionResponse findUserPermissions(UUID userId) {
		List<String> permissionNameList = new ArrayList<String>();
		userPermissionRepository.findAllByUserId(userId).forEach(u -> {
			Optional<Permission> permission = permissionRepository.findById(u.getPermissionId());

			permission.ifPresent(p -> {
				permissionNameList.add(p.getName());
			});
		});

		return new UserPermissionResponse(
				userId,
				permissionNameList
		);
	}

	public void assignUserPermission(UserPermissionRequest request) {
		request.getPermissionsName().forEach(p -> {
			Optional<Permission> permission = permissionRepository.findByName(p);
			if(permission.isPresent()){
				Optional<UserPermission> up = userPermissionRepository.findByUserIdAndPermissionId(request.getUserId(), permission.get().getId());

				if (up.isPresent()) {
					return;
				}

				userPermissionRepository.save(new UserPermission(
						request.getUserId(),
						permission.get().getId()
				));
			}
		});
	}

	public UUID findPermission(String permissionName) throws NotFoundException {
		Optional<Permission> permissionOptional = permissionRepository.findByName(permissionName);

		if (permissionOptional.isPresent()) {
			return permissionOptional.get().getId();
		}

		throw new NotFoundException("Permission Not Found : " + permissionName);
	}

	public Boolean hasPermission(UUID id, String permissionName){
		Optional<Permission> permissionOptional = permissionRepository.findByName(permissionName);
		if(permissionOptional.isEmpty()){
			return false;
		}

		Optional<UserPermission> userPermissionOptional = userPermissionRepository.findByUserIdAndPermissionId(id, permissionOptional.get().getId());
		return userPermissionOptional.isPresent();
	}
}
