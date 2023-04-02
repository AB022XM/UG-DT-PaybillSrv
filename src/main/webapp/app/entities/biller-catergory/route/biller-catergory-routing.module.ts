import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BillerCatergoryComponent } from '../list/biller-catergory.component';
import { BillerCatergoryDetailComponent } from '../detail/biller-catergory-detail.component';
import { BillerCatergoryUpdateComponent } from '../update/biller-catergory-update.component';
import { BillerCatergoryRoutingResolveService } from './biller-catergory-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const billerCatergoryRoute: Routes = [
  {
    path: '',
    component: BillerCatergoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillerCatergoryDetailComponent,
    resolve: {
      billerCatergory: BillerCatergoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillerCatergoryUpdateComponent,
    resolve: {
      billerCatergory: BillerCatergoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillerCatergoryUpdateComponent,
    resolve: {
      billerCatergory: BillerCatergoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(billerCatergoryRoute)],
  exports: [RouterModule],
})
export class BillerCatergoryRoutingModule {}
