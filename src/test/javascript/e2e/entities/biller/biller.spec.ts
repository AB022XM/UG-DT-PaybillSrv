import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BillerComponentsPage, BillerDeleteDialog, BillerUpdatePage } from './biller.page-object';

const expect = chai.expect;

describe('Biller e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let billerComponentsPage: BillerComponentsPage;
  let billerUpdatePage: BillerUpdatePage;
  let billerDeleteDialog: BillerDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Billers', async () => {
    await navBarPage.goToEntity('biller');
    billerComponentsPage = new BillerComponentsPage();
    await browser.wait(ec.visibilityOf(billerComponentsPage.title), 5000);
    expect(await billerComponentsPage.getTitle()).to.eq('paybillApiApp.biller.home.title');
    await browser.wait(ec.or(ec.visibilityOf(billerComponentsPage.entities), ec.visibilityOf(billerComponentsPage.noResult)), 1000);
  });

  it('should load create Biller page', async () => {
    await billerComponentsPage.clickOnCreateButton();
    billerUpdatePage = new BillerUpdatePage();
    expect(await billerUpdatePage.getPageTitle()).to.eq('paybillApiApp.biller.home.createOrEditLabel');
    await billerUpdatePage.cancel();
  });

  it('should create and save Billers', async () => {
    const nbButtonsBeforeCreate = await billerComponentsPage.countDeleteButtons();

    await billerComponentsPage.clickOnCreateButton();

    await promise.all([
      billerUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      billerUpdatePage.setBillerIdInput('5'),
      billerUpdatePage.setBillerCodeInput('5'),
      billerUpdatePage.setCategoryIdInput('5'),
      billerUpdatePage.setContactIdInput('5'),
      billerUpdatePage.setAddressIdInput('5'),
      billerUpdatePage.setFirstNameInput('firstName'),
      billerUpdatePage.setMiddleNameInput('middleName'),
      billerUpdatePage.setLastNameInput('lastName'),
      billerUpdatePage.setDateOfBirthInput('2000-12-31'),
      billerUpdatePage.setOutStandingAmountInput('outStandingAmount'),
      billerUpdatePage.statusSelectLastOption(),
      billerUpdatePage.setFreeField1Input('freeField1'),
      billerUpdatePage.setFreeField2Input('freeField2'),
      billerUpdatePage.setFreeField3Input('freeField3'),
      billerUpdatePage.setIsDeletedInput('2000-12-31'),
    ]);

    await billerUpdatePage.save();
    expect(await billerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await billerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Biller', async () => {
    const nbButtonsBeforeDelete = await billerComponentsPage.countDeleteButtons();
    await billerComponentsPage.clickOnLastDeleteButton();

    billerDeleteDialog = new BillerDeleteDialog();
    expect(await billerDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.biller.delete.question');
    await billerDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(billerComponentsPage.title), 5000);

    expect(await billerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
