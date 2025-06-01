# PlanSource-Integration-Test
This is a test repo for the Plansource interview skill demo.

## Steps to get the repo and run the test cases :
1. Clone the repo
2. Create your branch using git checkout
3. Edit the config.properties file with your desired firstname and lastname property value.
       <code>located here: src/test/resources/config.properties </code>
4. Select the test you want to run. <code> Test are located here: src/test/java/Org/PlanSource/tests</code>
5. Run with testNG.
6. Extent report is under test-report directory.
* If you face any issue, just do a Maven clean install.
* I have not deleted the branches so you can review the changes made through out.
> [!WARNING]
> IF YOUR TESTCASES CLICKS THE GET STARTED BUTTON FOR A USER, THEN IT WILL NOT APPEAR AGAIN WHEN ACCESSING THE USER PROFILE FOR ENROLLMENT.
> YOU MIGHT WANT TO MAKE CHANGES TO CONFIG.PROPERTIES.

## Test Cases:

- [X] Create an Employee using UI Automation
- [X] Add a Dependent for the created Employee in the Enrollment window.
- [X] Enroll the Employee into two Benefits
- [X] Enroll in Dental Benefits using API. [bug Fixed] ([#1])
- [X] Validate if Enrolled Benefits are visible in UI with API
- [X] Validate the Confirmation PDF after enrollment.

[#1]: https://github.com/Shubhanshu123/PlanSource-Integration-Test/issues/6

## What is not implemented
1. Capturing a screenshot on error. Since I am focusing on implementation and reporting. So, I am not implementing the screenshot feature.
2. Making a test run in parallel using thread-safe logic. Currently driver is defined static. So at the moment, only one instance is available because of this parallel runs are not working. But I will add that in future.
