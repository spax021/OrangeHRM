package ui.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import api.tests.BaseApiTest;
import pages.DashboardPage;
import pages.RecruitmentPage;

public class RecruitmentTest extends BaseTest {

	private RecruitmentPage recruitmentPage;
	private DashboardPage dashboardPage;

	@BeforeClass
	public void setLocal() {
		BaseApiTest.setLocalisation();
		System.out.println("This is executed");
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		recruitmentPage = new RecruitmentPage();
		dashboardPage = new DashboardPage();
		dashboardPage.loginUserToTheSystem();
	}

	@AfterMethod
	public void tearDown() {
		quitBrowser();
	}

	@Test(priority = -1, description= "Verify that Admin can add new candidate to recrutiment page")
	public void TestVerifyAddingNewCandidate()  {
		dashboardPage.clickRecruitmentMenu();
		recruitmentPage.clickAddNewCandidateButton();
		recruitmentPage.populateNewCandidateFields();
		recruitmentPage.saveNewCandidate();
		recruitmentPage.verifyNewCandidateIsCreated();
	}

	@Test(priority = 0, description= "Verify that Admin can search for a candidate by name")
	public void TestVerifySearchingCandidateByName() {
		dashboardPage.clickRecruitmentMenu();
		recruitmentPage.enterCandidateFirstname();
		recruitmentPage.choseCandidateFromList();
		recruitmentPage.searchForCandidate();
		recruitmentPage.verifyCandidateIsFound();
	}
	

	@Test(priority = 1, description= "Verify that Admin can edit candidate")
	public void TestVerifyEditingCandidate() {
		dashboardPage.clickRecruitmentMenu();
		recruitmentPage.enterCandidateFirstname();
		recruitmentPage.choseCandidateFromList();
		recruitmentPage.searchForCandidate();
		recruitmentPage.verifyCandidateIsFound();
		recruitmentPage.clickOnActionView();
		recruitmentPage.clickOnEditButton();
		recruitmentPage.editFirstname();
		recruitmentPage.editLastname();
		recruitmentPage.saveExistingCandidate();
		recruitmentPage.verifyChangesAreApplied();
		
	}
	
	

}
