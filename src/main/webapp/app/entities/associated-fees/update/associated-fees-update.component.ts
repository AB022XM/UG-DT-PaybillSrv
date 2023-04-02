import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AssociatedFeesFormService, AssociatedFeesFormGroup } from './associated-fees-form.service';
import { IAssociatedFees } from '../associated-fees.model';
import { AssociatedFeesService } from '../service/associated-fees.service';
import { IPayment } from 'app/entities/payment/payment.model';
import { PaymentService } from 'app/entities/payment/service/payment.service';

@Component({
  selector: 'jhi-associated-fees-update',
  templateUrl: './associated-fees-update.component.html',
})
export class AssociatedFeesUpdateComponent implements OnInit {
  isSaving = false;
  associatedFees: IAssociatedFees | null = null;

  paymentsSharedCollection: IPayment[] = [];

  editForm: AssociatedFeesFormGroup = this.associatedFeesFormService.createAssociatedFeesFormGroup();

  constructor(
    protected associatedFeesService: AssociatedFeesService,
    protected associatedFeesFormService: AssociatedFeesFormService,
    protected paymentService: PaymentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePayment = (o1: IPayment | null, o2: IPayment | null): boolean => this.paymentService.comparePayment(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ associatedFees }) => {
      this.associatedFees = associatedFees;
      if (associatedFees) {
        this.updateForm(associatedFees);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const associatedFees = this.associatedFeesFormService.getAssociatedFees(this.editForm);
    if (associatedFees.id !== null) {
      this.subscribeToSaveResponse(this.associatedFeesService.update(associatedFees));
    } else {
      this.subscribeToSaveResponse(this.associatedFeesService.create(associatedFees));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssociatedFees>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(associatedFees: IAssociatedFees): void {
    this.associatedFees = associatedFees;
    this.associatedFeesFormService.resetForm(this.editForm, associatedFees);

    this.paymentsSharedCollection = this.paymentService.addPaymentToCollectionIfMissing<IPayment>(
      this.paymentsSharedCollection,
      associatedFees.payment
    );
  }

  protected loadRelationshipsOptions(): void {
    this.paymentService
      .query()
      .pipe(map((res: HttpResponse<IPayment[]>) => res.body ?? []))
      .pipe(
        map((payments: IPayment[]) => this.paymentService.addPaymentToCollectionIfMissing<IPayment>(payments, this.associatedFees?.payment))
      )
      .subscribe((payments: IPayment[]) => (this.paymentsSharedCollection = payments));
  }
}
