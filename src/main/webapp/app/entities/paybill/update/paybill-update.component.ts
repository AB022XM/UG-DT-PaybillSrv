import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaybillFormService, PaybillFormGroup } from './paybill-form.service';
import { IPaybill } from '../paybill.model';
import { PaybillService } from '../service/paybill.service';
import { IBiller } from 'app/entities/biller/biller.model';
import { BillerService } from 'app/entities/biller/service/biller.service';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

@Component({
  selector: 'jhi-paybill-update',
  templateUrl: './paybill-update.component.html',
})
export class PaybillUpdateComponent implements OnInit {
  isSaving = false;
  paybill: IPaybill | null = null;
  paymentChannelValues = Object.keys(PaymentChannel);

  billersSharedCollection: IBiller[] = [];

  editForm: PaybillFormGroup = this.paybillFormService.createPaybillFormGroup();

  constructor(
    protected paybillService: PaybillService,
    protected paybillFormService: PaybillFormService,
    protected billerService: BillerService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareBiller = (o1: IBiller | null, o2: IBiller | null): boolean => this.billerService.compareBiller(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paybill }) => {
      this.paybill = paybill;
      if (paybill) {
        this.updateForm(paybill);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paybill = this.paybillFormService.getPaybill(this.editForm);
    if (paybill.id !== null) {
      this.subscribeToSaveResponse(this.paybillService.update(paybill));
    } else {
      this.subscribeToSaveResponse(this.paybillService.create(paybill));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaybill>>): void {
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

  protected updateForm(paybill: IPaybill): void {
    this.paybill = paybill;
    this.paybillFormService.resetForm(this.editForm, paybill);

    this.billersSharedCollection = this.billerService.addBillerToCollectionIfMissing<IBiller>(this.billersSharedCollection, paybill.biller);
  }

  protected loadRelationshipsOptions(): void {
    this.billerService
      .query()
      .pipe(map((res: HttpResponse<IBiller[]>) => res.body ?? []))
      .pipe(map((billers: IBiller[]) => this.billerService.addBillerToCollectionIfMissing<IBiller>(billers, this.paybill?.biller)))
      .subscribe((billers: IBiller[]) => (this.billersSharedCollection = billers));
  }
}
