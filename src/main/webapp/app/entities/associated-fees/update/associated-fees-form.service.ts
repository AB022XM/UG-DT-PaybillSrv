import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAssociatedFees, NewAssociatedFees } from '../associated-fees.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssociatedFees for edit and NewAssociatedFeesFormGroupInput for create.
 */
type AssociatedFeesFormGroupInput = IAssociatedFees | PartialWithRequiredKeyOf<NewAssociatedFees>;

type AssociatedFeesFormDefaults = Pick<NewAssociatedFees, 'id' | 'status'>;

type AssociatedFeesFormGroupContent = {
  id: FormControl<IAssociatedFees['id'] | NewAssociatedFees['id']>;
  recordUniqueIdentifier: FormControl<IAssociatedFees['recordUniqueIdentifier']>;
  feeId: FormControl<IAssociatedFees['feeId']>;
  feeCode: FormControl<IAssociatedFees['feeCode']>;
  feeDescription: FormControl<IAssociatedFees['feeDescription']>;
  status: FormControl<IAssociatedFees['status']>;
  freeField1: FormControl<IAssociatedFees['freeField1']>;
  freeField2: FormControl<IAssociatedFees['freeField2']>;
  freeField3: FormControl<IAssociatedFees['freeField3']>;
  createdAt: FormControl<IAssociatedFees['createdAt']>;
  updatedAt: FormControl<IAssociatedFees['updatedAt']>;
  isDeleted: FormControl<IAssociatedFees['isDeleted']>;
  payment: FormControl<IAssociatedFees['payment']>;
};

export type AssociatedFeesFormGroup = FormGroup<AssociatedFeesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssociatedFeesFormService {
  createAssociatedFeesFormGroup(associatedFees: AssociatedFeesFormGroupInput = { id: null }): AssociatedFeesFormGroup {
    const associatedFeesRawValue = {
      ...this.getFormDefaults(),
      ...associatedFees,
    };
    return new FormGroup<AssociatedFeesFormGroupContent>({
      id: new FormControl(
        { value: associatedFeesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(associatedFeesRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      feeId: new FormControl(associatedFeesRawValue.feeId, {
        validators: [Validators.required],
      }),
      feeCode: new FormControl(associatedFeesRawValue.feeCode, {
        validators: [Validators.required],
      }),
      feeDescription: new FormControl(associatedFeesRawValue.feeDescription),
      status: new FormControl(associatedFeesRawValue.status, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(associatedFeesRawValue.freeField1),
      freeField2: new FormControl(associatedFeesRawValue.freeField2),
      freeField3: new FormControl(associatedFeesRawValue.freeField3),
      createdAt: new FormControl(associatedFeesRawValue.createdAt),
      updatedAt: new FormControl(associatedFeesRawValue.updatedAt),
      isDeleted: new FormControl(associatedFeesRawValue.isDeleted),
      payment: new FormControl(associatedFeesRawValue.payment),
    });
  }

  getAssociatedFees(form: AssociatedFeesFormGroup): IAssociatedFees | NewAssociatedFees {
    return form.getRawValue() as IAssociatedFees | NewAssociatedFees;
  }

  resetForm(form: AssociatedFeesFormGroup, associatedFees: AssociatedFeesFormGroupInput): void {
    const associatedFeesRawValue = { ...this.getFormDefaults(), ...associatedFees };
    form.reset(
      {
        ...associatedFeesRawValue,
        id: { value: associatedFeesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AssociatedFeesFormDefaults {
    return {
      id: null,
      status: false,
    };
  }
}
