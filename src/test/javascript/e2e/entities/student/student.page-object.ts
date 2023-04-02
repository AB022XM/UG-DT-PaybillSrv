import { element, by, ElementFinder } from 'protractor';

export class StudentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-student div table .btn-danger'));
  title = element.all(by.css('jhi-student div h2#page-heading span')).first();
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

export class StudentUpdatePage {
  pageTitle = element(by.id('jhi-student-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  recordUniqueIdentifierInput = element(by.id('field_recordUniqueIdentifier'));
  studentIdInput = element(by.id('field_studentId'));
  studentCodeInput = element(by.id('field_studentCode'));
  studentClassIdInput = element(by.id('field_studentClassId'));
  contactIdInput = element(by.id('field_contactId'));
  addressIdInput = element(by.id('field_addressId'));
  firstNameInput = element(by.id('field_firstName'));
  middleNameInput = element(by.id('field_middleName'));
  lastNameInput = element(by.id('field_lastName'));
  paymentCodeInput = element(by.id('field_paymentCode'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  outStandingAmountInput = element(by.id('field_outStandingAmount'));
  statusSelect = element(by.id('field_status'));
  billerContactInput = element(by.id('field_billerContact'));
  billerAddressInput = element(by.id('field_billerAddress'));
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

  async setStudentIdInput(studentId: string): Promise<void> {
    await this.studentIdInput.sendKeys(studentId);
  }

  async getStudentIdInput(): Promise<string> {
    return await this.studentIdInput.getAttribute('value');
  }

  async setStudentCodeInput(studentCode: string): Promise<void> {
    await this.studentCodeInput.sendKeys(studentCode);
  }

  async getStudentCodeInput(): Promise<string> {
    return await this.studentCodeInput.getAttribute('value');
  }

  async setStudentClassIdInput(studentClassId: string): Promise<void> {
    await this.studentClassIdInput.sendKeys(studentClassId);
  }

  async getStudentClassIdInput(): Promise<string> {
    return await this.studentClassIdInput.getAttribute('value');
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

  async setPaymentCodeInput(paymentCode: string): Promise<void> {
    await this.paymentCodeInput.sendKeys(paymentCode);
  }

  async getPaymentCodeInput(): Promise<string> {
    return await this.paymentCodeInput.getAttribute('value');
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

  async setBillerContactInput(billerContact: string): Promise<void> {
    await this.billerContactInput.sendKeys(billerContact);
  }

  async getBillerContactInput(): Promise<string> {
    return await this.billerContactInput.getAttribute('value');
  }

  async setBillerAddressInput(billerAddress: string): Promise<void> {
    await this.billerAddressInput.sendKeys(billerAddress);
  }

  async getBillerAddressInput(): Promise<string> {
    return await this.billerAddressInput.getAttribute('value');
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

export class StudentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-student-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-student'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
