import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPayment, NewPayment } from '../payment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPayment for edit and NewPaymentFormGroupInput for create.
 */
type PaymentFormGroupInput = IPayment | PartialWithRequiredKeyOf<NewPayment>;

type PaymentFormDefaults = Pick<NewPayment, 'id'>;

type PaymentFormGroupContent = {
  id: FormControl<IPayment['id'] | NewPayment['id']>;
  recordUniqueIdentifier: FormControl<IPayment['recordUniqueIdentifier']>;
  paymentId: FormControl<IPayment['paymentId']>;
  paymentCode: FormControl<IPayment['paymentCode']>;
  customerId: FormControl<IPayment['customerId']>;
  feeAmount: FormControl<IPayment['feeAmount']>;
  isAmountFixed: FormControl<IPayment['isAmountFixed']>;
  feeDescription: FormControl<IPayment['feeDescription']>;
  fixedAmount: FormControl<IPayment['fixedAmount']>;
  paymentName: FormControl<IPayment['paymentName']>;
  outstandingAmount: FormControl<IPayment['outstandingAmount']>;
  paymentChannel: FormControl<IPayment['paymentChannel']>;
  freeField1: FormControl<IPayment['freeField1']>;
  freeField2: FormControl<IPayment['freeField2']>;
  freeField3: FormControl<IPayment['freeField3']>;
  biller: FormControl<IPayment['biller']>;
};

export type PaymentFormGroup = FormGroup<PaymentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentFormService {
  createPaymentFormGroup(payment: PaymentFormGroupInput = { id: null }): PaymentFormGroup {
    const paymentRawValue = {
      ...this.getFormDefaults(),
      ...payment,
    };
    return new FormGroup<PaymentFormGroupContent>({
      id: new FormControl(
        { value: paymentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(paymentRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      paymentId: new FormControl(paymentRawValue.paymentId, {
        validators: [Validators.required],
      }),
      paymentCode: new FormControl(paymentRawValue.paymentCode, {
        validators: [Validators.required],
      }),
      customerId: new FormControl(paymentRawValue.customerId),
      feeAmount: new FormControl(paymentRawValue.feeAmount),
      isAmountFixed: new FormControl(paymentRawValue.isAmountFixed),
      feeDescription: new FormControl(paymentRawValue.feeDescription, {
        validators: [Validators.minLength(3), Validators.maxLength(200)],
      }),
      fixedAmount: new FormControl(paymentRawValue.fixedAmount, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      paymentName: new FormControl(paymentRawValue.paymentName, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
      }),
      outstandingAmount: new FormControl(paymentRawValue.outstandingAmount),
      paymentChannel: new FormControl(paymentRawValue.paymentChannel, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(paymentRawValue.freeField1),
      freeField2: new FormControl(paymentRawValue.freeField2),
      freeField3: new FormControl(paymentRawValue.freeField3),
      biller: new FormControl(paymentRawValue.biller),
    });
  }

  getPayment(form: PaymentFormGroup): IPayment | NewPayment {
    return form.getRawValue() as IPayment | NewPayment;
  }

  resetForm(form: PaymentFormGroup, payment: PaymentFormGroupInput): void {
    const paymentRawValue = { ...this.getFormDefaults(), ...payment };
    form.reset(
      {
        ...paymentRawValue,
        id: { value: paymentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentFormDefaults {
    return {
      id: null,
    };
  }
}
