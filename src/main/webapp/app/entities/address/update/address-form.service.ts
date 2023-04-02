import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAddress, NewAddress } from '../address.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddress for edit and NewAddressFormGroupInput for create.
 */
type AddressFormGroupInput = IAddress | PartialWithRequiredKeyOf<NewAddress>;

type AddressFormDefaults = Pick<NewAddress, 'id'>;

type AddressFormGroupContent = {
  id: FormControl<IAddress['id'] | NewAddress['id']>;
  recordUniqueIdentifier: FormControl<IAddress['recordUniqueIdentifier']>;
  addressId: FormControl<IAddress['addressId']>;
  addresssName: FormControl<IAddress['addresssName']>;
  addressDescription: FormControl<IAddress['addressDescription']>;
  createdAt: FormControl<IAddress['createdAt']>;
  updatedAt: FormControl<IAddress['updatedAt']>;
  isDeleted: FormControl<IAddress['isDeleted']>;
};

export type AddressFormGroup = FormGroup<AddressFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddressFormService {
  createAddressFormGroup(address: AddressFormGroupInput = { id: null }): AddressFormGroup {
    const addressRawValue = {
      ...this.getFormDefaults(),
      ...address,
    };
    return new FormGroup<AddressFormGroupContent>({
      id: new FormControl(
        { value: addressRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(addressRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      addressId: new FormControl(addressRawValue.addressId, {
        validators: [Validators.required],
      }),
      addresssName: new FormControl(addressRawValue.addresssName, {
        validators: [Validators.required],
      }),
      addressDescription: new FormControl(addressRawValue.addressDescription),
      createdAt: new FormControl(addressRawValue.createdAt),
      updatedAt: new FormControl(addressRawValue.updatedAt),
      isDeleted: new FormControl(addressRawValue.isDeleted),
    });
  }

  getAddress(form: AddressFormGroup): IAddress | NewAddress {
    return form.getRawValue() as IAddress | NewAddress;
  }

  resetForm(form: AddressFormGroup, address: AddressFormGroupInput): void {
    const addressRawValue = { ...this.getFormDefaults(), ...address };
    form.reset(
      {
        ...addressRawValue,
        id: { value: addressRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AddressFormDefaults {
    return {
      id: null,
    };
  }
}
