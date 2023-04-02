import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaybillComponentsPage, PaybillDeleteDialog, PaybillUpdatePage } from './paybill.page-object';

const expect = chai.expect;

describe('Paybill e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paybillComponentsPage: PaybillComponentsPage;
  let paybillUpdatePage: PaybillUpdatePage;
  let paybillDeleteDialog: PaybillDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Paybills', async () => {
    await navBarPage.goToEntity('paybill');
    paybillComponentsPage = new PaybillComponentsPage();
    await browser.wait(ec.visibilityOf(paybillComponentsPage.title), 5000);
    expect(await paybillComponentsPage.getTitle()).to.eq('paybillApiApp.paybill.home.title');
    await browser.wait(ec.or(ec.visibilityOf(paybillComponentsPage.entities), ec.visibilityOf(paybillComponentsPage.noResult)), 1000);
  });

  it('should load create Paybill page', async () => {
    await paybillComponentsPage.clickOnCreateButton();
    paybillUpdatePage = new PaybillUpdatePage();
    expect(await paybillUpdatePage.getPageTitle()).to.eq('paybillApiApp.paybill.home.createOrEditLabel');
    await paybillUpdatePage.cancel();
  });

  it('should create and save Paybills', async () => {
    const nbButtonsBeforeCreate = await paybillComponentsPage.countDeleteButtons();

    await paybillComponentsPage.clickOnCreateButton();

    await promise.all([
      paybillUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      paybillUpdatePage.setProcessTimestampInput('2000-12-31'),
      paybillUpdatePage.setFeeAmountInput('5'),
      paybillUpdatePage.setFeeDescriptionInput('feeDescription'),
      paybillUpdatePage.setFeeDueFromDateInput('2000-12-31'),
      paybillUpdatePage.setFeeDueToDateInput('2000-12-31'),
      paybillUpdatePage.setFeeIdInput('feeId'),
      paybillUpdatePage.setFirstNameInput('firstName'),
      paybillUpdatePage.setLastNameInput('lastName'),
      paybillUpdatePage.setMiddleNameInput('middleName'),
      paybillUpdatePage.setOutstandingAmountInput('5'),
      paybillUpdatePage.setPaymentCodeInput('paymentCode'),
      paybillUpdatePage.setSchoolCodeInput('schoolCode'),
      paybillUpdatePage.setSchoolNameInput('schoolName'),
      paybillUpdatePage.setStudentClassInput('studentClass'),
      paybillUpdatePage.paymentChannelSelectLastOption(),
      paybillUpdatePage.setFreeField1Input('freeField1'),
      paybillUpdatePage.setFreeField2Input('freeField2'),
      paybillUpdatePage.setFreeField3Input('freeField3'),
      paybillUpdatePage.billerSelectLastOption(),
    ]);

    await paybillUpdatePage.save();
    expect(await paybillUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paybillComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Paybill', async () => {
    const nbButtonsBeforeDelete = await paybillComponentsPage.countDeleteButtons();
    await paybillComponentsPage.clickOnLastDeleteButton();

    paybillDeleteDialog = new PaybillDeleteDialog();
    expect(await paybillDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.paybill.delete.question');
    await paybillDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(paybillComponentsPage.title), 5000);

    expect(await paybillComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
