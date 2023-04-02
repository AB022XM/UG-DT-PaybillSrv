import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BillerCatergoryComponent } from './list/biller-catergory.component';
import { BillerCatergoryDetailComponent } from './detail/biller-catergory-detail.component';
import { BillerCatergoryUpdateComponent } from './update/biller-catergory-update.component';
import { BillerCatergoryDeleteDialogComponent } from './delete/biller-catergory-delete-dialog.component';
import { BillerCatergoryRoutingModule } from './route/biller-catergory-routing.module';

@NgModule({
  imports: [SharedModule, BillerCatergoryRoutingModule],
  declarations: [
    BillerCatergoryComponent,
    BillerCatergoryDetailComponent,
    BillerCatergoryUpdateComponent,
    BillerCatergoryDeleteDialogComponent,
  ],
})
export class BillerCatergoryModule {}
