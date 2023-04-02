import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ValidateCustomerByIdFormService, ValidateCustomerByIdFormGroup } from './validate-customer-by-id-form.service';
import { IValidateCustomerById } from '../validate-customer-by-id.model';
import { ValidateCustomerByIdService } from '../service/validate-customer-by-id.service';

@Component({
  selector: 'jhi-validate-customer-by-id-update',
  templateUrl: './validate-customer-by-id-update.component.html',
})
export class ValidateCustomerByIdUpdateComponent implements OnInit {
  isSaving = false;
  validateCustomerById: IValidateCustomerById | null = null;

  editForm: ValidateCustomerByIdFormGroup = this.validateCustomerByIdFormService.createValidateCustomerByIdFormGroup();

  constructor(
    protected validateCustomerByIdService: ValidateCustomerByIdService,
    protected validateCustomerByIdFormService: ValidateCustomerByIdFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ validateCustomerById }) => {
      this.validateCustomerById = validateCustomerById;
      if (validateCustomerById) {
        this.updateForm(validateCustomerById);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const validateCustomerById = this.validateCustomerByIdFormService.getValidateCustomerById(this.editForm);
    if (validateCustomerById.id !== null) {
      this.subscribeToSaveResponse(this.validateCustomerByIdService.update(validateCustomerById));
    } else {
      this.subscribeToSaveResponse(this.validateCustomerByIdService.create(validateCustomerById));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IValidateCustomerById>>): void {
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

  protected updateForm(validateCustomerById: IValidateCustomerById): void {
    this.validateCustomerById = validateCustomerById;
    this.validateCustomerByIdFormService.resetForm(this.editForm, validateCustomerById);
  }
}
