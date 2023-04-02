import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ValidateCustomerByIdComponent } from './list/validate-customer-by-id.component';
import { ValidateCustomerByIdDetailComponent } from './detail/validate-customer-by-id-detail.component';
import { ValidateCustomerByIdUpdateComponent } from './update/validate-customer-by-id-update.component';
import { ValidateCustomerByIdDeleteDialogComponent } from './delete/validate-customer-by-id-delete-dialog.component';
import { ValidateCustomerByIdRoutingModule } from './route/validate-customer-by-id-routing.module';

@NgModule({
  imports: [SharedModule, ValidateCustomerByIdRoutingModule],
  declarations: [
    ValidateCustomerByIdComponent,
    ValidateCustomerByIdDetailComponent,
    ValidateCustomerByIdUpdateComponent,
    ValidateCustomerByIdDeleteDialogComponent,
  ],
})
export class ValidateCustomerByIdModule {}
