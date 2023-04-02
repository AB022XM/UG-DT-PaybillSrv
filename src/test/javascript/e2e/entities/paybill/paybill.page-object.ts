import { element, by, ElementFinder } from 'protractor';

export class PaybillComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-paybill div table .btn-danger'));
  title = element.all(by.css('jhi-paybill div h2#page-heading span')).first();
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

export class PaybillUpdatePage {
  pageTitle = element(by.id('jhi-paybill-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  recordUniqueIdentifierInput = element(by.id('field_recordUniqueIdentifier'));
  processTimestampInput = element(by.id('field_processTimestamp'));
  feeAmountInput = element(by.id('field_feeAmount'));
  feeDescriptionInput = element(by.id('field_feeDescription'));
  feeDueFromDateInput = element(by.id('field_feeDueFromDate'));
  feeDueToDateInput = element(by.id('field_feeDueToDate'));
  feeIdInput = element(by.id('field_feeId'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  middleNameInput = element(by.id('field_middleName'));
  outstandingAmountInput = element(by.id('field_outstandingAmount'));
  paymentCodeInput = element(by.id('field_paymentCode'));
  schoolCodeInput = element(by.id('field_schoolCode'));
  schoolNameInput = element(by.id('field_schoolName'));
  studentClassInput = element(by.id('field_studentClass'));
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

  async setProcessTimestampInput(processTimestamp: string): Promise<void> {
    await this.processTimestampInput.sendKeys(processTimestamp);
  }

  async getProcessTimestampInput(): Promise<string> {
    return await this.processTimestampInput.getAttribute('value');
  }

  async setFeeAmountInput(feeAmount: string): Promise<void> {
    await this.feeAmountInput.sendKeys(feeAmount);
  }

  async getFeeAmountInput(): Promise<string> {
    return await this.feeAmountInput.getAttribute('value');
  }

  async setFeeDescriptionInput(feeDescription: string): Promise<void> {
    await this.feeDescriptionInput.sendKeys(feeDescription);
  }

  async getFeeDescriptionInput(): Promise<string> {
    return await this.feeDescriptionInput.getAttribute('value');
  }

  async setFeeDueFromDateInput(feeDueFromDate: string): Promise<void> {
    await this.feeDueFromDateInput.sendKeys(feeDueFromDate);
  }

  async getFeeDueFromDateInput(): Promise<string> {
    return await this.feeDueFromDateInput.getAttribute('value');
  }

  async setFeeDueToDateInput(feeDueToDate: string): Promise<void> {
    await this.feeDueToDateInput.sendKeys(feeDueToDate);
  }

  async getFeeDueToDateInput(): Promise<string> {
    return await this.feeDueToDateInput.getAttribute('value');
  }

  async setFeeIdInput(feeId: string): Promise<void> {
    await this.feeIdInput.sendKeys(feeId);
  }

  async getFeeIdInput(): Promise<string> {
    return await this.feeIdInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setMiddleNameInput(middleName: string): Promise<void> {
    await this.middleNameInput.sendKeys(middleName);
  }

  async getMiddleNameInput(): Promise<string> {
    return await this.middleNameInput.getAttribute('value');
  }

  async setOutstandingAmountInput(outstandingAmount: string): Promise<void> {
    await this.outstandingAmountInput.sendKeys(outstandingAmount);
  }

  async getOutstandingAmountInput(): Promise<string> {
    return await this.outstandingAmountInput.getAttribute('value');
  }

  async setPaymentCodeInput(paymentCode: string): Promise<void> {
    await this.paymentCodeInput.sendKeys(paymentCode);
  }

  async getPaymentCodeInput(): Promise<string> {
    return await this.paymentCodeInput.getAttribute('value');
  }

  async setSchoolCodeInput(schoolCode: string): Promise<void> {
    await this.schoolCodeInput.sendKeys(schoolCode);
  }

  async getSchoolCodeInput(): Promise<string> {
    return await this.schoolCodeInput.getAttribute('value');
  }

  async setSchoolNameInput(schoolName: string): Promise<void> {
    await this.schoolNameInput.sendKeys(schoolName);
  }

  async getSchoolNameInput(): Promise<string> {
    return await this.schoolNameInput.getAttribute('value');
  }

  async setStudentClassInput(studentClass: string): Promise<void> {
    await this.studentClassInput.sendKeys(studentClass);
  }

  async getStudentClassInput(): Promise<string> {
    return await this.studentClassInput.getAttribute('value');
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

export class PaybillDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paybill-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paybill'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
