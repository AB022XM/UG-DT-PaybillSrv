import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaybill } from '../paybill.model';
import { PaybillService } from '../service/paybill.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './paybill-delete-dialog.component.html',
})
export class PaybillDeleteDialogComponent {
  paybill?: IPaybill;

  constructor(protected paybillService: PaybillService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paybillService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
