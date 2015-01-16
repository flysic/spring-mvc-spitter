package com.springinaction.txexample;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/spitters")
public class SpitterController {
	
	@Autowired
	private SpitterService spitterService;

	@Value("1TT5F2KCS94CCAGB0CR2")
	private String s3AccessKey;

	@Value("0v3/JUq1hZ9eO7RK7qCcK2LdtLXiDTWHzDAOF1A4")
	private String s3SecretKey;

	@RequestMapping(method = RequestMethod.GET)
	public String listSpitters(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "perPage", defaultValue = "10") int perPage,
			Map<String, Object> model) {
		model.put("spitters", spitterService.getAllSpitters());
		return "spitters/list";
	}

	@RequestMapping(method = RequestMethod.GET, params = "new")
	public String createSpitter(Model model) {
		model.addAttribute(new Spitter());
		return "spitters/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addSpitter(@Valid Spitter spitter,
			BindingResult bindingResult,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		if (bindingResult.hasErrors() == true) {
			return "spitters/edit";
		}
		spitterService.saveSpitter(spitter);
		try {
			if (image.isEmpty() == false) {
				validateImage(image);
				saveImage(spitter.getId() + ".jpg", image);
			}
		} catch (ImageUploadException e) {
			bindingResult.reject(e.getMessage());
			return "spitters/edit";
		}
		return "redirect:/spitters/" + spitter.getUsername();
	}

	private void saveImage(String filename, MultipartFile image) {
		try {
			/*
			AWSCredentials awsCredentials = new AWSCredentials(s3AccessKey,
					s3SecretKey);
			S3Service s3 = new RestS3Service(awsCredentials);
			S3Bucket imageBucket = s3.getBucket("spitterImages");
			S3Object imageObject = new S3Object(filename);
			imageObject.setDataInputStream(new ByteArrayInputStream(image
					.getBytes()));
			imageObject.setContentLength(image.getSize());
			imageObject.setContentType("image/jpeg");
			AccessControlList acl = new AccessControlList();
			acl.setOwner(imageBucket.getOwner());
			acl.grantPermission(GroupGrantee.ALL_USERS,
					Permission.PERMISSION_READ);
			imageObject.setAcl(acl);
			s3.putObject(imageBucket, imageObject);
			*/
		} catch (Exception e) {
			throw new ImageUploadException("Unable to save image", e);
		}
	}

	private void validateImage(MultipartFile image) {
		if (image.getContentType().equals("image/jpeg") == false) {
			throw new ImageUploadException("Only jpg image accepted");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public String showSpitterProfile(@PathVariable String username, Model model) {
		model.addAttribute(spitterService.getSpitter(username));
		return "spitters/view";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}", params="edit")
	public String editSpitterProfile(@PathVariable String username, Model model) {
		model.addAttribute(spitterService.getSpitter(username));
		return "spitters/edit";
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{username}")
	public String updateSpitterFromForm(@PathVariable String username,
			Spitter spitter) {
		spitterService.saveSpitter(spitter);
		return "redirect:/spitters/" + username;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{username}")
	public String deleteSpitter(@PathVariable String username) {
		// TODO
		return "redirect:/home";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}/spittles")
	public String listSpittlesForSpitter(@PathVariable String username,
			Model model) {
		model.addAttribute(spitterService.getSpitter(username));
		List<Spittle> spittlesForSpitter = spitterService
				.getSpittlesForSpitter(username);
		model.addAttribute(spittlesForSpitter);
		return "spittles/list";
	}

	// Machine-friendly RESTful handler methods follow
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Spitter> allSpitters() {
		return spitterService.getAllSpitters();
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Spitter createSpitter(@RequestBody Spitter spitter) {
		spitterService.saveSpitter(spitter);
		return spitter;
	}

	// <start id="method_getSpitter_ResponseBody"/>
	@RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = { "Accept=text/xml, application/json" })
	public @ResponseBody Spitter getSpitter(@PathVariable String username) {
		return spitterService.getSpitter(username);
	}

	// <end id="method_getSpitter_ResponseBody"/>

	// @RequestMapping(value = "/{username}", method = RequestMethod.GET,
	// headers = "Accept=application/json")
	// public @ResponseBody
	// Spitter getSpitterAsXML(@PathVariable String username) {
	// return spitterService.getSpitter(username);
	// }

	// <start id="method_putSpitter"/>
	@RequestMapping(value = "/{username}", method = RequestMethod.PUT, headers = "Content-Type=application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateSpitter(@PathVariable String username,
			@RequestBody Spitter spitter) {
		spitterService.saveSpitter(spitter);
	}

	// <end id="method_putSpitter"/>

	@RequestMapping(value = "/{username}/spittles", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Spittle> getSpittlesForSpitter(
			@PathVariable String username) {
		return spitterService.getSpittlesForSpitter(username);
	}

}
