import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValidateCustomerById } from '../validate-customer-by-id.model';

@Component({
  selector: 'jhi-validate-customer-by-id-detail',
  templateUrl: './validate-customer-by-id-detail.component.html',
})
export class ValidateCustomerByIdDetailComponent implements OnInit {
  validateCustomerById: IValidateCustomerById | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validateCustomerById }) => {
      this.validateCustomerById = validateCustomerById;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
