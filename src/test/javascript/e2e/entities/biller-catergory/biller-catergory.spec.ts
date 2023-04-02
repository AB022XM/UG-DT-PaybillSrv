import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BillerCatergoryComponentsPage, BillerCatergoryDeleteDialog, BillerCatergoryUpdatePage } from './biller-catergory.page-object';

const expect = chai.expect;

describe('BillerCatergory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let billerCatergoryComponentsPage: BillerCatergoryComponentsPage;
  let billerCatergoryUpdatePage: BillerCatergoryUpdatePage;
  let billerCatergoryDeleteDialog: BillerCatergoryDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BillerCatergories', async () => {
    await navBarPage.goToEntity('biller-catergory');
    billerCatergoryComponentsPage = new BillerCatergoryComponentsPage();
    await browser.wait(ec.visibilityOf(billerCatergoryComponentsPage.title), 5000);
    expect(await billerCatergoryComponentsPage.getTitle()).to.eq('paybillApiApp.billerCatergory.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(billerCatergoryComponentsPage.entities), ec.visibilityOf(billerCatergoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create BillerCatergory page', async () => {
    await billerCatergoryComponentsPage.clickOnCreateButton();
    billerCatergoryUpdatePage = new BillerCatergoryUpdatePage();
    expect(await billerCatergoryUpdatePage.getPageTitle()).to.eq('paybillApiApp.billerCatergory.home.createOrEditLabel');
    await billerCatergoryUpdatePage.cancel();
  });

  it('should create and save BillerCatergories', async () => {
    const nbButtonsBeforeCreate = await billerCatergoryComponentsPage.countDeleteButtons();

    await billerCatergoryComponentsPage.clickOnCreateButton();

    await promise.all([
      billerCatergoryUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      billerCatergoryUpdatePage.setCategoryIdInput('5'),
      billerCatergoryUpdatePage.setCategoryCodeInput('categoryCode'),
      billerCatergoryUpdatePage.setCategoryNameInput('categoryName'),
      billerCatergoryUpdatePage.setCategoryDescriptionInput('categoryDescription'),
      billerCatergoryUpdatePage.statusSelectLastOption(),
      billerCatergoryUpdatePage.setFreeField1Input('freeField1'),
      billerCatergoryUpdatePage.setFreeField2Input('freeField2'),
      billerCatergoryUpdatePage.setFreeField3Input('freeField3'),
      billerCatergoryUpdatePage.getIsDeletedInput().click(),
    ]);

    await billerCatergoryUpdatePage.save();
    expect(await billerCatergoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await billerCatergoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BillerCatergory', async () => {
    const nbButtonsBeforeDelete = await billerCatergoryComponentsPage.countDeleteButtons();
    await billerCatergoryComponentsPage.clickOnLastDeleteButton();

    billerCatergoryDeleteDialog = new BillerCatergoryDeleteDialog();
    expect(await billerCatergoryDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.billerCatergory.delete.question');
    await billerCatergoryDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(billerCatergoryComponentsPage.title), 5000);

    expect(await billerCatergoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
