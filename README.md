# My Project: Bookkeeper App

## A program to keep track of investments

#### What can the application be used to do?
- log all stock purchases and sales
- monitor balances of investment accounts
- calculate net profits and net losses over selected periods of time
- view daily/weekly/monthly/yearly summaries of investments in an easy-to-read report

#### Who will use the application?
People who have investments/ play the stock market

#### Why this project is of interest to me
I wanted to pursue this project because my entire family plays the stock market, and have often expressed to me how difficult
it is to stay consistently organized in keeping track of their investments and transactions. I want to create a program 
that might make their lives a little bit easier. 

## User Stories
As a user, I want to be able to...
 - add a new account (with an initial balance) to my accounts list **(phase 1)**
 - withdraw balance from an account on my accounts list **(phase 1)**
 - deposit money into an account on my accounts list **(phase 1)**
 - remove an account from my accounts list  **(phase 1)**
 - view my list of accounts
 - add a stock purchase to my transactions list in a specific account
 - add a stock sale to my transactions list in a specific account
 - view my transactions list from a specific account 
 - remove a transaction from my transactions list in a specific account
 - save my accounts list to file **(phase 2)**
 - load my accounts list from file **(phase 2)**
 
#### Phase 4: Task 2
There is a type hierarchy in the gui package. ViewAccountDetailsPanel and ViewInvestmentDetails panel are both subclasses of 
the abstract class, ViewDetailsPanel, each with different functionality. 

#### Phase 4: Task 3
There are a lot of coupling issues in my gui package. When I add a new JPanel to my application, I have to make changes in several
other classes. Certain panels need to be displayed at certain times, and when I click a button, I want some panels to be 
visible, but others to not be. 

How I would attempt to fix this is:
 - use the observer pattern 
 - make a new class which handles displaying all of the panels in the application
 - make a new observer class which will be notified every time a button is clicked or something is selected in a list
 - everytime the observer class is notified, it will call the methods in the class which handles displaying the panels
 - make ListPanel, ViewDetailsPanel, and NewItemPanel extend Observable, because most of the user interaction will be through 
 the subclasses of those classes
 
As I add new panels to my gui package, I also realize there is quite a bit of code duplication. A lot of methods in different 
classes are similar, but only slightly differ. 

To fix this:
 - since each gui class has mostly distinct functionality, I would make a new abstract class for all the most common methods and have
   each of my gui classes extend it. 

