/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/22/2020
 */

package writer;

import java.util.ArrayList;

import objects.Dson;
import objects.DsonArray;
import objects.DsonElement;
import objects.DsonObject;

public class DsonWriter 
{
	
	// Method build a dson string from a dson object
	public String buildDsonString(DsonObject object)throws Exception
	{
		String dson_string=this.buildObject(object);
		return dson_string;
	}
	
	// Method build a dson string from a dson array
	public String buildDsonString(DsonArray array)throws Exception
	{
		String dson_string=this.buildArray(array);
		return dson_string;
	}
	
	// Method build the object
	private String buildObject(DsonObject object)throws Exception
	{
		String object_string="{";
		ArrayList<DsonElement> elements=object.getElements();
		for(int i=0; i<elements.size(); i++)
		{
			if(elements.get(i).getType().equalsIgnoreCase("DSON"))
			{
				if((i+1)<elements.size())
				{
					Dson dson=elements.get(i).getDson();
					if(elements.get((i+1)).getType().equalsIgnoreCase("DSON"))
					{
						String field=dson.getField();
						Object value=dson.getValue();
						if(value instanceof String)
						{
							object_string+='"'+field+'"'+':'+'"'+value+'"'+',';
						}
						else 
						{
							object_string+='"'+field+'"'+':'+value+',';
						}
					}
					else 
					{
						String field=dson.getField();
						object_string+='"'+field+'"'+':';
					}
				}
				else 
				{
					Dson dson=elements.get(i).getDson();
					String field=dson.getField();
					Object value=dson.getValue();
					if(value instanceof String)
					{
						object_string+='"'+field+'"'+':'+'"'+value+'"';
					}
					else 
					{
						object_string+='"'+field+'"'+':'+value;
					}
				}
			}
			else if(elements.get(i).getType().equalsIgnoreCase("DSON_OBJECT"))
			{
				DsonObject dsonobject=elements.get(i).getDsonObject();
				object_string+=this.buildObject(dsonobject);
				if(i!=(elements.size()-1))
				{
					object_string+=',';
				}
			}
			else if(elements.get(i).getType().equalsIgnoreCase("DSON_ARRAY"))
			{
				DsonArray dsonarray=elements.get(i).getDsonArray();
				object_string+=this.buildArray(dsonarray);
				if(i!=(elements.size()-1))
				{
					object_string+=',';
				}
			}
		}
		object_string+='}';
		return object_string;
	}
	
	// Method build the array
	private String buildArray(DsonArray array)throws Exception
	{
		String array_string="[";
		ArrayList<DsonElement> elements=array.getElements();
		for(int i=0; i<elements.size(); i++)
		{
			if(elements.get(i).getType().equalsIgnoreCase("DSON"))
			{
				if((i+1)<elements.size())
				{
					Dson dson=elements.get(i).getDson();
					if(elements.get((i+1)).getType().equalsIgnoreCase("DSON"))
					{
						Object value=dson.getValue();
						if(value instanceof String)
						{
							array_string+=""+'"'+value+'"'+',';
						}
						else 
						{
							array_string+=""+value+',';
						}
					}
					else 
					{
						Object value=dson.getValue();
						if(value instanceof String)
						{
							array_string+=""+'"'+value+'"'+',';
						}
						else 
						{
							array_string+=""+value+',';
						}
					}
				}
				else 
				{
					Dson dson=elements.get(i).getDson();
					Object value=dson.getValue();
					if(value instanceof String)
					{
						array_string+=""+'"'+value+'"';
					}
					else 
					{
						array_string+=""+value;
					}
				}
			}
			else if(elements.get(i).getType().equalsIgnoreCase("DSON_OBJECT"))
			{
				DsonObject dsonobject=elements.get(i).getDsonObject();
				array_string+=this.buildObject(dsonobject);
				if(i!=(elements.size()-1))
				{
					array_string+=',';
				}
			}
			else if(elements.get(i).getType().equalsIgnoreCase("DSON_ARRAY"))
			{
				DsonArray dsonarray=elements.get(i).getDsonArray();
				array_string+=this.buildArray(dsonarray);
				if(i!=(elements.size()-1))
				{
					array_string+=',';
				}
			}
		}
		array_string+=']';
		return array_string;
	}
}
