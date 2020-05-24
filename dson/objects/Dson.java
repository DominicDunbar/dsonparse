/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package objects;

public class Dson 
{
	private String FIELD;
	private Object VALUE;
	
	// Default constructor
	public Dson()throws Exception
	{
		this.FIELD=null;
		this.VALUE=null;
	}
	
	// Constructor that takes a field
	public Dson(String field)throws Exception
	{
		this.FIELD=field;
		this.VALUE=null;
	}
	
	// Constructor that takes a value
	public Dson(Object value)throws Exception
	{
		this.FIELD=null;
		this.VALUE=value;
	}
	
	// Constructor that takes a field and value
	public Dson(String field, Object value)throws Exception
	{
		this.FIELD=field;
		this.VALUE=value;
	}
	
///////////////////////////////
// Set Methods
/////////////////////////////
	
	// Method set the field
	public void setField(String field)throws Exception
	{
		this.FIELD=field;
	}
	
	// Method set the value
	public void setValue(Object value)throws Exception
	{
		this.VALUE=value;
	}
	
////////////////////////////////
// Get Methods
////////////////////////////////
	
	// Method get the field
	public String getField()
	{
		return this.FIELD;
	}
	
	// Method get the value
	public Object getValue()
	{
		return this.VALUE;
	}
	
////////////////////////////
// Other Methods
///////////////////////////
	
	// to string method
	public String toString()
	{
		String str="";
		if(this.FIELD!=null)
		{
			str=this.FIELD;
		}
		if(this.VALUE!=null)
		{
			str+=":"+this.VALUE;
		}
		return str;
	}
}
