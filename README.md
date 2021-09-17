# Spring-boot-crud Coding Project 

# Information
## Backend
The backend is accessed through localhost:8080/api

## Suppliers
The suppliers endpoints are:
- GET /suppliers: Get all suppliers 
- GET /suppliers/<id> : GET supplier with id:<id> 
- POST /suppliers <json data>: Create new supplier from data
- PUT /suppliers/<id> <json data>: Update supplier with id:<id>
- DELETE /suppliers/<id>: Delete supplier with id:<id>
- GET /suppliers/search/findByCompanyName?companyName=<companyName>: find suppliers from company <companyName>
- GET /suppliers/search/findByVatNumber?vatNumber=<vatNumber>: find suppliers with VAT number
- GET /suppliers/search/findByQuery?query=<query> find suppliers using companyName or vatNumber

# Deploying
## Backend
Before deploying to production go to PersonRepository.java and SupplierRepository.java from springbootcrud-service module and change 
`@CrossOrigin(origins = "http://localhost:9000")
to 
`@CrossOrigin(origins = "http://localhost")

To deploy the backend execute
``` 
git clone http://www.github.com//springbootcrud.git
cd springbootcrud
mvn package
java -jar springbootcrud/springbootcrud-webapp/target/springbootcrud-webapp-1.0-SNAPSHOT.jar
```

## Frontend
```
cd springbootcrud/cpringbootcrud-client
npm install 
npm run build
```
copy contents of dist directory to web server


# Thoughts
In FindByQuery method in Service because we are are using "LIKE (% <term> %)" in the query we are doing a full text search which is slow and resource hungry. If we could change it to "LIKE ( <term> %)" so that it searches based on the first letters/numbers we could index the columns companyName and vatNumber.

In the frontend I also added a clear button for the search input
