import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ValidateCustomerByIdComponentsPage,
  ValidateCustomerByIdDeleteDialog,
  ValidateCustomerByIdUpdatePage,
} from './validate-customer-by-id.page-object';

const expect = chai.expect;

describe('ValidateCustomerById e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let validateCustomerByIdComponentsPage: ValidateCustomerByIdComponentsPage;
  let validateCustomerByIdUpdatePage: ValidateCustomerByIdUpdatePage;
  let validateCustomerByIdDeleteDialog: ValidateCustomerByIdDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ValidateCustomerByIds', async () => {
    await navBarPage.goToEntity('validate-customer-by-id');
    validateCustomerByIdComponentsPage = new ValidateCustomerByIdComponentsPage();
    await browser.wait(ec.visibilityOf(validateCustomerByIdComponentsPage.title), 5000);
    expect(await validateCustomerByIdComponentsPage.getTitle()).to.eq('paybillApiApp.validateCustomerById.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(validateCustomerByIdComponentsPage.entities), ec.visibilityOf(validateCustomerByIdComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ValidateCustomerById page', async () => {
    await validateCustomerByIdComponentsPage.clickOnCreateButton();
    validateCustomerByIdUpdatePage = new ValidateCustomerByIdUpdatePage();
    expect(await validateCustomerByIdUpdatePage.getPageTitle()).to.eq('paybillApiApp.validateCustomerById.home.createOrEditLabel');
    await validateCustomerByIdUpdatePage.cancel();
  });

  it('should create and save ValidateCustomerByIds', async () => {
    const nbButtonsBeforeCreate = await validateCustomerByIdComponentsPage.countDeleteButtons();

    await validateCustomerByIdComponentsPage.clickOnCreateButton();

    await promise.all([validateCustomerByIdUpdatePage.setCustomnerIdInput('5'), validateCustomerByIdUpdatePage.setBillCodeInput('5')]);

    await validateCustomerByIdUpdatePage.save();
    expect(await validateCustomerByIdUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await validateCustomerByIdComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ValidateCustomerById', async () => {
    const nbButtonsBeforeDelete = await validateCustomerByIdComponentsPage.countDeleteButtons();
    await validateCustomerByIdComponentsPage.clickOnLastDeleteButton();

    validateCustomerByIdDeleteDialog = new ValidateCustomerByIdDeleteDialog();
    expect(await validateCustomerByIdDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.validateCustomerById.delete.question');
    await validateCustomerByIdDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(validateCustomerByIdComponentsPage.title), 5000);

    expect(await validateCustomerByIdComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
