import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BillerComponent } from '../list/biller.component';
import { BillerDetailComponent } from '../detail/biller-detail.component';
import { BillerUpdateComponent } from '../update/biller-update.component';
import { BillerRoutingResolveService } from './biller-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const billerRoute: Routes = [
  {
    path: '',
    component: BillerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillerDetailComponent,
    resolve: {
      biller: BillerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillerUpdateComponent,
    resolve: {
      biller: BillerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillerUpdateComponent,
    resolve: {
      biller: BillerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(billerRoute)],
  exports: [RouterModule],
})
export class BillerRoutingModule {}
