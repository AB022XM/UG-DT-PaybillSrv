import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaybill } from '../paybill.model';

@Component({
  selector: 'jhi-paybill-detail',
  templateUrl: './paybill-detail.component.html',
})
export class PaybillDetailComponent implements OnInit {
  paybill: IPaybill | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paybill }) => {
      this.paybill = paybill;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
