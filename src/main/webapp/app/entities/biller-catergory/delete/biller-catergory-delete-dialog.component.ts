import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillerCatergory } from '../biller-catergory.model';
import { BillerCatergoryService } from '../service/biller-catergory.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './biller-catergory-delete-dialog.component.html',
})
export class BillerCatergoryDeleteDialogComponent {
  billerCatergory?: IBillerCatergory;

  constructor(protected billerCatergoryService: BillerCatergoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billerCatergoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
