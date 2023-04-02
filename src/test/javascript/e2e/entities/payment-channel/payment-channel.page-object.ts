import { element, by, ElementFinder } from 'protractor';

export class PaymentChannelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payment-channel div table .btn-danger'));
  title = element.all(by.css('jhi-payment-channel div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class PaymentChannelUpdatePage {
  pageTitle = element(by.id('jhi-payment-channel-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  recordUniqueIdentifierInput = element(by.id('field_recordUniqueIdentifier'));
  channelIdInput = element(by.id('field_channelId'));
  channelCodeInput = element(by.id('field_channelCode'));
  channelNameSelect = element(by.id('field_channelName'));
  statusSelect = element(by.id('field_status'));
  freeField1Input = element(by.id('field_freeField1'));
  freeField2Input = element(by.id('field_freeField2'));
  freeField3Input = element(by.id('field_freeField3'));
  isDeletedSelect = element(by.id('field_isDeleted'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setRecordUniqueIdentifierInput(recordUniqueIdentifier: string): Promise<void> {
    await this.recordUniqueIdentifierInput.sendKeys(recordUniqueIdentifier);
  }

  async getRecordUniqueIdentifierInput(): Promise<string> {
    return await this.recordUniqueIdentifierInput.getAttribute('value');
  }

  async setChannelIdInput(channelId: string): Promise<void> {
    await this.channelIdInput.sendKeys(channelId);
  }

  async getChannelIdInput(): Promise<string> {
    return await this.channelIdInput.getAttribute('value');
  }

  async setChannelCodeInput(channelCode: string): Promise<void> {
    await this.channelCodeInput.sendKeys(channelCode);
  }

  async getChannelCodeInput(): Promise<string> {
    return await this.channelCodeInput.getAttribute('value');
  }

  async setChannelNameSelect(channelName: string): Promise<void> {
    await this.channelNameSelect.sendKeys(channelName);
  }

  async getChannelNameSelect(): Promise<string> {
    return await this.channelNameSelect.element(by.css('option:checked')).getText();
  }

  async channelNameSelectLastOption(): Promise<void> {
    await this.channelNameSelect.all(by.tagName('option')).last().click();
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async setFreeField1Input(freeField1: string): Promise<void> {
    await this.freeField1Input.sendKeys(freeField1);
  }

  async getFreeField1Input(): Promise<string> {
    return await this.freeField1Input.getAttribute('value');
  }

  async setFreeField2Input(freeField2: string): Promise<void> {
    await this.freeField2Input.sendKeys(freeField2);
  }

  async getFreeField2Input(): Promise<string> {
    return await this.freeField2Input.getAttribute('value');
  }

  async setFreeField3Input(freeField3: string): Promise<void> {
    await this.freeField3Input.sendKeys(freeField3);
  }

  async getFreeField3Input(): Promise<string> {
    return await this.freeField3Input.getAttribute('value');
  }

  async setIsDeletedSelect(isDeleted: string): Promise<void> {
    await this.isDeletedSelect.sendKeys(isDeleted);
  }

  async getIsDeletedSelect(): Promise<string> {
    return await this.isDeletedSelect.element(by.css('option:checked')).getText();
  }

  async isDeletedSelectLastOption(): Promise<void> {
    await this.isDeletedSelect.all(by.tagName('option')).last().click();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class PaymentChannelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentChannel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentChannel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
