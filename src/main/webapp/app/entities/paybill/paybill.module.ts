import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaybillComponent } from './list/paybill.component';
import { PaybillDetailComponent } from './detail/paybill-detail.component';
import { PaybillUpdateComponent } from './update/paybill-update.component';
import { PaybillDeleteDialogComponent } from './delete/paybill-delete-dialog.component';
import { PaybillRoutingModule } from './route/paybill-routing.module';

@NgModule({
  imports: [SharedModule, PaybillRoutingModule],
  declarations: [PaybillComponent, PaybillDetailComponent, PaybillUpdateComponent, PaybillDeleteDialogComponent],
})
export class PaybillModule {}
