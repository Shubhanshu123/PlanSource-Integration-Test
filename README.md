# PlanSource-Integration-Test
This is a test repo for the Plansource interview skill demo.

## Steps to get the repo and run the test cases :
1. Clone the repo
2. Create your branch using git checkout
3. Select the test you want to run.
4. run with testNG.
5. Extent report is under test-report directory.
* If you face any issue, just do a Maven clean install.
* I have not deleted the branches so you can review the changes made through out.

## Test Cases:

- [X] Create an Employee using UI Automation
- [X] Add a Dependent for the created Employee in the Enrollment window.
- [X] Enroll the Employee into two Benefits
- [ ] Enroll in Dental Benefits using API. [bug] Waiting for this stupid bug to get fixed ([#1])
- [ ] Validate if Enrolled Benefits are visible in UI with API
- [X] Validate the Confirmation PDF after enrollment.

[#1]: https://github.com/Shubhanshu123/PlanSource-Integration-Test/issues/6

## What is not implemented
1. Capturing a screenshot on error. Since I am focusing on implementation and reporting. So, I am not implementing the screenshot feature.
2. Making a test run in parallel using thread-safe logic. Currently driver is defined static. So at the moment, only one instance is available because of this parallel runs are not working. But I will add that in future.
