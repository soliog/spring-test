package com.sno.spring.test.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonManagedReference;

import com.sno.spring.test.util.BaseEntity;
import com.sno.spring.test.util.EntityFinder;

@XmlRootElement
@Entity
public class Contact extends BaseEntity<Contact> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private static transient final EntityFinder<Contact> finder = 
			new EntityFinder<Contact>(Contact.class);
	
	private String firstName;
	private String lastName;
	
	@JsonManagedReference
	@OneToMany(mappedBy="contact", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Address> addresses;

	public Contact() {
	}

	public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public static final EntityManager entityManager() throws Exception {
		return finder.entityManager();
	}

	public static long size() throws Exception {
		return finder.size();
	}

	public static List<Contact> findAll() throws Exception {
		return finder.findAll();
	}
	
	public static Contact find(Integer id) throws Exception {
		return finder.find(id);
	}
}
