/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package textformat;

public class StringFormat 
{
	
	// Method formate a json string
	public char[] formatJson(String json_string)
	{
		char ch[]=new char[0];
		try 
		{
			json_string=this.removeNewLineCharacter(json_string);
			ch=this.toCharArray(json_string);
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return ch;
	}
	
	// Method remove the new line characters
	public String removeNewLineCharacter(String json_string)
	{
		String str="";
		try 
		{
			char ch;
			for(int i=0; i<json_string.length(); i++)
			{
				ch=json_string.charAt(i);
				if(ch!='\n')
				{
					str+=ch;
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return str;
	}
	
	// Method convert a string a array of characters
	public char[] toCharArray(String str)
	{
		char ch[]=new char[str.length()];
		try 
		{
			
			for(int i=0; i<ch.length; i++)
			{
				ch[i]=str.charAt(i);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return ch;
	}
}
