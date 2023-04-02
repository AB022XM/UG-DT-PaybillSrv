import { element, by, ElementFinder } from 'protractor';

export class BillerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-biller div table .btn-danger'));
  title = element.all(by.css('jhi-biller div h2#page-heading span')).first();
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

export class BillerUpdatePage {
  pageTitle = element(by.id('jhi-biller-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  recordUniqueIdentifierInput = element(by.id('field_recordUniqueIdentifier'));
  billerIdInput = element(by.id('field_billerId'));
  billerCodeInput = element(by.id('field_billerCode'));
  categoryIdInput = element(by.id('field_categoryId'));
  contactIdInput = element(by.id('field_contactId'));
  addressIdInput = element(by.id('field_addressId'));
  firstNameInput = element(by.id('field_firstName'));
  middleNameInput = element(by.id('field_middleName'));
  lastNameInput = element(by.id('field_lastName'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  outStandingAmountInput = element(by.id('field_outStandingAmount'));
  statusSelect = element(by.id('field_status'));
  freeField1Input = element(by.id('field_freeField1'));
  freeField2Input = element(by.id('field_freeField2'));
  freeField3Input = element(by.id('field_freeField3'));
  isDeletedInput = element(by.id('field_isDeleted'));

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

  async setBillerIdInput(billerId: string): Promise<void> {
    await this.billerIdInput.sendKeys(billerId);
  }

  async getBillerIdInput(): Promise<string> {
    return await this.billerIdInput.getAttribute('value');
  }

  async setBillerCodeInput(billerCode: string): Promise<void> {
    await this.billerCodeInput.sendKeys(billerCode);
  }

  async getBillerCodeInput(): Promise<string> {
    return await this.billerCodeInput.getAttribute('value');
  }

  async setCategoryIdInput(categoryId: string): Promise<void> {
    await this.categoryIdInput.sendKeys(categoryId);
  }

  async getCategoryIdInput(): Promise<string> {
    return await this.categoryIdInput.getAttribute('value');
  }

  async setContactIdInput(contactId: string): Promise<void> {
    await this.contactIdInput.sendKeys(contactId);
  }

  async getContactIdInput(): Promise<string> {
    return await this.contactIdInput.getAttribute('value');
  }

  async setAddressIdInput(addressId: string): Promise<void> {
    await this.addressIdInput.sendKeys(addressId);
  }

  async getAddressIdInput(): Promise<string> {
    return await this.addressIdInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setMiddleNameInput(middleName: string): Promise<void> {
    await this.middleNameInput.sendKeys(middleName);
  }

  async getMiddleNameInput(): Promise<string> {
    return await this.middleNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setOutStandingAmountInput(outStandingAmount: string): Promise<void> {
    await this.outStandingAmountInput.sendKeys(outStandingAmount);
  }

  async getOutStandingAmountInput(): Promise<string> {
    return await this.outStandingAmountInput.getAttribute('value');
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

  async setIsDeletedInput(isDeleted: string): Promise<void> {
    await this.isDeletedInput.sendKeys(isDeleted);
  }

  async getIsDeletedInput(): Promise<string> {
    return await this.isDeletedInput.getAttribute('value');
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

export class BillerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-biller-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-biller'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
