# Inventory Management System

Inventory  will  manage  an  in-memory  database  of  items  in  a  shop.  It  will  operate  upon  this  database by 
reading and executing commands from an input file named in.txt. The program will also  produce  results,  which  will  
be  written  to  a  file  named  out.txt.  For  this  program,  it  is  sufficient to store the records in a simple 
in-memory Java collection in the order they are added (e.g., an ArrayList would be a good choice).

nventory  must  support  the  following  commands.  <X>  represents  an  argument  passed  to  the command,  
e.g.,  5,  i7-8700K,  18/2/1995,  “Intel  Corp”.  Arguments  will  be  given  without  angle  brackets  and  
will  be  separated  by  whitespace;  arguments  that  contain  whitespace  must  be  double-quoted. For  example,  
a  company  name  that  has  a  space.  Arguments will  not  contain  escaped whitespace (e.g. '\ ') or 
escaped quotes (e.g. \").   

### LOAD <filename.csv>
* Loads the contents of database from the named file, replacing the existing contents.
* The file is in a comma-separated value (CSV) format, with one entry per line.
* Each entry matches the following format exactly. <Name>,<Company>,<ReleaseDate>,<Quantity> 
* Name and Company are strings. They may not contain commas or double quotes. 
* ReleaseDate is given as MM/DD/YYYY.●Quantity is a number.
* At  the  end  of  the  file,  there  is  exactly  one  blank  line  (i.e.,  the  text  of  all  entries  is  followed by a new-line character \n).
* You can assume the CSV file is always given in the correct format. Example of an entryin the CSV file: i7-8700K,Intel Corp,05/02/2017,12 
* The following line should be written to the output file, where N is the number of records loaded:LOAD: OK <N>


### STORE <filename.csv>
* Stores the contents of the database in the named file. Overwrites if the file already exists.
* The format must be exactly as described above.
* The following line should be written to the output file (not the csv file!), where N is the number of records stored: STORE: OK <N>CLEAR
* Clears the contents of the database.
* The following line should be written to the output file:CLEAR: OK

### ADD <Name> <Company> <ReleaseDate>
* Add the given entry to the database.●Quantity is initialized to 0. An entry for an item must be added before BUY or SELL.
* Duplicate entries are not allowed, i.e., adding the same NAME/COMPANY combo twice results in an error. (This also applies if the items were loaded from a CSV file.)

### STATUS
* Lists all added items.
* STATUS will write the following to the output file, where M is the number of items.
* STATUS: OK <M><Name1>,<Company1>,<ReleaseDate1>,<Quantity1><Name2>,<Company2>,<ReleaseDate2>,<Quantity2>... <NameM>,<CompanyM>,<ReleaseDateM>,<QuantityM>

### BUY <Name> <Company> <Quantity>
* Buys  an  item.  BUY  searches  for  the  record  identified  by  Name  Company  and  adds  the  specified quantity to the old quantity.
* Quantity should be >= 1.
* The following line should be written to the output file:BUY: OK <Name> <Company> <UpdatedQuantity> 

### SELL <Name> <Company> <Quantity>
* Sells  an  item.  SELL  searches  for  the  record  identified  by  Name  Company  and  subtracts  the specified quantity to the old quantity.
* <Quantity> should be >= 1. ●Cannot sell a quantity more than the quantity in the inventory.
* The following line should be written to the output file:SELL: OK <Name> <Company> <UpdatedQuantity>

### QUAN GREATER <Quantity> QUAN FEWER <Quantity>QUAN BETWEEN <Quantity1> <Quantity2>
* These  commands  list  all  items  that  are  greater  than,  fewer  than  or  in  between  the  specified quantities.
* Note:  GREATER  means  >,  not  >=.  Similarly implement FEWER  and  BETWEEN  by ignoring the equivalent case. 
* The following will be written to the output file, where M is the number of items matching the request:QUAN: OK <M><Name1>,<Company1>,<ReleaseDate1>,<Quantity1><Name2>,<Company2>,<ReleaseDate2>,<Quantity2>... <NameM>,<CompanyM>,<ReleaseDateM>,<QuantityM>

### SEARCH <Needle>
* Search for Needle as a substring in either Name or Company.
* The  following  lines  will  be  written  to  the  output  file.  The  format  for  each  record  is  the  CSV format as described for LOAD. M is the number of matching records:SEARCH: OK <M> <Name1>,<Company1>,<ReleaseDate1>,<Quantity1><Name2>,<Company2>,<ReleaseDate2>,<Quantity2>... <NameM>,<CompanyM>,<ReleaseDateM>,<QuantityM>

## Credits
* Question taken from ECE 5510 Virginia Tech