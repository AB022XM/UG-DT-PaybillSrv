import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AssociatedFeesComponent } from '../list/associated-fees.component';
import { AssociatedFeesDetailComponent } from '../detail/associated-fees-detail.component';
import { AssociatedFeesUpdateComponent } from '../update/associated-fees-update.component';
import { AssociatedFeesRoutingResolveService } from './associated-fees-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const associatedFeesRoute: Routes = [
  {
    path: '',
    component: AssociatedFeesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AssociatedFeesDetailComponent,
    resolve: {
      associatedFees: AssociatedFeesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AssociatedFeesUpdateComponent,
    resolve: {
      associatedFees: AssociatedFeesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AssociatedFeesUpdateComponent,
    resolve: {
      associatedFees: AssociatedFeesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(associatedFeesRoute)],
  exports: [RouterModule],
})
export class AssociatedFeesRoutingModule {}
