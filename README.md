## Super Simple Stock Market

This is a test application that is used to simulate Stock Market environment.

## Built with
- [Java](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Lombok](https://projectlombok.org/)

## Installation 

For IntelliJ:

- clone repo or download zip and extract
- Use File -> Open or Import project
- Navigate to the pom.xml file inside the project
- Click enter and accept all the defaults
- Once project is setup in Intellij, you can run the SssmApplication class
 

## Design and Implementation
This sample application provides REST style API services to perform following operations:

[[ STOCK ]]

Stock is our POJO to model Stock/Share information. This represents static information about the stock such
as its symbol, shareType, lastDividend, fixedDividend, parValue which is not expected to change frequently

1. List Stocks: 						GET 	/stock/list
2. Retrieve details for a Stock: 		GET 	/stock/{symbol}  [Example: /stock/TEA]
3. Add new Stock:						PUT 	/stock
4. Update existing Stock:				PUT		/stock

[[ STOCKVALUE ]]

StockValue is our POJO that models the Stock Price information on a particular date. Stock prices are subject to fluctuation
depending on market conditions and as such StockValue represents an Immutable image of this image at the close of trading day.

5. Calcuate DividendYield 				GET		/stockValue/{symbol}/dividendYield?price=
6. Calcuate PeRatio 					GET		/stockValue/{symbol}/peRatio?price=

[[ TRADE ]]

Trade is our Pojo that models an actual Trade executed to Buy/Sell a certain quantity of Stock at a certain Price at a certain time.
Trade is immutable 

7. List all trades						GET 	/trade/list
8. List all trades in last n minutes	GET 	/trade/list?timeInMinutes=
9. Calcuate VolumeWeightedStockPrice 	GET		/trade/VolumeWeightedStockPrice/{timeinMinutes}
10. Calculate GBCEShareIndex			GET 	/trade/GBCEShareIndex
11. Record Trade BUY                    POST    /trade/BUY/{symbol}?price={}&quantity={} 
12. Record Trade SELL                   POST    /trade/SELL/{symbol}?price={}&quantity={} 


The entire application is designed as primarly 3 layers

a. Controller layer - responsible for handling incoming requests and interacting with other internal application modules to provide a response

b. Service layer - responsible to perform business functions

c. DAO/Respository layer - This layer abstracts underlying persistence store. In our example application we are using In-memory repository to store 
the data

Apart from the above layer we also have our models which encapsulate our entities such as Trade, Stock and StockValue

## Validation

The application data is validated using Spring by placing standard and custom validation constructs along the fields and parameters.
For validation purpose we use the Default validation group context for the test application.


