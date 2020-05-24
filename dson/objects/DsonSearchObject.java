/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/19/2020
 */

package objects;

import java.util.ArrayList;

public class DsonSearchObject 
{
	private ArrayList<Object> SEARCH_OBJECT;
	private char CH[]=null;
	private int INDEX=0;
	private int PIVOT=0;
	
	// Default constructor
	public DsonSearchObject(String search_path)throws Exception
	{
		this.SEARCH_OBJECT=new ArrayList<Object>();
		if(search_path!=null)
		{
			if(!search_path.isEmpty())
			{
				this.CH=new char[search_path.length()];
				this.characterConverter(search_path);
				this.pathParse();
			}
			else
			{
				throw new Exception("Search_Path can't be a empty String");
			}
		}
		else 
		{
			throw new Exception("Search_Path can't be a null String");
		}
	}
	
	// Method parses a string into characters 
	private void characterConverter(String search_path)throws Exception
	{
		char ch;
		for(int i=0; i<search_path.length(); i++)
		{
			ch=search_path.charAt(i);
			if(Character.isSpaceChar(ch))
			{
				throw new Exception("Search Path can't contain spaces");
			}
			else
			{
				this.CH[i]=ch;
			}
		}
	}
	
	// Method parses the path to search
	private void pathParse()throws Exception
	{
		while(this.INDEX<this.CH.length) 
		{
			if(this.CH[this.INDEX]!='[' && this.CH[this.INDEX]!='.')
			{
				String path=this.fieldPathParse();
				this.SEARCH_OBJECT.add(path);
			}
			else if(this.CH[this.INDEX]=='[')
			{
				this.INDEX++;
				int path=this.arrayPathParse();
				this.SEARCH_OBJECT.add(path);
			}
			else if(this.CH[this.INDEX]=='.')
			{
				this.INDEX++;
			}
		}
	}
	
	// Method parses a field path to search
	private String fieldPathParse()throws Exception
	{
		String field="";
		while(this.INDEX<this.CH.length)
		{
			if(this.CH[this.INDEX]=='.')
			{
				this.INDEX++;
				break;
			}
			else 
			{
				field+=this.CH[this.INDEX];
				this.INDEX++;
			}
		}
		return field;
	}
	
	// Method parses a array path to search
	private int arrayPathParse()throws Exception
	{
		int index=-1;
		String path="";
		while(this.INDEX<this.CH.length)
		{
			if(this.CH[this.INDEX]==']')
			{
				this.INDEX++;
				break;
			}
			else if(Character.isDigit(this.CH[this.INDEX])) 
			{
				path+=this.CH[this.INDEX];
				this.INDEX++;
			}
			else 
			{
				throw new Exception("Search Path of an array must be a digit");
			}
		}
		if(path.isEmpty())
		{
			throw new Exception("Search Path of an array must contain a digit");
		}
		else 
		{
			index=Integer.parseInt(path);
		}
		return index;
	}
	
////////////////////////////
// Get Method
///////////////////////////
	
	// Method get the next path
	public Object getNextPath()
	{
		Object path=null;
		if(this.hasNext())
		{
			this.PIVOT++;
			path=this.SEARCH_OBJECT.get(this.PIVOT);
		}
		return path;
	}
	
	// Method get the current path
	public Object getCurrentPath()
	{
		Object path=null;
		if(this.PIVOT>=0)
		{
			path=this.SEARCH_OBJECT.get(this.PIVOT);
		}
		return path;
	}
	
//////////////////////////////
// Other Methods
/////////////////////////////
	
	// Method determine if the search object have a next search path
	public boolean hasNext()
	{
		boolean bool=false;
		if(this.PIVOT<this.SEARCH_OBJECT.size()-1)
		{
			bool=true;
		}
		return bool;
	}
	
	// Method return a string of the dson search object
	public String toString()
	{
		String str="";
		for(int i=0; i<this.SEARCH_OBJECT.size(); i++)
		{
			if(i==this.SEARCH_OBJECT.size()-1)
			{
				str+=this.SEARCH_OBJECT.get(i);
			}
			else 
			{
				str+=this.SEARCH_OBJECT.get(i)+"->";
			}
		}
		return str;
	}
}
