import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentChannel } from '../payment-channel.model';

@Component({
  selector: 'jhi-payment-channel-detail',
  templateUrl: './payment-channel-detail.component.html',
})
export class PaymentChannelDetailComponent implements OnInit {
  paymentChannel: IPaymentChannel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentChannel }) => {
      this.paymentChannel = paymentChannel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
