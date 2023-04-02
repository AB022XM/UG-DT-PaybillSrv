import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BillerComponent } from './list/biller.component';
import { BillerDetailComponent } from './detail/biller-detail.component';
import { BillerUpdateComponent } from './update/biller-update.component';
import { BillerDeleteDialogComponent } from './delete/biller-delete-dialog.component';
import { BillerRoutingModule } from './route/biller-routing.module';

@NgModule({
  imports: [SharedModule, BillerRoutingModule],
  declarations: [BillerComponent, BillerDetailComponent, BillerUpdateComponent, BillerDeleteDialogComponent],
})
export class BillerModule {}
