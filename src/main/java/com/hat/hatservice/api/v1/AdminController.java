package com.hat.hatservice.api.v1;

import com.hat.hatservice.api.dto.StakeResponse;
import com.hat.hatservice.api.dto.StakeSettingsRequest;
import com.hat.hatservice.api.dto.StakeSettingsResponse;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.service.StakeService;
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

	public AdminController(StakeService stakeService) {
		this.stakeService = stakeService;
	}

	@GetMapping(value = "/stakes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<StakeResponse> findAllStakes() throws NotFoundException {
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
}
