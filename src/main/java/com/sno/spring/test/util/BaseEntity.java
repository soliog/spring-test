package com.sno.spring.test.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

@Configurable(dependencyCheck=true)
public abstract class BaseEntity<T> {

	@PersistenceContext
    protected transient EntityManager entityManager;		
		
    @SuppressWarnings("unchecked")
	public T persist() {        		   
        
    	entityManager.persist(this);
        return (T) this;
    }
        
    public void remove() {
        
    	if (entityManager.contains(this)) {
            entityManager.remove(this);        
    	} else {            
        	//T attached = findCard(id);
            //entityManager.remove(attached);
        }
    }
        
    public void flush() {            	
        entityManager.flush();
    }
        
    public void clear() {            	
        entityManager.clear();
    }
        
    public T merge() {
            	
    	@SuppressWarnings("unchecked")
		T merged = entityManager.merge((T) this);        
        return merged;
    }
}
