import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IValidateCustomerById } from '../validate-customer-by-id.model';
import { ValidateCustomerByIdService } from '../service/validate-customer-by-id.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './validate-customer-by-id-delete-dialog.component.html',
})
export class ValidateCustomerByIdDeleteDialogComponent {
  validateCustomerById?: IValidateCustomerById;

  constructor(protected validateCustomerByIdService: ValidateCustomerByIdService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.validateCustomerByIdService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
