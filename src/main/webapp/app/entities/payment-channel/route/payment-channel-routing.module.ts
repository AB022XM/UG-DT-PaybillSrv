import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentChannelComponent } from '../list/payment-channel.component';
import { PaymentChannelDetailComponent } from '../detail/payment-channel-detail.component';
import { PaymentChannelUpdateComponent } from '../update/payment-channel-update.component';
import { PaymentChannelRoutingResolveService } from './payment-channel-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const paymentChannelRoute: Routes = [
  {
    path: '',
    component: PaymentChannelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentChannelDetailComponent,
    resolve: {
      paymentChannel: PaymentChannelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentChannelUpdateComponent,
    resolve: {
      paymentChannel: PaymentChannelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentChannelUpdateComponent,
    resolve: {
      paymentChannel: PaymentChannelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentChannelRoute)],
  exports: [RouterModule],
})
export class PaymentChannelRoutingModule {}
