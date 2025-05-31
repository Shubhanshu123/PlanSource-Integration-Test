package Org.PlanSource.tests;

import BaseClass.BaseClass;
import Org.PlanSource.model.DentalBenefitAddAPIandUI;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Org.PlanSource.Listeners.ExtentTestListener.class)
public class PlanSourceAPITests extends BaseClass {

    @Test(priority = 4, dependsOnMethods = {"Org.PlanSource.tests.PlanSourceUITests.AddMedical_and_VoluntaryBenefits"})
    public void addDentalBenefit(){
        DentalBenefitAddAPIandUI benefit = new DentalBenefitAddAPIandUI();
        benefit.dentalBenefit(driver);
    }
    @Test(priority = 5, dependsOnMethods = {"addDentalBenefit"})
    public void checkPdfConfirmation(){
        DentalBenefitAddAPIandUI benefit = new DentalBenefitAddAPIandUI();
        benefit.pdfCheck(driver);
    }


}
