import { element, by, ElementFinder } from 'protractor';

export class ValidateCustomerByIdComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-validate-customer-by-id div table .btn-danger'));
  title = element.all(by.css('jhi-validate-customer-by-id div h2#page-heading span')).first();
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

export class ValidateCustomerByIdUpdatePage {
  pageTitle = element(by.id('jhi-validate-customer-by-id-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  customnerIdInput = element(by.id('field_customnerId'));
  billCodeInput = element(by.id('field_billCode'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCustomnerIdInput(customnerId: string): Promise<void> {
    await this.customnerIdInput.sendKeys(customnerId);
  }

  async getCustomnerIdInput(): Promise<string> {
    return await this.customnerIdInput.getAttribute('value');
  }

  async setBillCodeInput(billCode: string): Promise<void> {
    await this.billCodeInput.sendKeys(billCode);
  }

  async getBillCodeInput(): Promise<string> {
    return await this.billCodeInput.getAttribute('value');
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

export class ValidateCustomerByIdDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-validateCustomerById-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-validateCustomerById'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
