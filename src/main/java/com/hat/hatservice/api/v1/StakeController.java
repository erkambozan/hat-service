package com.hat.hatservice.api.v1;

import com.hat.hatservice.api.dto.StakeRequest;
import com.hat.hatservice.api.dto.StakeResponse;
import com.hat.hatservice.api.dto.StakeSettingsResponse;
import com.hat.hatservice.exception.NotFoundException;
import com.hat.hatservice.service.StakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hat/stake")
public class StakeController {
	private final StakeService stakeService;

	public StakeController(StakeService stakeService) {
		this.stakeService = stakeService;
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void stake(@RequestBody StakeRequest request) throws Exception {
		stakeService.stake(request);
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<StakeResponse> findStakesByUserId() throws NotFoundException {
		return stakeService.findStakesByUserId();
	}

	@GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<StakeSettingsResponse> getStakeSettingsAll() {
		return stakeService.getStakeSettingsAll();
	}
}
