Feature: Login feature

  Scenario: As a valid user I can log into my app

  	Then I enter "radek" into input field number 1
  	Then take picture
  	Then I enter "password" into input field number 2
  	Then take picture
  	Then I press the "WANT COOKIES" button
  	Then I should see text containing "MUNCHIES"
  	Then I wait
  	Then take picture


