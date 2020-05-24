/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

import java.util.ArrayList;

public class DsonObject 
{
	private ArrayList<DsonElement> DSON_ELEMENTS;
	
	// Default constructor
	public DsonObject()
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
	}
	
	// Constructor that takes a dson
	public DsonObject(Dson dson)throws Exception
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
		if(dson.getField()==null || dson.getField().isEmpty())
		{
			throw new Exception("Error: Dson Object must have field exception");
		}
		else 
		{
			DsonElement element=new DsonElement(dson);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
	// Constructor that takes a dson object
	public DsonObject(String field, DsonObject dsonobject)throws Exception
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
		if(field==null || field.isEmpty())
		{
			throw new Exception("Error: Dson Object must have a field exception");
		}
		else 
		{
			Dson dson=new Dson(field);
			DsonElement fieldelement=new DsonElement(dson);
			DsonElement element=new DsonElement(dsonobject);
			this.DSON_ELEMENTS.add(fieldelement);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
	// Constructor that takes a dson array
	public DsonObject(String field, DsonArray dsonarray)throws Exception
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
		if(field==null || field.isEmpty())
		{
			throw new Exception("Error: Dson Object must have a field exception");
		}
		else 
		{
			Dson dson=new Dson(field);
			DsonElement fieldelement=new DsonElement(dson);
			DsonElement element=new DsonElement(dsonarray);
			this.DSON_ELEMENTS.add(fieldelement);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
/////////////////////////
// Set Methods
/////////////////////////
	
	// Method set a element
	public void setElement(int index, DsonElement element)throws Exception
	{
		this.DSON_ELEMENTS.set(index, element);
	}
	
	// Method set the field
	public void setField(DsonSearchObject search_object, String field)throws Exception
	{
		Object path=search_object.getCurrentPath();
		if(path instanceof String)
		{
			int index=-1;
			Dson dson=null;
			for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
			{
				if(this.DSON_ELEMENTS.get(i).getType().equalsIgnoreCase("DSON"))
				{
					dson=this.DSON_ELEMENTS.get(i).getDson();
					if(dson.getField().equals(path))
					{
						index=i;
						break;
					}
				}
			}
			if(index>-1)
			{
				if(!search_object.hasNext())
				{
					dson.setField(field);
				}
				else if((index+1)<this.DSON_ELEMENTS.size() && search_object.hasNext())
				{
					search_object.getNextPath();
					this.DSON_ELEMENTS.get((index+1)).setField(search_object, field);
				}
			}
			else 
			{
				throw new Exception("Error: path not found exception");
			}
		}
		else 
		{
			throw new Exception("Error: key field exception");
		}
	}
	
	// Method set the value
	public void setValue(DsonSearchObject search_object, Object value)throws Exception
	{
		Object path=search_object.getCurrentPath();
		if(path instanceof String)
		{
			int index=-1;
			Dson dson=null;
			for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
			{
				if(this.DSON_ELEMENTS.get(i).getType().equalsIgnoreCase("DSON"))
				{
					dson=this.DSON_ELEMENTS.get(i).getDson();
					if(dson.getField().equals(path))
					{
						index=i;
						break;
					}
				}
			}
			if(index>-1)
			{
				if((index+1)<this.DSON_ELEMENTS.size() && !search_object.hasNext())
				{
					if(!this.DSON_ELEMENTS.get((index+1)).getType().equalsIgnoreCase("DSON"))
					{
						throw new Exception("Error: value assignment exception");
					}
					else 
					{
						dson.setValue(value);
					}
				}
				else if((index+1)<this.DSON_ELEMENTS.size() && search_object.hasNext())
				{
					if(!this.DSON_ELEMENTS.get((index+1)).getType().equalsIgnoreCase("DSON"))
					{
						search_object.getNextPath();
						this.DSON_ELEMENTS.get((index+1)).setValue(search_object, value);
					}
					else 
					{
						throw new Exception("Error: non element exception");
					}
				}
				else if(index==this.DSON_ELEMENTS.size()-1 && !search_object.hasNext())
				{
					dson.setValue(value);
				}
				else if(index==this.DSON_ELEMENTS.size()-1 && search_object.hasNext())
				{
					throw new Exception("Error: out of bounds element exception");
				}
			}
			else 
			{
				throw new Exception("Error: path not found exception");
			}
		}
		else 
		{
			throw new Exception("Error: key field exception");
		}
	}
	
//////////////////////////
// Get Methods
//////////////////////////
	
	// Method get the dson object
	public ArrayList<DsonElement> getElements()
	{
		return this.DSON_ELEMENTS;
	}
	
	// Method get the fields
	public ArrayList<String> getTypes()
	{
		ArrayList<String> types=new ArrayList<String>();
		for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
		{
			ArrayList<String> eletypes=this.DSON_ELEMENTS.get(i).getTypes();
			for(int a=0; a<eletypes.size(); a++)
			{
				types.add(eletypes.get(a));
			}
		}
		return types;
	}
	
	// Method get the fields
	public ArrayList<String> getFields()
	{
		ArrayList<String> fields=new ArrayList<String>();
		for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
		{
			ArrayList<String> elefields=this.DSON_ELEMENTS.get(i).getFields();
			for(int a=0; a<elefields.size(); a++)
			{
				fields.add(elefields.get(a));
			}
		}
		return fields;
	}
	
	// Method get the values
	public ArrayList<Object> getValues()
	{
		ArrayList<Object> values=new ArrayList<Object>();
		for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
		{
			if(this.DSON_ELEMENTS.get(i).getType().equalsIgnoreCase("DSON"))
			{
				if((i+1)<this.DSON_ELEMENTS.size())
				{
					if(this.DSON_ELEMENTS.get((i+1)).getType().equalsIgnoreCase("DSON"))
					{
						Dson dson=this.DSON_ELEMENTS.get(i).getDson();
						values.add(dson.getValue());
					}
					else
					{
						ArrayList<Object> elevalue=this.DSON_ELEMENTS.get((i+1)).getValues();
						for(int a=0; a<elevalue.size(); a++)
						{
							values.add(elevalue.get(a));
						}
					}
				}
				else
				{
					Dson dson=this.DSON_ELEMENTS.get(i).getDson();
					values.add(dson.getValue());
				}
			}
		}
		return values;
	}
	
	// Method get the value contain in that particular object
	public Object getValue(DsonSearchObject search_object)throws Exception
	{
		Object value=null;
		Object path=search_object.getCurrentPath();
		if(path instanceof String)
		{
			int index=-1;
			Dson dson=null;
			for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
			{
				if(this.DSON_ELEMENTS.get(i).getType().equalsIgnoreCase("DSON"))
				{
					dson=this.DSON_ELEMENTS.get(i).getDson();
					if(dson.getField().equals(path))
					{
						index=i;
						break;
					}
				}
			}
			if(index>-1)
			{
				if((index+1)<this.DSON_ELEMENTS.size() && !search_object.hasNext())
				{
					if(!this.DSON_ELEMENTS.get((index+1)).getType().equalsIgnoreCase("DSON"))
					{
						throw new Exception("Error: non Dson exception");
					}
					else 
					{
						value=dson.getValue();
					}
				}
				else if((index+1)<this.DSON_ELEMENTS.size() && search_object.hasNext())
				{
					if(!this.DSON_ELEMENTS.get((index+1)).getType().equalsIgnoreCase("DSON"))
					{
						search_object.getNextPath();
						value=this.DSON_ELEMENTS.get((index+1)).getValue(search_object);
					}
					else 
					{
						throw new Exception("Error: non Element exception");
					}
				}
				else if(index==this.DSON_ELEMENTS.size()-1 && !search_object.hasNext())
				{
					value=dson.getValue();
				}
				else if(index==this.DSON_ELEMENTS.size()-1 && search_object.hasNext())
				{
					throw new Exception("Error: out of bounds Element exception");
				}
			}
			else 
			{
				throw new Exception("Error: path not found exception");
			}
		}
		else 
		{
			throw new Exception("Error: key field exception");
		}
		return value;
	}
	
//////////////////////////
// Other Methods
//////////////////////////
	
	// Method append a dson the arraylist of elements
	public void append(Dson dson)throws Exception
	{
		if(dson.getField()==null || dson.getField().isEmpty())
		{
			throw new Exception("Error: Dson Object must have field exception");
		}
		else 
		{
			DsonElement element=new DsonElement(dson);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
	// Method append a dson object the arraylist of elements
	public void append(String field, DsonObject dsonobject)throws Exception
	{
		if(field==null || field.isEmpty())
		{
			throw new Exception("Error: Dson Object must have a field exception");
		}
		else 
		{
			Dson dson=new Dson(field);
			DsonElement fieldelement=new DsonElement(dson);
			DsonElement element=new DsonElement(dsonobject);
			this.DSON_ELEMENTS.add(fieldelement);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
	// Method append a dson array the arraylist of elements
	public void append(String field, DsonArray dsonarray)throws Exception
	{
		if(field==null || field.isEmpty())
		{
			throw new Exception("Error: Dson Object must have a field exception");
		}
		else 
		{
			Dson dson=new Dson(field);
			DsonElement fieldelement=new DsonElement(dson);
			DsonElement element=new DsonElement(dsonarray);
			this.DSON_ELEMENTS.add(fieldelement);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
	// Method return a string of dson objects
	public String toString()
	{
		String str="";
		for(int i=0; i<this.DSON_ELEMENTS.size(); i++)
		{
			if(i==this.DSON_ELEMENTS.size()-1)
			{
				str+=this.DSON_ELEMENTS.get(i).toString();
			}
			else 
			{
				str+=this.DSON_ELEMENTS.get(i).toString()+",";
			}
		}
		return str;
	}
}
