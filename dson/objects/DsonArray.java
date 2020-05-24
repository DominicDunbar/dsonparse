/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

import java.util.ArrayList;

public class DsonArray
{
	private ArrayList<DsonElement> DSON_ELEMENTS;
	
	// Default constructor
	public DsonArray()
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
	}
	
	// Constructor that takes a dson
	public DsonArray(Dson dson)throws Exception
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
		DsonElement element=new DsonElement(dson);
		this.DSON_ELEMENTS.add(element);
	}
	
	// Constructor that takes a dson object
	public DsonArray(DsonObject dsonobject)throws Exception
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
		DsonElement element=new DsonElement(dsonobject);
		this.DSON_ELEMENTS.add(element);
	}
	
	// Constructor that takes a dson array
	public DsonArray(DsonArray dsonarray)throws Exception
	{
		this.DSON_ELEMENTS=new ArrayList<DsonElement>();
		DsonElement element=new DsonElement(dsonarray);
		this.DSON_ELEMENTS.add(element);
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
		if(path instanceof Integer)
		{
			int index=(int)path;
			if(index<this.DSON_ELEMENTS.size())
			{
				if(!this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && search_object.hasNext())
				{
					search_object.getNextPath();
					this.DSON_ELEMENTS.get(index).setField(search_object, field);
				}
				else if(this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && !search_object.hasNext())
				{
					throw new Exception("Error: array field assignment exception");
				}
			}
			else 
			{
				throw new Exception("Error: array out of bounds exception");
			}
		}
		else 
		{
			throw new Exception("Error: array key exception");
		}
	}
	
	// Method set the value
	public void setValue(DsonSearchObject search_object, Object value)throws Exception
	{
		Object path=search_object.getCurrentPath();
		if(path instanceof Integer)
		{
			int index=(int)path;
			if(index<this.DSON_ELEMENTS.size())
			{
				if(!this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && search_object.hasNext())
				{
					search_object.getNextPath();
					this.DSON_ELEMENTS.get(index).setValue(search_object, value);
				}
				else if(this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && !search_object.hasNext())
				{
					Dson dson=this.DSON_ELEMENTS.get(index).getDson();
					dson.setValue(value);
				}
				else if(this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && search_object.hasNext())
				{
					throw new Exception("Error: non element exception");
				}
				else if(!this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && !search_object.hasNext())
				{
					throw new Exception("Error: non dson assignment exception");
				}
			}
			else 
			{
				throw new Exception("Error: array out of bounds exception");
			}
		}
		else 
		{
			throw new Exception("Error: array key exception");
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
			if(!this.DSON_ELEMENTS.get(i).getType().equalsIgnoreCase("DSON"))
			{
				ArrayList<String> elefields=this.DSON_ELEMENTS.get(i).getFields();
				for(int a=0; a<elefields.size(); a++)
				{
					fields.add(elefields.get(a));
				}
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
				Dson dson=this.DSON_ELEMENTS.get(i).getDson();
				values.add(dson.getValue());
			}
			else 
			{
				ArrayList<Object> elevalue=this.DSON_ELEMENTS.get(i).getValues();
				for(int a=0; a<elevalue.size(); a++)
				{
					values.add(elevalue.get(a));
				}
			}
		}
		return values;
	}
	
	// Method get the value contain in that particular array object
	public Object getValue(DsonSearchObject search_object)throws Exception
	{
		Object value=null;
		Object path=search_object.getCurrentPath();
		if(path instanceof Integer)
		{
			int index=(int)path;
			if(index<this.DSON_ELEMENTS.size())
			{
				if(!this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && search_object.hasNext())
				{
					search_object.getNextPath();
					value=this.DSON_ELEMENTS.get(index).getValue(search_object);
				}
				else if(this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && !search_object.hasNext())
				{
					Dson dson=this.DSON_ELEMENTS.get(index).getDson();
					value=dson.getValue();
				}
				else if(this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && search_object.hasNext())
				{
					throw new Exception("Error: non element exception");
				}
				else if(!this.DSON_ELEMENTS.get(index).getType().equalsIgnoreCase("DSON") && !search_object.hasNext())
				{
					throw new Exception("Error: non dson exception");
				}
			}
			else 
			{
				throw new Exception("Error: array out of bounds exception");
			}
		}
		else 
		{
			throw new Exception("Error: array key exception");
		}
		return value;
	}
	
//////////////////////////
// Other Methods
//////////////////////////
	
	// Method append a dson the arraylist of elements
	public void append(Dson dson)throws Exception
	{
		if(dson.getField()!=null)
		{
			throw new Exception("Error: dson array field exception");
		}
		else 
		{
			DsonElement element=new DsonElement(dson);
			this.DSON_ELEMENTS.add(element);
		}
	}
	
	// Method append a dson object the arraylist of elements
	public void append(DsonObject dsonobject)throws Exception
	{
		DsonElement element=new DsonElement(dsonobject);
		this.DSON_ELEMENTS.add(element);
	}
	
	// Method append a dson array the arraylist of elements
	public void append(DsonArray dsonarray)throws Exception
	{
		DsonElement element=new DsonElement(dsonarray);
		this.DSON_ELEMENTS.add(element);
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
