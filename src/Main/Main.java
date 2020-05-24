/*
 * Name: Craig Dominic Dunbar
 * Company: Cyber Legacy Systems LLC
 * Location: United States of America, Metuchen, New Jersey,08840
 * Date: 05/17/2020
 */

package Main;

import objects.Dson;
import objects.DsonArray;
import objects.DsonObject;
import parser.DsonParser;
import writer.DsonWriter;

public class Main 
{
	// Main method
	public static void main(String[] args) 
	{
		try 
		{
			// Sample Json String from TDAmeritrade historical data
			
			String str1="{"+
					  '"'+"AAPL"+'"'+':'+" {"+
			    '"'+"assetType"+'"'+':'+' '+'"'+"EQUITY"+'"'+','+
			    '"'+"assetMainType"+'"'+':'+' '+'"'+"EQUITY"+'"'+','+
			    '"'+"cusip"+'"'+':'+' '+'"'+"037833100"+'"'+','+
			    '"'+"symbol"+'"'+':'+' '+'"'+"AAPL"+'"'+','+
			    '"'+"description"+'"'+':'+' '+'"'+"Apple Inc. - Common Stock"+'"'+','+
			    '"'+"bidPrice"+'"'+':'+' '+314.8+','+
			    '"'+"bidSize"+'"'+':'+' '+100+','+
			    '"'+"bidId"+'"'+':'+' '+'"'+"Q"+'"'+','+
			    '"'+"askPrice"+'"'+':'+' '+314.82+','+
			    '"'+"askSize"+'"'+':'+' '+100+','+
			    '"'+"askId"+'"'+':'+' '+'"'+"Z"+'"'+','+
			    '"'+"lastPrice"+'"'+':'+' '+314.8+','+
			    '"'+"lastSize"+'"'+':'+' '+100+','+
			    '"'+"lastId"+'"'+':'+' '+'"'+"Q"+'"'+','+
			    '"'+"openPrice"+'"'+':'+' '+313.17+','+
			    '"'+"highPrice"+'"'+':'+' '+314.97+','+
			    '"'+"lowPrice"+'"'+':'+' '+310.3241+','+
			    '"'+"bidTick"+'"'+':'+' '+'"'+" "+'"'+','+
			    '"'+"closePrice"+'"'+':'+' '+307.71+','+
			    '"'+"netChange"+'"'+':'+' '+7.09+','+
			    '"'+"totalVolume"+'"'+':'+' '+17372418+','+
			    '"'+"quoteTimeInLong"+'"'+':'+' '+Long.parseLong("1589820692775")+','+
			    '"'+"tradeTimeInLong"+'"'+':'+' '+Long.parseLong("1589820692698")+','+
			    '"'+"mark"+'"'+':'+' '+314.8+','+
			    '"'+"exchange"+'"'+':'+' '+'"'+"q"+'"'+','+
			    '"'+"exchangeName"+'"'+':'+' '+'"'+"NASD"+'"'+','+
			    '"'+"marginable"+'"'+':'+' '+true+','+
			    '"'+"shortable"+'"'+':'+' '+true+','+
			    '"'+"volatility"+'"'+':'+' '+0.0292+','+
			    '"'+"digits"+'"'+':'+' '+4+','+
			    '"'+"52WkHigh"+'"'+':'+' '+327.85+','+
			    '"'+"52WkLow"+'"'+':'+' '+170.27+','+
			    '"'+"nAV"+'"'+':'+' '+0+','+
			    '"'+"peRatio"+'"'+':'+' '+24.1228+','+
			    '"'+"divAmount"+'"'+':'+' '+3.28+','+
			    '"'+"divYield"+'"'+':'+' '+1.07+','+
			    '"'+"divDate"+'"'+':'+' '+'"'+"2020-02-07 00:00:00.000"+'"'+','+
			    '"'+"securityStatus"+'"'+':'+' '+'"'+"Normal"+'"'+','+
			    '"'+"regularMarketLastPrice"+'"'+':'+' '+314.8+','+
			    '"'+"regularMarketLastSize"+'"'+':'+' '+1+','+
			    '"'+"regularMarketNetChange"+'"'+':'+' '+7.09+','+
			    '"'+"regularMarketTradeTimeInLong"+'"'+':'+' '+Long.parseLong("1589820692698")+','+
			    '"'+"netPercentChangeInDouble"+'"'+':'+' '+2.3041+','+
			    '"'+"markChangeInDouble"+'"'+':'+' '+7.09+','+
			    '"'+"markPercentChangeInDouble"+'"'+':'+' '+2.3041+','+
			    '"'+"regularMarketPercentChangeInDouble"+'"'+':'+' '+2.3041+','+
			    '"'+"delayed"+'"'+':'+' '+true+
			  '}'+
			'}';
			
			// Reading from json string
			DsonParser dson=new DsonParser(str1);
			Object value=dson.getValue("AAPL.openPrice");
			System.out.println(value); // out put 313.17
			dson.setValue("AAPL.openPrice", 413.17); // assign 413.17 to openPrice
			value=dson.getValue("AAPL.openPrice");
			System.out.println(value); // output 413.17
			
			// writing java objects to a json string
			DsonObject mainobj=new DsonObject();
			Dson firstname=new Dson("first_name", "my firt name");
			Dson lastname=new Dson("last_name", "my last name");
			DsonObject address=new DsonObject();
			Dson location=new Dson("location", "usa");
			Dson city=new Dson("city", "New York");
			Dson zipcode=new Dson("zip_code", 80188);
			address.append(location);
			address.append(city);
			address.append(zipcode);
			mainobj.append(firstname);
			mainobj.append(lastname);
			mainobj.append("address", address);
			
			DsonWriter writer=new DsonWriter();
			String jsonstring=writer.buildDsonString(mainobj);
			System.out.println(jsonstring); 
			// output {"first_name":"my firt name","last_name":"my last name","address":{"location":"usa","city":"New York","zip_code":80188}}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
	}

}
