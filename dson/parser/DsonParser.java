/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package parser;

import java.util.ArrayList;

import objects.Dson;
import objects.DsonArray;
import objects.DsonElement;
import objects.DsonObject;
import objects.DsonSearchObject;
import textformat.StringFormat;

public class DsonParser 
{
	private char CH[]; // holds the characts of the json string
	private int INDEX=0; // holds the index of where the parser currently parsing
	private DsonElement ROOT_ELEMENT;
	
	// Method create a dson object
	public DsonParser(String json_string)throws Exception
	{
		StringFormat format=new StringFormat();
		this.CH=format.formatJson(json_string);
		if(this.CH[this.INDEX]=='{')
		{
			this.INDEX++;
			DsonObject dsonobject=this.dsonObjectParse();
			this.ROOT_ELEMENT=new DsonElement(dsonobject);
		}
		else if(this.CH[this.INDEX]=='[')
		{
			this.INDEX++;
			DsonArray dsonarray=this.dsonArrayParse();
			this.ROOT_ELEMENT=new DsonElement(dsonarray);
		}
		else if(Character.isSpaceChar(this.CH[this.INDEX]))
		{
			this.INDEX++;
		}
		else 
		{
			throw new Exception("Error: not a json string exception");
		}
	}
	
	// Method create a dson
	private Dson dsonParse()throws Exception
	{
		Dson dson=new Dson();
		dson.setField(this.fieldParse());
		dson.setValue(this.valueParse());
		return dson;
	}
	
	// Method parses a json object
	private DsonObject dsonObjectParse()throws Exception
	{
		boolean add=false;
		Dson dson=new Dson();
		DsonObject dsonobject=new DsonObject();
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]=='}')
			{
				this.INDEX++;
				if(add)
				{
					dsonobject.append(dson);
				}
				return dsonobject;
			}
			else if(this.CH[this.INDEX]=='{') 
			{
				this.INDEX++;
				DsonObject obj=this.dsonObjectParse();
				dsonobject.append(dson.getField(), obj);
				add=false;
			}
			else if(this.CH[this.INDEX]=='[')
			{
				this.INDEX++;
				DsonArray array=this.dsonArrayParse();
				dsonobject.append(dson.getField(), array);
				add=false;
			}
			else if(add)
			{
				dsonobject.append(dson);
				add=false;
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]) || this.CH[this.INDEX]==',')
			{
				this.INDEX++;
			}
			else 
			{
				dson=this.dsonParse();
				add=true;
			}
		}
		return dsonobject;
	}
	
	// Method parses a json array
	private DsonArray dsonArrayParse()throws Exception
	{
		DsonArray dsonarray=new DsonArray();
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==']')
			{
				this.INDEX++;
				return dsonarray;
			}
			else if(this.CH[this.INDEX]==',')
			{
				this.INDEX++;
			}
			else if(this.CH[this.INDEX]=='{') 
			{
				this.INDEX++;
				DsonObject obj=this.dsonObjectParse();
				dsonarray.append(obj);
			}
			else if(this.CH[this.INDEX]=='[')
			{
				this.INDEX++;
				DsonArray array=this.dsonArrayParse();
				dsonarray.append(array);
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else 
			{
				Object value=this.valueParse();
				Dson dson=new Dson(value);
				dsonarray.append(dson);
			}
		}
		return dsonarray;
	}
	
	// Method parses a field
	private String fieldParse()throws Exception
	{
		String field="";
		int tempindex=this.INDEX;
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==':')
			{
				this.INDEX++;
				break;
			}
			else if(this.CH[this.INDEX]=='{' || this.CH[this.INDEX]==']' ||
					this.CH[this.INDEX]=='[' || this.CH[this.INDEX]==']' || 
					this.CH[this.INDEX]==',')
			{
				this.INDEX=tempindex;
				field=null;
				break;
			}
			else if(this.CH[this.INDEX]=='"' || Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
			else if(Character.isLetterOrDigit(this.CH[this.INDEX]) || this.CH[this.INDEX]=='_')
			{
				field+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return field;
	}
	
	// Method parses a value
	private Object valueParse()throws Exception
	{
		Object value=null;
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]=='{' || this.CH[this.INDEX]=='}' ||
					this.CH[this.INDEX]=='[' || this.CH[this.INDEX]==']' || 
					this.CH[this.INDEX]==',')
			{
				break;
			}
			else if(this.CH[this.INDEX]=='t' || this.CH[this.INDEX]=='f')
			{
				String result=this.booleanParse();
				if(result.equalsIgnoreCase("true"))
				{
					value=true;
				}
				else
				{
					value=false;
				}
			}
			else if(this.CH[this.INDEX]=='n') 
			{
				String result=this.nullParse();
				if(result.equalsIgnoreCase("null"))
				{
					value=null;
				}
			}
			else if(Character.isDigit(this.CH[this.INDEX]) || this.CH[this.INDEX]=='-')
			{
				String result=this.numberParse();
				if(result.contains(".") || result.contains("e") || result.contains("E"))
				{
					value=Double.parseDouble(result);
				}
				else if(result.length()>9)
				{
					value=Long.parseLong(result);
				}
				else 
				{
					value=Integer.parseInt(result);
				}
			}
			else if(this.CH[this.INDEX]=='"')
			{
				this.INDEX++;
				value=this.stringParse();
			}
			else if(Character.isSpaceChar(this.CH[this.INDEX]))
			{
				this.INDEX++;
			}
		}
		return value;
	}
	
	// Method parses a boolean
	private String booleanParse()throws Exception
	{
		String result="";
		while(this.INDEX<this.CH.length)
		{
			if(this.CH[this.INDEX]==',' || this.CH[this.INDEX]=='}' || this.CH[this.INDEX]==']')
			{
				break;
			}
			else 
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		if(!result.equalsIgnoreCase("true") && !result.equalsIgnoreCase("false"))
		{
			throw new Exception("Error: not a boolean exception");
		}
		return result;
	}
	
	// Method parses a null
	private String nullParse()throws Exception
	{
		String result="";
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==',' || this.CH[this.INDEX]=='}' || this.CH[this.INDEX]==']')
			{
				break;
			}
			else
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		if(!result.equalsIgnoreCase("null"))
		{
			throw new Exception("Error: not a null type exception");
		}
		return result;
	}
	
	// Method parses a number
	private String numberParse()throws Exception
	{
		String result="";
		while (this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]==',' || this.CH[this.INDEX]=='}' || this.CH[this.INDEX]==']')
			{
				break;
			}
			else if(Character.isDigit(this.CH[this.INDEX]) || this.CH[this.INDEX]=='-' ||
					this.CH[this.INDEX]=='+' || this.CH[this.INDEX]=='.' || 
					this.CH[this.INDEX]=='e' || this.CH[this.INDEX]=='E')
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
			else 
			{
				throw new Exception("Error: not a number exception");
			}
			
		}
		return result;
	}
	
	// Method parses a String
	private String stringParse()
	{
		String result="";
		while (this.INDEX<this.CH.length) 
		{
			if((this.CH[this.INDEX]=='"' && this.CH[(this.INDEX+1)]==',') || 
					(this.CH[this.INDEX]=='"' && this.CH[(this.INDEX+1)]=='}') || 
					(this.CH[this.INDEX]=='"' && this.CH[(this.INDEX+1)]==']'))
			{
				this.INDEX++;
				break;
			}
			else
			{
				result+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return result;
	}
	
////////////////////////////
// Set Method
////////////////////////////
	
	// Method set the field
	public void setField(String search_path, String field)throws Exception
	{
		DsonSearchObject search_object=new DsonSearchObject(search_path);
		this.ROOT_ELEMENT.setField(search_object, field);
	}
	
	// Method set the value
	public void setValue(String search_path, Object value)throws Exception
	{
		DsonSearchObject search_object=new DsonSearchObject(search_path);
		this.ROOT_ELEMENT.setValue(search_object, value);
	}
	
	
//////////////////////////
// Get Method
//////////////////////////
	
	// Method get the root element
	public DsonElement getRootElement()
	{
		return this.ROOT_ELEMENT;
	}
	
	// Method get the type the root element is
	public String getType()
	{
		return this.ROOT_ELEMENT.getType();
	}
	
	// Method get the types
	public ArrayList<String> getTypes()
	{
		ArrayList<String> types=this.ROOT_ELEMENT.getTypes();
		return types;
	}
	
	// Method get the fields
	public ArrayList<String> getFields()
	{
		ArrayList<String> fields=this.ROOT_ELEMENT.getFields();
		return fields;
	}
	
	// Method get the value of the object
	public Object getValue(String search_path)throws Exception
	{
		
		Object value=null;
		DsonSearchObject search_object=new DsonSearchObject(search_path);
		value=this.ROOT_ELEMENT.getValue(search_object);
		return value;
	}
	
	// Method get the values
	public ArrayList<Object> getValues()
	{
		ArrayList<Object> values=this.ROOT_ELEMENT.getValues();
		return values;
	}
	
////////////////////////////
// Other Methods
///////////////////////////
	
	// Method return a string of the elements
	public String toString()
	{
		return this.ROOT_ELEMENT.toString();
	}
	
}
