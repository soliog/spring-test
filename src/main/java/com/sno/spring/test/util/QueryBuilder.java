package com.sno.spring.test.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Query;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;


public class QueryBuilder<T> {
	
	private static final int MAX_LEVEL = 1;
	
	private List<FieldInfo> properties;
	
	public List<T> getResultList(T obj, EntityManager em) throws Exception {
		
		Query qry = buildQuery(obj, em);
		return (List<T>) qry.getResultList();	
	}
	
	public T getSingleResult(T obj, EntityManager em) throws Exception {
		
		Query qry = buildQuery(obj, em);
		return (T) qry.getSingleResult();	
	}
			
	public Query buildQuery(T obj, EntityManager em) throws Exception {
		
		StringBuilder sb = new StringBuilder();
		
		String p1 = null;
		ArrayList<Object> params = new ArrayList<Object>();
		Object v = null;
		for (FieldInfo p : getProperties(obj)) {						
			
			try {
				v = PropertyUtils.getNestedProperty(obj, p.property);
				if (v != null && 
					(!(v instanceof Number) || ((Number) v).doubleValue() != 0)) {
					
					if (sb.length() > 0) {
						sb.append(" and ");
					}
					
					if (p.idColumn) {
						
						 if (p.level == 0) {
							 
							 sb.append("e=?");
							 params.add(obj);
						 }
						 else {
							 
							 p1 = p.property.substring(0, p.property.lastIndexOf('.'));
							 sb.append(p1).append("=?");
							 params.add(PropertyUtils.getNestedProperty(obj, p1));
						 }
					}
					else {
						
						sb.append(p.property).append("=?");
						params.add(v);
					}
				}							
			} catch (NestedNullException e) {}							
		}
		
		Query qry = em.createQuery(
			" from " + obj.getClass().getName() + " e where " + sb.toString());
		for (int i=0; i<params.size(); i++) {
			qry.setParameter(i+1, params.get(i));
		}			
		
		return qry;
	}
	
	private List<FieldInfo> getProperties(T obj) {
		
		if (properties == null) {
			properties = getProperties(obj.getClass(), "", 0);
		}
		return properties;
	}
		
	private List<FieldInfo> getProperties(Class<?> clazz, String prefix, int level) {

		List<FieldInfo> props = new ArrayList<FieldInfo>();
		
		Column anColumn = null;
		JoinColumn anJoinColumn = null;    	
    	for (Field f : clazz.getDeclaredFields()) {
    			    		
    		anColumn = f.getAnnotation(Column.class);        		    		
    		if (anColumn != null) {    			
    			
    			props.add(this.new FieldInfo(prefix + f.getName(), 
    				(f.getAnnotation(Id.class) != null), level));
    		}
    		else {
    			
    			anJoinColumn = f.getAnnotation(JoinColumn.class);
    			if (level <= MAX_LEVEL && anJoinColumn != null) {	    				    				
	    			props.addAll(getProperties(f.getType(), prefix + f.getName() + ".", level+1));	    			
    			}
    		}    		    		
    	}    					
		return props;
	}			
	
	private class FieldInfo {
		
		private String property;
		private boolean idColumn;
		private int level;
		
		private FieldInfo(String property, boolean idColumn, int level) {
			this.property = property;
			this.idColumn = idColumn;
			this.level = level;
		}
	}
}
