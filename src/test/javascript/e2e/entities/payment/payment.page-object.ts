import { element, by, ElementFinder } from 'protractor';

export class PaymentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payment div table .btn-danger'));
  title = element.all(by.css('jhi-payment div h2#page-heading span')).first();
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

export class PaymentUpdatePage {
  pageTitle = element(by.id('jhi-payment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  recordUniqueIdentifierInput = element(by.id('field_recordUniqueIdentifier'));
  paymentIdInput = element(by.id('field_paymentId'));
  paymentCodeInput = element(by.id('field_paymentCode'));
  customerIdInput = element(by.id('field_customerId'));
  feeAmountInput = element(by.id('field_feeAmount'));
  isAmountFixedSelect = element(by.id('field_isAmountFixed'));
  feeDescriptionInput = element(by.id('field_feeDescription'));
  fixedAmountInput = element(by.id('field_fixedAmount'));
  paymentNameInput = element(by.id('field_paymentName'));
  outstandingAmountInput = element(by.id('field_outstandingAmount'));
  paymentChannelSelect = element(by.id('field_paymentChannel'));
  freeField1Input = element(by.id('field_freeField1'));
  freeField2Input = element(by.id('field_freeField2'));
  freeField3Input = element(by.id('field_freeField3'));

  billerSelect = element(by.id('field_biller'));

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

  async setPaymentIdInput(paymentId: string): Promise<void> {
    await this.paymentIdInput.sendKeys(paymentId);
  }

  async getPaymentIdInput(): Promise<string> {
    return await this.paymentIdInput.getAttribute('value');
  }

  async setPaymentCodeInput(paymentCode: string): Promise<void> {
    await this.paymentCodeInput.sendKeys(paymentCode);
  }

  async getPaymentCodeInput(): Promise<string> {
    return await this.paymentCodeInput.getAttribute('value');
  }

  async setCustomerIdInput(customerId: string): Promise<void> {
    await this.customerIdInput.sendKeys(customerId);
  }

  async getCustomerIdInput(): Promise<string> {
    return await this.customerIdInput.getAttribute('value');
  }

  async setFeeAmountInput(feeAmount: string): Promise<void> {
    await this.feeAmountInput.sendKeys(feeAmount);
  }

  async getFeeAmountInput(): Promise<string> {
    return await this.feeAmountInput.getAttribute('value');
  }

  async setIsAmountFixedSelect(isAmountFixed: string): Promise<void> {
    await this.isAmountFixedSelect.sendKeys(isAmountFixed);
  }

  async getIsAmountFixedSelect(): Promise<string> {
    return await this.isAmountFixedSelect.element(by.css('option:checked')).getText();
  }

  async isAmountFixedSelectLastOption(): Promise<void> {
    await this.isAmountFixedSelect.all(by.tagName('option')).last().click();
  }

  async setFeeDescriptionInput(feeDescription: string): Promise<void> {
    await this.feeDescriptionInput.sendKeys(feeDescription);
  }

  async getFeeDescriptionInput(): Promise<string> {
    return await this.feeDescriptionInput.getAttribute('value');
  }

  async setFixedAmountInput(fixedAmount: string): Promise<void> {
    await this.fixedAmountInput.sendKeys(fixedAmount);
  }

  async getFixedAmountInput(): Promise<string> {
    return await this.fixedAmountInput.getAttribute('value');
  }

  async setPaymentNameInput(paymentName: string): Promise<void> {
    await this.paymentNameInput.sendKeys(paymentName);
  }

  async getPaymentNameInput(): Promise<string> {
    return await this.paymentNameInput.getAttribute('value');
  }

  async setOutstandingAmountInput(outstandingAmount: string): Promise<void> {
    await this.outstandingAmountInput.sendKeys(outstandingAmount);
  }

  async getOutstandingAmountInput(): Promise<string> {
    return await this.outstandingAmountInput.getAttribute('value');
  }

  async setPaymentChannelSelect(paymentChannel: string): Promise<void> {
    await this.paymentChannelSelect.sendKeys(paymentChannel);
  }

  async getPaymentChannelSelect(): Promise<string> {
    return await this.paymentChannelSelect.element(by.css('option:checked')).getText();
  }

  async paymentChannelSelectLastOption(): Promise<void> {
    await this.paymentChannelSelect.all(by.tagName('option')).last().click();
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

  async billerSelectLastOption(): Promise<void> {
    await this.billerSelect.all(by.tagName('option')).last().click();
  }

  async billerSelectOption(option: string): Promise<void> {
    await this.billerSelect.sendKeys(option);
  }

  getBillerSelect(): ElementFinder {
    return this.billerSelect;
  }

  async getBillerSelectedOption(): Promise<string> {
    return await this.billerSelect.element(by.css('option:checked')).getText();
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

export class PaymentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-payment-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-payment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
