import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentChannelComponent } from './list/payment-channel.component';
import { PaymentChannelDetailComponent } from './detail/payment-channel-detail.component';
import { PaymentChannelUpdateComponent } from './update/payment-channel-update.component';
import { PaymentChannelDeleteDialogComponent } from './delete/payment-channel-delete-dialog.component';
import { PaymentChannelRoutingModule } from './route/payment-channel-routing.module';

@NgModule({
  imports: [SharedModule, PaymentChannelRoutingModule],
  declarations: [
    PaymentChannelComponent,
    PaymentChannelDetailComponent,
    PaymentChannelUpdateComponent,
    PaymentChannelDeleteDialogComponent,
  ],
})
export class PaymentChannelModule {}
