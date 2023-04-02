import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SchoolComponentsPage, SchoolDeleteDialog, SchoolUpdatePage } from './school.page-object';

const expect = chai.expect;

describe('School e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let schoolComponentsPage: SchoolComponentsPage;
  let schoolUpdatePage: SchoolUpdatePage;
  let schoolDeleteDialog: SchoolDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Schools', async () => {
    await navBarPage.goToEntity('school');
    schoolComponentsPage = new SchoolComponentsPage();
    await browser.wait(ec.visibilityOf(schoolComponentsPage.title), 5000);
    expect(await schoolComponentsPage.getTitle()).to.eq('paybillApiApp.school.home.title');
    await browser.wait(ec.or(ec.visibilityOf(schoolComponentsPage.entities), ec.visibilityOf(schoolComponentsPage.noResult)), 1000);
  });

  it('should load create School page', async () => {
    await schoolComponentsPage.clickOnCreateButton();
    schoolUpdatePage = new SchoolUpdatePage();
    expect(await schoolUpdatePage.getPageTitle()).to.eq('paybillApiApp.school.home.createOrEditLabel');
    await schoolUpdatePage.cancel();
  });

  it('should create and save Schools', async () => {
    const nbButtonsBeforeCreate = await schoolComponentsPage.countDeleteButtons();

    await schoolComponentsPage.clickOnCreateButton();

    await promise.all([
      schoolUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      schoolUpdatePage.setSchoolIdInput('5'),
      schoolUpdatePage.setSchoolCodeInput('schoolCode'),
      schoolUpdatePage.setSchoolPhoneNumberInput('schoolPhoneNumber'),
      schoolUpdatePage.setSchoolAlternativePhoneNumberInput('schoolAlternativePhoneNumber'),
      schoolUpdatePage.setSchoolemailAddessInput('schoolemailAddess'),
      schoolUpdatePage.setSchoolNameInput('schoolName'),
      schoolUpdatePage.statusSelectLastOption(),
      schoolUpdatePage.setFreeField1Input('freeField1'),
      schoolUpdatePage.setRegistrationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      schoolUpdatePage.setApprovedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      schoolUpdatePage.setFreeField2Input('freeField2'),
      schoolUpdatePage.setFreeField3Input('freeField3'),
      schoolUpdatePage.setIsDeletedInput('2000-12-31'),
    ]);

    await schoolUpdatePage.save();
    expect(await schoolUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await schoolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last School', async () => {
    const nbButtonsBeforeDelete = await schoolComponentsPage.countDeleteButtons();
    await schoolComponentsPage.clickOnLastDeleteButton();

    schoolDeleteDialog = new SchoolDeleteDialog();
    expect(await schoolDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.school.delete.question');
    await schoolDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(schoolComponentsPage.title), 5000);

    expect(await schoolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
