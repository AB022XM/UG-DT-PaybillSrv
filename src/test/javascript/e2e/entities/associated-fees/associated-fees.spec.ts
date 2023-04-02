import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AssociatedFeesComponentsPage, AssociatedFeesDeleteDialog, AssociatedFeesUpdatePage } from './associated-fees.page-object';

const expect = chai.expect;

describe('AssociatedFees e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let associatedFeesComponentsPage: AssociatedFeesComponentsPage;
  let associatedFeesUpdatePage: AssociatedFeesUpdatePage;
  let associatedFeesDeleteDialog: AssociatedFeesDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AssociatedFees', async () => {
    await navBarPage.goToEntity('associated-fees');
    associatedFeesComponentsPage = new AssociatedFeesComponentsPage();
    await browser.wait(ec.visibilityOf(associatedFeesComponentsPage.title), 5000);
    expect(await associatedFeesComponentsPage.getTitle()).to.eq('paybillApiApp.associatedFees.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(associatedFeesComponentsPage.entities), ec.visibilityOf(associatedFeesComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AssociatedFees page', async () => {
    await associatedFeesComponentsPage.clickOnCreateButton();
    associatedFeesUpdatePage = new AssociatedFeesUpdatePage();
    expect(await associatedFeesUpdatePage.getPageTitle()).to.eq('paybillApiApp.associatedFees.home.createOrEditLabel');
    await associatedFeesUpdatePage.cancel();
  });

  it('should create and save AssociatedFees', async () => {
    const nbButtonsBeforeCreate = await associatedFeesComponentsPage.countDeleteButtons();

    await associatedFeesComponentsPage.clickOnCreateButton();

    await promise.all([
      associatedFeesUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      associatedFeesUpdatePage.setFeeIdInput('5'),
      associatedFeesUpdatePage.setFeeCodeInput('feeCode'),
      associatedFeesUpdatePage.setFeeDescriptionInput('feeDescription'),
      associatedFeesUpdatePage.getStatusInput().click(),
      associatedFeesUpdatePage.setFreeField1Input('freeField1'),
      associatedFeesUpdatePage.setFreeField2Input('freeField2'),
      associatedFeesUpdatePage.setFreeField3Input('freeField3'),
      associatedFeesUpdatePage.setCreatedAtInput('2000-12-31'),
      associatedFeesUpdatePage.setUpdatedAtInput('2000-12-31'),
      associatedFeesUpdatePage.setIsDeletedInput('2000-12-31'),
      associatedFeesUpdatePage.paymentSelectLastOption(),
    ]);

    await associatedFeesUpdatePage.save();
    expect(await associatedFeesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await associatedFeesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AssociatedFees', async () => {
    const nbButtonsBeforeDelete = await associatedFeesComponentsPage.countDeleteButtons();
    await associatedFeesComponentsPage.clickOnLastDeleteButton();

    associatedFeesDeleteDialog = new AssociatedFeesDeleteDialog();
    expect(await associatedFeesDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.associatedFees.delete.question');
    await associatedFeesDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(associatedFeesComponentsPage.title), 5000);

    expect(await associatedFeesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
