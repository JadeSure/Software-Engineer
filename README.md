# Software-Engineer 
This project includes use case diagrams, user stories with class and object diagram, design patterns and principles, sequence and communication diagrams, state diagrams, and activity diagrams. Besides, it also includes Junit for automatically testing.  

Agile: 
1. Based on iterative and incremental development  
2. Most important phrases  
2.1 Self-organizing, cross-functional teams.  
2.2 Adaptive planning
2.3 incremental development and delivery
2.4 fixed timeslots and iterative approach
2.5 fast and flexible response to change


## Description:
Project 4: Business Intelligence System
A supermarket wants to introduce a business intelligence system allowing purchasing patterns of customers to be analysed. The swipe-card used by customers capture the address (post-code) and other relevant details of customers. To incentivise the use of the card, customers get 1 point for every $10 spent and are automatically discounted $5 for every 20 points. The system stores the product, customer, transaction and employee related details allowing necessary sales reports to be generated for business intelligence. Data may be stored in a relational database, flat files or through serialization. The system should store product, customer and employee related details allowing necessary reports to be generated.
** Current Limitations
A typical checkout terminal consists of a touch screen and a barcode reader but for this assignment all data will be entered through the keyboard (including the customer card number and product ID). Assume the customer is required to specify the customer number thus allowing customer details to be stored.

Project Requirements A typical customer:
• I want to be able to select the product name from a given list.  
• I want to be able check the price of any item by keying in the ID before proceeding
with the sale  
• I want to check the discounts applicable for a specific product when purchases in
bulk  
The sales staff (Kim):  
• I want to be able to login to the system the system and override the transaction details (removing item, cancellation) in case a customer has problems.  
The warehouse staff (Frank):  
I want to be able to replenish stock levels before placing items received on the shelves.  
The manager (Tim): I want a system that:  
• Maintains unit-price, stock level, replenish-level and reorder quantity for all items.  
Maintains supplier details for all products.  
• Allows me to override the standard price for a specific product when there is a
promotion  
• Allows me to offer special discounts for bulk sales (for example, 10% discount for 5
items, 20% discount for 10 items etc.) on specified products  
• Automatically place a purchase order for all items below replenishment level  
• Generate a sales report for the specified period  
• Generate a report on fast moving items based on value.  
• Generate a report on sales based on customer address (postcode) to arrange for a
marketing campaign.  
• Generate supply report (Payments for supplies are out of scope)  
• List products generating the most revenue.
Bonus marks (one of the following)  
Extend into web-based or client server applications allowing concurrent access. This extension will require the use of sockets and/or RMI and/or Servlets access.
Interface to an external barcode reader
Possible Domain Classes
System, Sale, SalesLineItem, Product, Location, Customer, Credit- Card, Employee,
Employee, Manager, SalesStaff, Supplier
Suggested Initial Test Cases for Milestone 1  
1. Test performing a sales reduces the stock level for all products in the sale  
2. Test replenishing stock increases stock level by the specified amount  
3. Test sale price is computed correctly based on sale line items  
4. Test sale price is computed correctly for discounted items  
5. Test sale price is computed correctly for items offering discounts for bulk sale  
6. Test sale price is not affected for non-discounted items  
7. Test the loyalty points are allocated correctly  
8. Test Maximum discounts are automatically given based on current loyalty points at
the end of transaction  
9. Test discounts are computed correctly when loyalty points are combined with bulk
discount  
10. Test discounts are computed correctly when loyalty points are combined with some
promotional items  
# User-story
Analyze the user requirements
(who + what + why)
## 1.	As a customer:
1.1.	I want to be able to select the product name from a given list, so that I can search the item I want.  
1.2.	I want to be able check the price of any item by keying in the ID before proceeding with the sale, so that I can budget my costs.  
1.3.	I want to check the discounts applicable for a specific product when purchases in bulk, so that I could save money in specific items.  

## 2.	As a sale staff:
2.1.	I want to be able to login to the system and override the transaction details (removing item), so that I can help customer remove item double scanned by accident.  
2.2.	I want to be able to login to the system the system and override the transaction details (cancellation), so that I can help customer cancel the whole transaction when they changed their mind.   
2.3.	I want to be able to check the details of a product to help customer find what they want.(similar like a customer’s function, i believe that a sale staff can have all the functions which users have)   
2.4.	I want to be able to login to the system and override the transaction history (refund), so that I can help the customer get their money back in exchange for the goods bought to be returned. (similar)

## 3.	As a warehouse staff:
3.1.	I want to be able to replenish stock levels before placing items received on the shelves of warehouse, so that I can take them out to retailers in time.   
3.2.	I want to be able to make request for purchasing products from suppliers so that I can keep the products in stock.   
3.3.	I want to be able to see details of purchase order so that when the stock arrived, I’m able to check its quantity if it meets our request. 

## 4.	As a manager:
4.1.	I want to maintain unit-price, stock level, replenish-level and reorder quantity for all items, so that I can know the status of supermarket operation and according to existing information to make a strategy.  
4.2.	I want to maintains supplier details for all products, so that if there is any problems with any products or order more products, I can contact supplier directly. (could be deleted because these are general description manager’s purpose)  
4.3.	I want to override the standard price for a specific product when there is a promotion, so that I can promote products at the first time.  
4.4.	I want to offer special discounts for bulk sales on specified products, so that I can give a discounts for bulk sales.  
4.5.	I want to automatically place a purchase order for all items below replenishment level, so that I can keep the goods plentiful at all times.  
4.6.	I want to generate a sales report for the specified period, so that I can analyse the performance of our supermarket.  
4.7.	I want to generate a report on fast moving items based on value so that I can know our best seller.  
4.8.	I want to generate a report on sales based on customer address (postcode), so that I can arrange for a marketing campaign.  
4.9.	I want to generate supply report, so that I can keep track of our suppliers and find the opportunity for collaboration.   
4.10.	I want to list products generating the most revenue, so that I can make a strategy for making more money.  


