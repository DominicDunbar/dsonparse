# dsonparse
Dson parse is a Json String Converter and Writer for Java that converts Json String into Java objects and Java Objects into Json String.


Converting a Json String into java objects
Create a DsonParser with your Json String
ex: DsonParser dson=new DsonParser(your Json String);


How to get a value from a un-nested DsonObject
Ex: Object value = dson.getValue("field_name");


How to get a value from a nested DsonObject
Ex: Object value = dson.getValue("field_name1.field_name2");


How to get a value from a DsonArray
Ex: Object value = dson.getValue("[index]");

How to get a value from a 2Dimensional Array
Ex: Object value = dson.getValue("[row][column]");

How to get a value from a complex DsonObject that
has an inner DsonObect that has a 2Dimensional array
Ex: Object value = dson.getValue("field_name1.field_name2.[row][column]");

How to get a value from a complex DsonObect that
has an inner DsonObject that has a array that contains
DsonObjects
Ex: Object value = dson.getValue("field_name1.field_name2.[index].field_name3");


How to assign a value to a specific field
using the same field path pattern shown above in
getting values we call the setValue(field_path, object);
Ex: dson.setValue("field_name", value);


note: if you try to access fields that don't exists 
Exception will be thrown


How to get all the values from the JsonString that is 
contain in the DsonParser
Ex: ArrayList<Object> values=dson.getValues();


How to get all Fields
Ex: ArrayList<String> fields=dson.getFields();


How to get the types of DsonElements they are in the
JsonString
Ex: ArrayList<String> types=dson.getTypes();



How to create a Json String
First create your DsonArray or DsonObject

Ex: 
DsonObject mainobj=new DsonObject();
DsonObject subobj=new DsonObject();
DsonArray  array=new DsonArray();

Dson dson1=new Dson(field_name, value);
Dson dson2=new Dson(field_name, value);
Dson dson3=new Dson(field_name, value);
Dson dson4=new Dson(field_name, value);
Dson dson5=new Dson(value);
Dson dson6=new Dson(value);

mainobj.append(dson1);
mainobj.append(dson2);

subobj.append(dson3)
subobj.append(dson4)

array.append(dson5)
array.append(dson6)

subobj.append("field_name", array);
mainobj.append("field_name", subobj);

To create the String from the object above
DsonWriter writer=new DsonWriter();
String jsonString=writer.buildDsonString(mainobj);

Note:
All DsonObject's Dson must have field names or 
Exceptions will be thrown.

Also DsonArray Dson can't contain field names or
Exceptions will be thrown.
