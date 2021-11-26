package com.hat.hatservice.api.v1;

import com.hat.hatservice.api.dto.StakeRequest;
import com.hat.hatservice.api.dto.StakeSettingsRequest;
import com.hat.hatservice.api.dto.StakeSettingsResponse;
import com.hat.hatservice.exception.DuplicateException;
import com.hat.hatservice.exception.InsufficientBalance;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.service.StakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/hat/stake")
public class StakeController {
	private final StakeService stakeService;

	public StakeController(StakeService stakeService) {
		this.stakeService = stakeService;
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void stake(@RequestBody StakeRequest request) throws DuplicateException, NotFoundException, InsufficientBalance {
		stakeService.stake(request);
	}

	@PostMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public StakeSettingsResponse create(@RequestBody StakeSettingsRequest request) throws DuplicateException {
		return stakeService.createStakeSettings(request);
	}

	@PutMapping(value = "/settings/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public void update(@RequestBody StakeSettingsRequest request, @PathVariable("id") UUID id) throws NotFoundException {
		stakeService.updateStakeSettings(request, id);
	}
}
