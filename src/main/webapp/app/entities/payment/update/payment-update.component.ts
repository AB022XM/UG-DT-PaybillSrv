import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaymentFormService, PaymentFormGroup } from './payment-form.service';
import { IPayment } from '../payment.model';
import { PaymentService } from '../service/payment.service';
import { IBiller } from 'app/entities/biller/biller.model';
import { BillerService } from 'app/entities/biller/service/biller.service';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  payment: IPayment | null = null;
  recordStatusValues = Object.keys(RecordStatus);
  paymentChannelValues = Object.keys(PaymentChannel);

  billersSharedCollection: IBiller[] = [];

  editForm: PaymentFormGroup = this.paymentFormService.createPaymentFormGroup();

  constructor(
    protected paymentService: PaymentService,
    protected paymentFormService: PaymentFormService,
    protected billerService: BillerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBiller = (o1: IBiller | null, o2: IBiller | null): boolean => this.billerService.compareBiller(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      this.payment = payment;
      if (payment) {
        this.updateForm(payment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.paymentFormService.getPayment(this.editForm);
    if (payment.id !== null) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  protected updateForm(payment: IPayment): void {
    this.payment = payment;
    this.paymentFormService.resetForm(this.editForm, payment);

    this.billersSharedCollection = this.billerService.addBillerToCollectionIfMissing<IBiller>(this.billersSharedCollection, payment.biller);
  }

  protected loadRelationshipsOptions(): void {
    this.billerService
      .query()
      .pipe(map((res: HttpResponse<IBiller[]>) => res.body ?? []))
      .pipe(map((billers: IBiller[]) => this.billerService.addBillerToCollectionIfMissing<IBiller>(billers, this.payment?.biller)))
      .subscribe((billers: IBiller[]) => (this.billersSharedCollection = billers));
  }
}
