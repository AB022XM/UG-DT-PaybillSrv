import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaybillComponent } from '../list/paybill.component';
import { PaybillDetailComponent } from '../detail/paybill-detail.component';
import { PaybillUpdateComponent } from '../update/paybill-update.component';
import { PaybillRoutingResolveService } from './paybill-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const paybillRoute: Routes = [
  {
    path: '',
    component: PaybillComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaybillDetailComponent,
    resolve: {
      paybill: PaybillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaybillUpdateComponent,
    resolve: {
      paybill: PaybillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaybillUpdateComponent,
    resolve: {
      paybill: PaybillRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paybillRoute)],
  exports: [RouterModule],
})
export class PaybillRoutingModule {}
