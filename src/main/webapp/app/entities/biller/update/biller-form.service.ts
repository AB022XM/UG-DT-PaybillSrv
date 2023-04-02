import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBiller, NewBiller } from '../biller.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBiller for edit and NewBillerFormGroupInput for create.
 */
type BillerFormGroupInput = IBiller | PartialWithRequiredKeyOf<NewBiller>;

type BillerFormDefaults = Pick<NewBiller, 'id'>;

type BillerFormGroupContent = {
  id: FormControl<IBiller['id'] | NewBiller['id']>;
  recordUniqueIdentifier: FormControl<IBiller['recordUniqueIdentifier']>;
  billerId: FormControl<IBiller['billerId']>;
  billerCode: FormControl<IBiller['billerCode']>;
  categoryId: FormControl<IBiller['categoryId']>;
  contactId: FormControl<IBiller['contactId']>;
  addressId: FormControl<IBiller['addressId']>;
  firstName: FormControl<IBiller['firstName']>;
  middleName: FormControl<IBiller['middleName']>;
  lastName: FormControl<IBiller['lastName']>;
  dateOfBirth: FormControl<IBiller['dateOfBirth']>;
  outStandingAmount: FormControl<IBiller['outStandingAmount']>;
  status: FormControl<IBiller['status']>;
  freeField1: FormControl<IBiller['freeField1']>;
  freeField2: FormControl<IBiller['freeField2']>;
  freeField3: FormControl<IBiller['freeField3']>;
  isDeleted: FormControl<IBiller['isDeleted']>;
};

export type BillerFormGroup = FormGroup<BillerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BillerFormService {
  createBillerFormGroup(biller: BillerFormGroupInput = { id: null }): BillerFormGroup {
    const billerRawValue = {
      ...this.getFormDefaults(),
      ...biller,
    };
    return new FormGroup<BillerFormGroupContent>({
      id: new FormControl(
        { value: billerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(billerRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      billerId: new FormControl(billerRawValue.billerId),
      billerCode: new FormControl(billerRawValue.billerCode, {
        validators: [Validators.required],
      }),
      categoryId: new FormControl(billerRawValue.categoryId, {
        validators: [Validators.required],
      }),
      contactId: new FormControl(billerRawValue.contactId, {
        validators: [Validators.required],
      }),
      addressId: new FormControl(billerRawValue.addressId, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(billerRawValue.firstName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)],
      }),
      middleName: new FormControl(billerRawValue.middleName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)],
      }),
      lastName: new FormControl(billerRawValue.lastName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(30)],
      }),
      dateOfBirth: new FormControl(billerRawValue.dateOfBirth, {
        validators: [Validators.required],
      }),
      outStandingAmount: new FormControl(billerRawValue.outStandingAmount, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(8)],
      }),
      status: new FormControl(billerRawValue.status, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(billerRawValue.freeField1),
      freeField2: new FormControl(billerRawValue.freeField2),
      freeField3: new FormControl(billerRawValue.freeField3),
      isDeleted: new FormControl(billerRawValue.isDeleted),
    });
  }

  getBiller(form: BillerFormGroup): IBiller | NewBiller {
    return form.getRawValue() as IBiller | NewBiller;
  }

  resetForm(form: BillerFormGroup, biller: BillerFormGroupInput): void {
    const billerRawValue = { ...this.getFormDefaults(), ...biller };
    form.reset(
      {
        ...billerRawValue,
        id: { value: billerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BillerFormDefaults {
    return {
      id: null,
    };
  }
}
