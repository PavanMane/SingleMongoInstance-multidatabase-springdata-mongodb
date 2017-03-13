package org.p1.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes({
    @CompoundIndex(name = "CX_Bottle_typeAsc_nameAsc",
                   unique = true,
                   def = "{'type' : 1, 'name' : 1}")
})
public class Bottle {
	
	public enum Type {
		SIPPER,
		PLASTIC,
		BPAFREE,
		;
	}

	@Id
	private String id;
	private Type type;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
