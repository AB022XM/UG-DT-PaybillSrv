import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactInfoComponentsPage, ContactInfoDeleteDialog, ContactInfoUpdatePage } from './contact-info.page-object';

const expect = chai.expect;

describe('ContactInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactInfoComponentsPage: ContactInfoComponentsPage;
  let contactInfoUpdatePage: ContactInfoUpdatePage;
  let contactInfoDeleteDialog: ContactInfoDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactInfos', async () => {
    await navBarPage.goToEntity('contact-info');
    contactInfoComponentsPage = new ContactInfoComponentsPage();
    await browser.wait(ec.visibilityOf(contactInfoComponentsPage.title), 5000);
    expect(await contactInfoComponentsPage.getTitle()).to.eq('paybillApiApp.contactInfo.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contactInfoComponentsPage.entities), ec.visibilityOf(contactInfoComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContactInfo page', async () => {
    await contactInfoComponentsPage.clickOnCreateButton();
    contactInfoUpdatePage = new ContactInfoUpdatePage();
    expect(await contactInfoUpdatePage.getPageTitle()).to.eq('paybillApiApp.contactInfo.home.createOrEditLabel');
    await contactInfoUpdatePage.cancel();
  });

  it('should create and save ContactInfos', async () => {
    const nbButtonsBeforeCreate = await contactInfoComponentsPage.countDeleteButtons();

    await contactInfoComponentsPage.clickOnCreateButton();

    await promise.all([
      contactInfoUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      contactInfoUpdatePage.setContactIdInput('contactId'),
      contactInfoUpdatePage.setPhoneNumberInput('phoneNumber'),
      contactInfoUpdatePage.setEmailAddressInput('emailAddress'),
      contactInfoUpdatePage.setParentsPhoneNumberInput('parentsPhoneNumber'),
      contactInfoUpdatePage.statusSelectLastOption(),
      contactInfoUpdatePage.getIsDeletedInput().click(),
      contactInfoUpdatePage.setFreeField1Input('freeField1'),
      contactInfoUpdatePage.setFreeField2Input('freeField2'),
      contactInfoUpdatePage.setFreeField3Input('freeField3'),
      contactInfoUpdatePage.studentSelectLastOption(),
    ]);

    await contactInfoUpdatePage.save();
    expect(await contactInfoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ContactInfo', async () => {
    const nbButtonsBeforeDelete = await contactInfoComponentsPage.countDeleteButtons();
    await contactInfoComponentsPage.clickOnLastDeleteButton();

    contactInfoDeleteDialog = new ContactInfoDeleteDialog();
    expect(await contactInfoDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.contactInfo.delete.question');
    await contactInfoDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(contactInfoComponentsPage.title), 5000);

    expect(await contactInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
