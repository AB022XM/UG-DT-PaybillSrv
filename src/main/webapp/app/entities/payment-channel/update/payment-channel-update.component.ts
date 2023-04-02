import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PaymentChannelFormService, PaymentChannelFormGroup } from './payment-channel-form.service';
import { IPaymentChannel } from '../payment-channel.model';
import { PaymentChannelService } from '../service/payment-channel.service';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

@Component({
  selector: 'jhi-payment-channel-update',
  templateUrl: './payment-channel-update.component.html',
})
export class PaymentChannelUpdateComponent implements OnInit {
  isSaving = false;
  paymentChannel: IPaymentChannel | null = null;
  paymentChannelValues = Object.keys(PaymentChannel);
  recordStatusValues = Object.keys(RecordStatus);

  editForm: PaymentChannelFormGroup = this.paymentChannelFormService.createPaymentChannelFormGroup();

  constructor(
    protected paymentChannelService: PaymentChannelService,
    protected paymentChannelFormService: PaymentChannelFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentChannel }) => {
      this.paymentChannel = paymentChannel;
      if (paymentChannel) {
        this.updateForm(paymentChannel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentChannel = this.paymentChannelFormService.getPaymentChannel(this.editForm);
    if (paymentChannel.id !== null) {
      this.subscribeToSaveResponse(this.paymentChannelService.update(paymentChannel));
    } else {
      this.subscribeToSaveResponse(this.paymentChannelService.create(paymentChannel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentChannel>>): void {
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

  protected updateForm(paymentChannel: IPaymentChannel): void {
    this.paymentChannel = paymentChannel;
    this.paymentChannelFormService.resetForm(this.editForm, paymentChannel);
  }
}
