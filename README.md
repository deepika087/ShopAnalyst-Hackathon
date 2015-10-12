# ShopAnalyst-Hackathon
Spring boot project and mongo backend to implement ShopAnalyst hackerearth hackathon

Problem statement
Member Miner — a quest to build feature-rich REST service!
[Problem Statement]
Develop a backend application which would process given member data and enable feature-rich REST API.
[Minimum Requirement]
— Batch process given data from Mini-SQL Dump (or CSV) (having 1000 records).
•	Data Parameters: members[] —> { id, caption, ethnicity, weight, height, is_veg, drink, dob}
•	caption AS text (profile description)
•	ethnicity AS integer (1-9).
•	weight AS integer (in grams)
•	height AS integer (in centimetre)
•	is_veg AS boolean (0/1)
•	drink AS boolean (0/1)
•	dob AS date
•	^ Some parameters may have null value in it; handle it carefully!
— Store data into your preferred SQL/NoSQL storage. (i.e. MySQL, Postgres, SQLite, Cassandra or MongoDB).
• convert ethnicity from integer to Text (mapping given under guide/gist).
• convert weight from grams to Kilograms.
— Emphasis on functional backend RESTful service developed in your favourite backend language (i.e. Python, Java, PHP or Node.js).
— Functionally interactive API designed with basic function to retrieve member’s data (JSON response).
- Full text search from caption# /search_caption?query=<?>
- API call responding total number of data (records) # /count
— Submit Documentation and Source code with instruction.
[Plus Point]
— a feature to add paging parameter (and limiting output to certain records).
- Limiting response to max. 10 records and add paging: /search_caption?query=vegas&page=1
— Additional API function:
- Find out average height and weight of given ethnic category # /ethnicity//averages
•	^ Output response should contain mean height and mean weight of specific ethnic group.
— Enhance application to process and handle 1-million data set.
— Easy setup with instructions and documentation to make deployment easier.
— Use your imagination and add features which would make feature rich backend app; we always admire creativity.
[Advance Level] (Optional / If time permits)
— Extra API functions:
- Search users based on social habits (yes/no)# /social_habits?vegetarian=<?>&drink=<?>
— Handle Input Exceptions, Unicode and Null values. Beautify; Comment; Documented code.
— Awesome If you could manage to host this application somewhere with Demo URL!
— A feature to download API response (as PDF).
• Example (Added response parameter): /search_caption?query=vegas&response=PDF
— You may add portfolio activity comprising awesome work you have done on backend applications.
[Guide]
— Member Data Chunk: http://hackerearth.0x10.info/api/shopalyst/
•	DataSet 1 (Begineer/Intermediate): containing 1,000 Records.
•	DataSet 2 (If time permits): containing ~1 Million Records.
•	Ethnic group conversion: https://gist.github.com/mayurah/ade1e2666b651b7e5c4a#file-ethnic-group-csv
— For any query/support/help, Buzz us at IRC: http://webchat.freenode.net/?channels=hackerearth&uio=d4
— Emphasis on backend processing and API. You may use AWS or Heroku and provide Demo URL in Instructions attached with Source archive.
•	https://aws.amazon.com/free/
•	https://signup.heroku.com/www-pricing-top
— You are free to use more robust RESTful URL hierarchy; given structure is to help you kickstart with the development.
— Ideal Stack:
•	Ideal Back-end technology stack# Any SQL/NoSQL with Python, PHP, Node.js, Java, Ruby to make deployment easeful!
— You may use below resources for misc. things:
http://www.django-rest-framework.org/
http://www.tutorialspoint.com/nodejs/nodejs_restful_api.htm
http://codebeautify.org/jsonvalidate
[Wireframe MVP / Reference Structure]
•	Data-1 ( < 100KB ):
•	member_details with ~1,000 records (Format CSV or MySQL Dump).
•	Data-2 (~60MB Uncompressed / 38MB compressed):
•	member_details with ~9,40,684 records (Format CSV or MySQL Dump).

Task Flow#
1.	Download/Crawl member data, process and store it into preferred storage medium (Any SQL/ NoSQL).
2.	Build RESTful application serving various API requests on top of stored data.
3.	Add miscellaneous features into REST API (Record Limit, PDF, Unicode support).
4.	Submit Documented Instructions and Code.

