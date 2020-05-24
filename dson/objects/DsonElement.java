/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

import java.util.ArrayList;

public class DsonElement 
{
	private String TYPE;
	private Dson DSON;
	private DsonObject DSON_OBJECT;
	private DsonArray DSON_ARRAY;
	
	// Default constructor
	public DsonElement()throws Exception
	{
		this.TYPE=null;
		this.DSON=null;
		this.DSON_OBJECT=null;
		this.DSON_ARRAY=null;
	}
	
	// Constructor that takes a dson
	public DsonElement(Dson dson)throws Exception
	{
		this.TYPE="DSON";
		this.DSON=dson;
		this.DSON_OBJECT=null;
		this.DSON_ARRAY=null;
	}
	
	// Constructor that dson object
	public DsonElement(DsonObject dsonobject)throws Exception
	{
		this.TYPE="DSON_OBJECT";
		this.DSON=null;
		this.DSON_OBJECT=dsonobject;
		this.DSON_ARRAY=null;
	}
	
	// Constructor that takes a dson array
	public DsonElement(DsonArray dsonarray)throws Exception
	{
		this.TYPE="DSON_ARRAY";
		this.DSON=null;
		this.DSON_OBJECT=null;
		this.DSON_ARRAY=dsonarray;
	}

///////////////////////////////////
// Set Methods
//////////////////////////////////
	
	// Method set the dson
	public void setDson(Dson dson)throws Exception
	{
		this.DSON=dson;
	}
	
	// Method set the dson object
	public void setDsonObject(DsonObject dsonobject)throws Exception
	{
		this.DSON_OBJECT=dsonobject;
	}
	
	// Method set the dson array
	public void setDsonArray(DsonArray dsonarray)throws Exception
	{
		this.DSON_ARRAY=dsonarray;
	}
	
	// Method set the field
	public void setField(DsonSearchObject search_object, String field)throws Exception
	{
		if(this.DSON_OBJECT!=null) 
		{
			this.DSON_OBJECT.setField(search_object, field);
		}
		else if(this.DSON_ARRAY!=null)
		{
			this.DSON_ARRAY.setField(search_object, field);
		}
	}
	
	// Method set the value 
	public void setValue(DsonSearchObject search_object, Object value)throws Exception
	{
		if(this.DSON_OBJECT!=null) 
		{
			this.DSON_OBJECT.setValue(search_object, value);
		}
		else if(this.DSON_ARRAY!=null)
		{
			this.DSON_ARRAY.setValue(search_object, value);
		}
	}
	
/////////////////////////////////
// Get Method
////////////////////////////////
	
	// Method get the type
	public String getType()
	{
		return this.TYPE;
	}
	
	// Method get the dson
	public Dson getDson()
	{
		return this.DSON;
	}
	
	// Method get the dson object
	public DsonObject getDsonObject()
	{
		return this.DSON_OBJECT;
	}
	
	// Method get the dson array
	public DsonArray getDsonArray()
	{
		return this.DSON_ARRAY;
	}
	
	// Method get the fields
	public ArrayList<String> getTypes()
	{
		ArrayList<String> types=new ArrayList<String>();
		if(this.DSON!=null)
		{
			types.add(this.getType());
		}
		else if(this.DSON_OBJECT!=null) 
		{
			types.add(this.getType());
			ArrayList<String> objtypes=this.DSON_OBJECT.getTypes();
			for(int i=0; i<objtypes.size(); i++)
			{
				types.add(objtypes.get(i));
			}
		}
		else if (this.DSON_ARRAY!=null) 
		{
			types.add(this.getType());
			ArrayList<String> arraytypes=this.DSON_ARRAY.getTypes();
			for(int i=0; i<arraytypes.size(); i++)
			{
				types.add(arraytypes.get(i));
			}
		}
		return types;
	}
	
	// Method get the fields
	public ArrayList<String> getFields()
	{
		ArrayList<String> fields=new ArrayList<String>();
		if(this.DSON!=null)
		{
			fields.add(this.DSON.getField());
		}
		else if(this.DSON_OBJECT!=null) 
		{
			fields=this.DSON_OBJECT.getFields();
		}
		else if (this.DSON_ARRAY!=null) 
		{
			fields=this.DSON_ARRAY.getFields();
		}
		return fields;
	}
	
	// Method get the values
	public ArrayList<Object> getValues()
	{
		ArrayList<Object> values=new ArrayList<Object>();
		if(this.DSON!=null)
		{
			values.add(this.DSON.getValue());
		}
		if(this.DSON_OBJECT!=null) 
		{
			values=this.DSON_OBJECT.getValues();
		}
		else if (this.DSON_ARRAY!=null) 
		{
			values=this.DSON_ARRAY.getValues();
		}
		return values;
	}
	
	// Method get the value(s) in a object
	public Object getValue(DsonSearchObject search_object)throws Exception
	{
		Object value=null;
		if(this.DSON_OBJECT!=null) 
		{
			value=this.DSON_OBJECT.getValue(search_object);
		}
		else if(this.DSON_ARRAY!=null)
		{
			value=this.DSON_ARRAY.getValue(search_object);
		}
		return value;
	}

	
///////////////////////////////
// Other Methods
///////////////////////////////
	
	// Method return a string representation of a dson element
	public String toString()
	{
		String str="";
		if(this.DSON!=null)
		{
			str=this.DSON.toString();
		}
		else if(this.DSON_OBJECT!=null) 
		{
			str=this.DSON_OBJECT.toString();
		}
		else if (this.DSON_ARRAY!=null) 
		{
			str=this.DSON_ARRAY.toString();
		}
		return str;
	}
}
