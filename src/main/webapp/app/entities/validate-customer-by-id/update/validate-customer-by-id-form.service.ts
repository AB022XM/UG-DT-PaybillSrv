import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IValidateCustomerById, NewValidateCustomerById } from '../validate-customer-by-id.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IValidateCustomerById for edit and NewValidateCustomerByIdFormGroupInput for create.
 */
type ValidateCustomerByIdFormGroupInput = IValidateCustomerById | PartialWithRequiredKeyOf<NewValidateCustomerById>;

type ValidateCustomerByIdFormDefaults = Pick<NewValidateCustomerById, 'id'>;

type ValidateCustomerByIdFormGroupContent = {
  id: FormControl<IValidateCustomerById['id'] | NewValidateCustomerById['id']>;
  customnerId: FormControl<IValidateCustomerById['customnerId']>;
  billCode: FormControl<IValidateCustomerById['billCode']>;
};

export type ValidateCustomerByIdFormGroup = FormGroup<ValidateCustomerByIdFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ValidateCustomerByIdFormService {
  createValidateCustomerByIdFormGroup(
    validateCustomerById: ValidateCustomerByIdFormGroupInput = { id: null }
  ): ValidateCustomerByIdFormGroup {
    const validateCustomerByIdRawValue = {
      ...this.getFormDefaults(),
      ...validateCustomerById,
    };
    return new FormGroup<ValidateCustomerByIdFormGroupContent>({
      id: new FormControl(
        { value: validateCustomerByIdRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      customnerId: new FormControl(validateCustomerByIdRawValue.customnerId, {
        validators: [Validators.required],
      }),
      billCode: new FormControl(validateCustomerByIdRawValue.billCode, {
        validators: [Validators.required],
      }),
    });
  }

  getValidateCustomerById(form: ValidateCustomerByIdFormGroup): IValidateCustomerById | NewValidateCustomerById {
    return form.getRawValue() as IValidateCustomerById | NewValidateCustomerById;
  }

  resetForm(form: ValidateCustomerByIdFormGroup, validateCustomerById: ValidateCustomerByIdFormGroupInput): void {
    const validateCustomerByIdRawValue = { ...this.getFormDefaults(), ...validateCustomerById };
    form.reset(
      {
        ...validateCustomerByIdRawValue,
        id: { value: validateCustomerByIdRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ValidateCustomerByIdFormDefaults {
    return {
      id: null,
    };
  }
}
