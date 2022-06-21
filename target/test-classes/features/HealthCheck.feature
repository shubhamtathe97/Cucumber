@ui @healthcheck
Feature: E-commerce project website health check
#@Searchheadphone
 #Scenario: Is User able to open the browser and navigate to the URL and search the product
     #Given User open the browser
     #And   User navigate to the home page of the application url
     #When  User search for a product "Headphone"
     #Then  Search product is displayed
#
#@test
#Scenario: User is click on the Product and check the Product Details
    #Given User open the browser
    #And   User navigate to the home page of the application url
    #And   User search for a product "Headphone"
    #When  User click on any product
    #Then  Product Description is displayed in new tab


  Scenario: User is able to Open the browser, navigate to the URL and Search for Product
    Given   User navigate to the home page of the application url
    When    User search for a product "Headphone"
    Then    Search product is displayed

  Scenario: User is click on the Product and check the Product Details
    Given   User navigate to the home page of the application url
    And     User search for a product "Laptop"
    When    User click on any product
    Then    Product Description is displayed in new tab