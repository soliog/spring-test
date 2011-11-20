package com.sno.spring.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sno.spring.test.domain.Address;
import com.sno.spring.test.domain.Contact;

@Controller
@RequestMapping(value="contact/{contactId}/address")
public class AddressController {
	
	@RequestMapping(method=RequestMethod.GET)	
	public List<Address> index(@PathVariable Integer contactId) throws Exception {
		return Contact.find(contactId).getAddresses();	
	}	

}
