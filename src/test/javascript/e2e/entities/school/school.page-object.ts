import { element, by, ElementFinder } from 'protractor';

export class SchoolComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-school div table .btn-danger'));
  title = element.all(by.css('jhi-school div h2#page-heading span')).first();
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

export class SchoolUpdatePage {
  pageTitle = element(by.id('jhi-school-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  recordUniqueIdentifierInput = element(by.id('field_recordUniqueIdentifier'));
  schoolIdInput = element(by.id('field_schoolId'));
  schoolCodeInput = element(by.id('field_schoolCode'));
  schoolPhoneNumberInput = element(by.id('field_schoolPhoneNumber'));
  schoolAlternativePhoneNumberInput = element(by.id('field_schoolAlternativePhoneNumber'));
  schoolemailAddessInput = element(by.id('field_schoolemailAddess'));
  schoolNameInput = element(by.id('field_schoolName'));
  statusSelect = element(by.id('field_status'));
  freeField1Input = element(by.id('field_freeField1'));
  registrationDateInput = element(by.id('field_registrationDate'));
  approvedDateInput = element(by.id('field_approvedDate'));
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

  async setSchoolIdInput(schoolId: string): Promise<void> {
    await this.schoolIdInput.sendKeys(schoolId);
  }

  async getSchoolIdInput(): Promise<string> {
    return await this.schoolIdInput.getAttribute('value');
  }

  async setSchoolCodeInput(schoolCode: string): Promise<void> {
    await this.schoolCodeInput.sendKeys(schoolCode);
  }

  async getSchoolCodeInput(): Promise<string> {
    return await this.schoolCodeInput.getAttribute('value');
  }

  async setSchoolPhoneNumberInput(schoolPhoneNumber: string): Promise<void> {
    await this.schoolPhoneNumberInput.sendKeys(schoolPhoneNumber);
  }

  async getSchoolPhoneNumberInput(): Promise<string> {
    return await this.schoolPhoneNumberInput.getAttribute('value');
  }

  async setSchoolAlternativePhoneNumberInput(schoolAlternativePhoneNumber: string): Promise<void> {
    await this.schoolAlternativePhoneNumberInput.sendKeys(schoolAlternativePhoneNumber);
  }

  async getSchoolAlternativePhoneNumberInput(): Promise<string> {
    return await this.schoolAlternativePhoneNumberInput.getAttribute('value');
  }

  async setSchoolemailAddessInput(schoolemailAddess: string): Promise<void> {
    await this.schoolemailAddessInput.sendKeys(schoolemailAddess);
  }

  async getSchoolemailAddessInput(): Promise<string> {
    return await this.schoolemailAddessInput.getAttribute('value');
  }

  async setSchoolNameInput(schoolName: string): Promise<void> {
    await this.schoolNameInput.sendKeys(schoolName);
  }

  async getSchoolNameInput(): Promise<string> {
    return await this.schoolNameInput.getAttribute('value');
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

  async setRegistrationDateInput(registrationDate: string): Promise<void> {
    await this.registrationDateInput.sendKeys(registrationDate);
  }

  async getRegistrationDateInput(): Promise<string> {
    return await this.registrationDateInput.getAttribute('value');
  }

  async setApprovedDateInput(approvedDate: string): Promise<void> {
    await this.approvedDateInput.sendKeys(approvedDate);
  }

  async getApprovedDateInput(): Promise<string> {
    return await this.approvedDateInput.getAttribute('value');
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

export class SchoolDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-school-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-school'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
