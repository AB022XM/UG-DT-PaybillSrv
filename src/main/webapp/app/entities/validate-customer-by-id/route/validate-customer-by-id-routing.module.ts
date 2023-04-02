import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ValidateCustomerByIdComponent } from '../list/validate-customer-by-id.component';
import { ValidateCustomerByIdDetailComponent } from '../detail/validate-customer-by-id-detail.component';
import { ValidateCustomerByIdUpdateComponent } from '../update/validate-customer-by-id-update.component';
import { ValidateCustomerByIdRoutingResolveService } from './validate-customer-by-id-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const validateCustomerByIdRoute: Routes = [
  {
    path: '',
    component: ValidateCustomerByIdComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ValidateCustomerByIdDetailComponent,
    resolve: {
      validateCustomerById: ValidateCustomerByIdRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ValidateCustomerByIdUpdateComponent,
    resolve: {
      validateCustomerById: ValidateCustomerByIdRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ValidateCustomerByIdUpdateComponent,
    resolve: {
      validateCustomerById: ValidateCustomerByIdRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(validateCustomerByIdRoute)],
  exports: [RouterModule],
})
export class ValidateCustomerByIdRoutingModule {}
