import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaymentChannelComponentsPage, PaymentChannelDeleteDialog, PaymentChannelUpdatePage } from './payment-channel.page-object';

const expect = chai.expect;

describe('PaymentChannel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentChannelComponentsPage: PaymentChannelComponentsPage;
  let paymentChannelUpdatePage: PaymentChannelUpdatePage;
  let paymentChannelDeleteDialog: PaymentChannelDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentChannels', async () => {
    await navBarPage.goToEntity('payment-channel');
    paymentChannelComponentsPage = new PaymentChannelComponentsPage();
    await browser.wait(ec.visibilityOf(paymentChannelComponentsPage.title), 5000);
    expect(await paymentChannelComponentsPage.getTitle()).to.eq('paybillApiApp.paymentChannel.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentChannelComponentsPage.entities), ec.visibilityOf(paymentChannelComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentChannel page', async () => {
    await paymentChannelComponentsPage.clickOnCreateButton();
    paymentChannelUpdatePage = new PaymentChannelUpdatePage();
    expect(await paymentChannelUpdatePage.getPageTitle()).to.eq('paybillApiApp.paymentChannel.home.createOrEditLabel');
    await paymentChannelUpdatePage.cancel();
  });

  it('should create and save PaymentChannels', async () => {
    const nbButtonsBeforeCreate = await paymentChannelComponentsPage.countDeleteButtons();

    await paymentChannelComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentChannelUpdatePage.setRecordUniqueIdentifierInput('64c99148-3908-465d-8c4a-e510e3ade974'),
      paymentChannelUpdatePage.setChannelIdInput('5'),
      paymentChannelUpdatePage.setChannelCodeInput('5'),
      paymentChannelUpdatePage.channelNameSelectLastOption(),
      paymentChannelUpdatePage.statusSelectLastOption(),
      paymentChannelUpdatePage.setFreeField1Input('freeField1'),
      paymentChannelUpdatePage.setFreeField2Input('freeField2'),
      paymentChannelUpdatePage.setFreeField3Input('freeField3'),
      paymentChannelUpdatePage.isDeletedSelectLastOption(),
    ]);

    await paymentChannelUpdatePage.save();
    expect(await paymentChannelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentChannelComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentChannel', async () => {
    const nbButtonsBeforeDelete = await paymentChannelComponentsPage.countDeleteButtons();
    await paymentChannelComponentsPage.clickOnLastDeleteButton();

    paymentChannelDeleteDialog = new PaymentChannelDeleteDialog();
    expect(await paymentChannelDeleteDialog.getDialogTitle()).to.eq('paybillApiApp.paymentChannel.delete.question');
    await paymentChannelDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(paymentChannelComponentsPage.title), 5000);

    expect(await paymentChannelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
