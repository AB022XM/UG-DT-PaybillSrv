import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICustomer, NewCustomer } from '../customer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICustomer for edit and NewCustomerFormGroupInput for create.
 */
type CustomerFormGroupInput = ICustomer | PartialWithRequiredKeyOf<NewCustomer>;

type CustomerFormDefaults = Pick<NewCustomer, 'id'>;

type CustomerFormGroupContent = {
  id: FormControl<ICustomer['id'] | NewCustomer['id']>;
  recordUniqueIdentifier: FormControl<ICustomer['recordUniqueIdentifier']>;
  contactId: FormControl<ICustomer['contactId']>;
  addressId: FormControl<ICustomer['addressId']>;
  customerId: FormControl<ICustomer['customerId']>;
  firstName: FormControl<ICustomer['firstName']>;
  middleName: FormControl<ICustomer['middleName']>;
  lastName: FormControl<ICustomer['lastName']>;
  dateOfBirth: FormControl<ICustomer['dateOfBirth']>;
  status: FormControl<ICustomer['status']>;
  customerAddressId: FormControl<ICustomer['customerAddressId']>;
  customerContactId: FormControl<ICustomer['customerContactId']>;
  freeField1: FormControl<ICustomer['freeField1']>;
  freeField2: FormControl<ICustomer['freeField2']>;
  freeField3: FormControl<ICustomer['freeField3']>;
};

export type CustomerFormGroup = FormGroup<CustomerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CustomerFormService {
  createCustomerFormGroup(customer: CustomerFormGroupInput = { id: null }): CustomerFormGroup {
    const customerRawValue = {
      ...this.getFormDefaults(),
      ...customer,
    };
    return new FormGroup<CustomerFormGroupContent>({
      id: new FormControl(
        { value: customerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(customerRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      contactId: new FormControl(customerRawValue.contactId),
      addressId: new FormControl(customerRawValue.addressId),
      customerId: new FormControl(customerRawValue.customerId, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(customerRawValue.firstName, {
        validators: [Validators.minLength(3), Validators.maxLength(30)],
      }),
      middleName: new FormControl(customerRawValue.middleName, {
        validators: [Validators.minLength(3), Validators.maxLength(30)],
      }),
      lastName: new FormControl(customerRawValue.lastName, {
        validators: [Validators.minLength(3), Validators.maxLength(30)],
      }),
      dateOfBirth: new FormControl(customerRawValue.dateOfBirth),
      status: new FormControl(customerRawValue.status, {
        validators: [Validators.required],
      }),
      customerAddressId: new FormControl(customerRawValue.customerAddressId),
      customerContactId: new FormControl(customerRawValue.customerContactId),
      freeField1: new FormControl(customerRawValue.freeField1),
      freeField2: new FormControl(customerRawValue.freeField2),
      freeField3: new FormControl(customerRawValue.freeField3),
    });
  }

  getCustomer(form: CustomerFormGroup): ICustomer | NewCustomer {
    return form.getRawValue() as ICustomer | NewCustomer;
  }

  resetForm(form: CustomerFormGroup, customer: CustomerFormGroupInput): void {
    const customerRawValue = { ...this.getFormDefaults(), ...customer };
    form.reset(
      {
        ...customerRawValue,
        id: { value: customerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CustomerFormDefaults {
    return {
      id: null,
    };
  }
}
