package com.sno.spring.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sno.spring.test.domain.Address;
import com.sno.spring.test.domain.Contact;
import com.sno.spring.test.util.BaseEntityAnnotationSupport.ITest;

@Controller
@RequestMapping(value="contact")
@SessionAttributes("contact")
public class ContactController {	
	
	@RequestMapping(method=RequestMethod.GET)	
	public List<Contact> index() throws Exception {		
		return Contact.findAll();		
	}	
	
	@RequestMapping(value="new", method=RequestMethod.GET)
	public Contact newContact() {		
		return new Contact();
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	public Contact add(@RequestBody Contact c) {						
		return c.merge();
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public Contact get(@PathVariable Integer id) throws Exception {	
		
		Contact c = Contact.find(id);
		((ITest) c).test1();
		return c;
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public void delete(@PathVariable String id, @RequestParam("method") String method) {
		delete(id);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable String id) {						
	}
		
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public Contact edit(@PathVariable Integer id) throws Exception {	
		return get(id);
	}		
	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public Contact update(@ModelAttribute("contact") Contact c, @RequestBody Contact c1, 
			@RequestParam("method") String method) {			
        return update(c, c1);
	}
	
	@Transactional
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public Contact update(@ModelAttribute("contact") Contact c, @RequestBody Contact c1) {							
        
		Address ad = new Address();
		ad.setStreet("1200 Monks");
		c.getAddresses().add(ad);
		ad.setContact(c);
		c.merge();
		c.flush();
		return c;
	}		
}
