import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentChannel } from '../payment-channel.model';
import { PaymentChannelService } from '../service/payment-channel.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './payment-channel-delete-dialog.component.html',
})
export class PaymentChannelDeleteDialogComponent {
  paymentChannel?: IPaymentChannel;

  constructor(protected paymentChannelService: PaymentChannelService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentChannelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
