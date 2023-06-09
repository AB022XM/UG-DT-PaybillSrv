import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudentComponentsPage, StudentDeleteDialog, StudentUpdatePage } from './student.page-object';

const expect = chai.expect;

describe('Student e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studentComponentsPage: StudentComponentsPage;
  let studentUpdatePage: StudentUpdatePage;
  let studentDeleteDialog: StudentDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Students', async () => {
    await navBarPage.goToEntity('student');
    studentComponentsPage = new StudentComponentsPage();
    await browser.wait(ec.visibilityOf(studentComponentsPage.title), 5000);
    expect(await studentComponentsPage.getTitle()).to.eq('paybillApiApp.student.home.title');
    await browser.wait(ec.or(ec.visibilityOf(studentComponentsPage.entities), ec.visibilityOf(studentComponentsPage.noResult)), 1000);
  });

  it('should load create Student page', async () => {
    await studentComponentsPage.clickOnCreateButton();
    studentUpdatePage = new StudentUpdatePage();
    expect(await studentUpdatePage.getPageTitle()).to.eq('paybillApiApp.student.home.createOrEditLabel');
    await studentUpdatePage.cancel();
  });

  it('should create and save Students', async () => {
    const nbButtonsBeforeCreate = await studentComponentsPage.countDeleteButtons();

    await studentComponentsPage.clickOnCreateButton();

    await promise.all([
      studentUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      studentUpdatePage.setStudentIdInput('5'),
      studentUpdatePage.setStudentCodeInput('5'),
      studentUpdatePage.setStudentClassIdInput('5'),
      studentUpdatePage.setContactIdInput('5'),
      studentUpdatePage.setAddressIdInput('5'),
      studentUpdatePage.setFirstNameInput('firstName'),
      studentUpdatePage.setMiddleNameInput('middleName'),
      studentUpdatePage.setLastNameInput('lastName'),
      studentUpdatePage.setPaymentCodeInput('paymentCode'),
      studentUpdatePage.setDateOfBirthInput('2000-12-31'),
      studentUpdatePage.setOutStandingAmountInput('outStandingAmount'),
      studentUpdatePage.statusSelectLastOption(),
      studentUpdatePage.setBillerContactInput('billerContact'),
      studentUpdatePage.setBillerAddressInput('billerAddress'),
      studentUpdatePage.setFreeField1Input('freeField1'),
      studentUpdatePage.setFreeField2Input('freeField2'),
      studentUpdatePage.setFreeField3Input('freeField3'),
      studentUpdatePage.setIsDeletedInput('2000-12-31'),
    ]);

    await studentUpdatePage.save();
    expect(await studentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await studentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Student', async () => {
    const nbButtonsBeforeDelete = await studentComponentsPage.countDeleteButtons();
    await studentComponentsPage.clickOnLastDeleteButton();

    studentDeleteDialog = new StudentDeleteDialog();
    expect(await studentDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.student.delete.question');
    await studentDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(studentComponentsPage.title), 5000);

    expect(await studentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
