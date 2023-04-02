import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AssociatedFeesComponent } from './list/associated-fees.component';
import { AssociatedFeesDetailComponent } from './detail/associated-fees-detail.component';
import { AssociatedFeesUpdateComponent } from './update/associated-fees-update.component';
import { AssociatedFeesDeleteDialogComponent } from './delete/associated-fees-delete-dialog.component';
import { AssociatedFeesRoutingModule } from './route/associated-fees-routing.module';

@NgModule({
  imports: [SharedModule, AssociatedFeesRoutingModule],
  declarations: [
    AssociatedFeesComponent,
    AssociatedFeesDetailComponent,
    AssociatedFeesUpdateComponent,
    AssociatedFeesDeleteDialogComponent,
  ],
})
export class AssociatedFeesModule {}
