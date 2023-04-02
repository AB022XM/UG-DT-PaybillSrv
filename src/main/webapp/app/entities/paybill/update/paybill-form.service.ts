import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaybill, NewPaybill } from '../paybill.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaybill for edit and NewPaybillFormGroupInput for create.
 */
type PaybillFormGroupInput = IPaybill | PartialWithRequiredKeyOf<NewPaybill>;

type PaybillFormDefaults = Pick<NewPaybill, 'id'>;

type PaybillFormGroupContent = {
  id: FormControl<IPaybill['id'] | NewPaybill['id']>;
  recordUniqueIdentifier: FormControl<IPaybill['recordUniqueIdentifier']>;
  processTimestamp: FormControl<IPaybill['processTimestamp']>;
  feeAmount: FormControl<IPaybill['feeAmount']>;
  feeDescription: FormControl<IPaybill['feeDescription']>;
  feeDueFromDate: FormControl<IPaybill['feeDueFromDate']>;
  feeDueToDate: FormControl<IPaybill['feeDueToDate']>;
  feeId: FormControl<IPaybill['feeId']>;
  firstName: FormControl<IPaybill['firstName']>;
  lastName: FormControl<IPaybill['lastName']>;
  middleName: FormControl<IPaybill['middleName']>;
  outstandingAmount: FormControl<IPaybill['outstandingAmount']>;
  paymentCode: FormControl<IPaybill['paymentCode']>;
  schoolCode: FormControl<IPaybill['schoolCode']>;
  schoolName: FormControl<IPaybill['schoolName']>;
  studentClass: FormControl<IPaybill['studentClass']>;
  paymentChannel: FormControl<IPaybill['paymentChannel']>;
  freeField1: FormControl<IPaybill['freeField1']>;
  freeField2: FormControl<IPaybill['freeField2']>;
  freeField3: FormControl<IPaybill['freeField3']>;
  biller: FormControl<IPaybill['biller']>;
};

export type PaybillFormGroup = FormGroup<PaybillFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaybillFormService {
  createPaybillFormGroup(paybill: PaybillFormGroupInput = { id: null }): PaybillFormGroup {
    const paybillRawValue = {
      ...this.getFormDefaults(),
      ...paybill,
    };
    return new FormGroup<PaybillFormGroupContent>({
      id: new FormControl(
        { value: paybillRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(paybillRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      processTimestamp: new FormControl(paybillRawValue.processTimestamp),
      feeAmount: new FormControl(paybillRawValue.feeAmount),
      feeDescription: new FormControl(paybillRawValue.feeDescription, {
        validators: [Validators.minLength(3), Validators.maxLength(200)],
      }),
      feeDueFromDate: new FormControl(paybillRawValue.feeDueFromDate),
      feeDueToDate: new FormControl(paybillRawValue.feeDueToDate),
      feeId: new FormControl(paybillRawValue.feeId, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      firstName: new FormControl(paybillRawValue.firstName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      lastName: new FormControl(paybillRawValue.lastName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      middleName: new FormControl(paybillRawValue.middleName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      outstandingAmount: new FormControl(paybillRawValue.outstandingAmount),
      paymentCode: new FormControl(paybillRawValue.paymentCode, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      schoolCode: new FormControl(paybillRawValue.schoolCode, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      schoolName: new FormControl(paybillRawValue.schoolName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(200)],
      }),
      studentClass: new FormControl(paybillRawValue.studentClass, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      paymentChannel: new FormControl(paybillRawValue.paymentChannel, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(paybillRawValue.freeField1),
      freeField2: new FormControl(paybillRawValue.freeField2),
      freeField3: new FormControl(paybillRawValue.freeField3),
      biller: new FormControl(paybillRawValue.biller),
    });
  }

  getPaybill(form: PaybillFormGroup): IPaybill | NewPaybill {
    return form.getRawValue() as IPaybill | NewPaybill;
  }

  resetForm(form: PaybillFormGroup, paybill: PaybillFormGroupInput): void {
    const paybillRawValue = { ...this.getFormDefaults(), ...paybill };
    form.reset(
      {
        ...paybillRawValue,
        id: { value: paybillRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaybillFormDefaults {
    return {
      id: null,
    };
  }
}
