import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaymentChannel, NewPaymentChannel } from '../payment-channel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentChannel for edit and NewPaymentChannelFormGroupInput for create.
 */
type PaymentChannelFormGroupInput = IPaymentChannel | PartialWithRequiredKeyOf<NewPaymentChannel>;

type PaymentChannelFormDefaults = Pick<NewPaymentChannel, 'id'>;

type PaymentChannelFormGroupContent = {
  id: FormControl<IPaymentChannel['id'] | NewPaymentChannel['id']>;
  recordUniqueIdentifier: FormControl<IPaymentChannel['recordUniqueIdentifier']>;
  channelId: FormControl<IPaymentChannel['channelId']>;
  channelCode: FormControl<IPaymentChannel['channelCode']>;
  channelName: FormControl<IPaymentChannel['channelName']>;
  status: FormControl<IPaymentChannel['status']>;
  freeField1: FormControl<IPaymentChannel['freeField1']>;
  freeField2: FormControl<IPaymentChannel['freeField2']>;
  freeField3: FormControl<IPaymentChannel['freeField3']>;
  isDeleted: FormControl<IPaymentChannel['isDeleted']>;
};

export type PaymentChannelFormGroup = FormGroup<PaymentChannelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentChannelFormService {
  createPaymentChannelFormGroup(paymentChannel: PaymentChannelFormGroupInput = { id: null }): PaymentChannelFormGroup {
    const paymentChannelRawValue = {
      ...this.getFormDefaults(),
      ...paymentChannel,
    };
    return new FormGroup<PaymentChannelFormGroupContent>({
      id: new FormControl(
        { value: paymentChannelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(paymentChannelRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      channelId: new FormControl(paymentChannelRawValue.channelId, {
        validators: [Validators.required],
      }),
      channelCode: new FormControl(paymentChannelRawValue.channelCode),
      channelName: new FormControl(paymentChannelRawValue.channelName, {
        validators: [Validators.required],
      }),
      status: new FormControl(paymentChannelRawValue.status),
      freeField1: new FormControl(paymentChannelRawValue.freeField1),
      freeField2: new FormControl(paymentChannelRawValue.freeField2),
      freeField3: new FormControl(paymentChannelRawValue.freeField3),
      isDeleted: new FormControl(paymentChannelRawValue.isDeleted),
    });
  }

  getPaymentChannel(form: PaymentChannelFormGroup): IPaymentChannel | NewPaymentChannel {
    return form.getRawValue() as IPaymentChannel | NewPaymentChannel;
  }

  resetForm(form: PaymentChannelFormGroup, paymentChannel: PaymentChannelFormGroupInput): void {
    const paymentChannelRawValue = { ...this.getFormDefaults(), ...paymentChannel };
    form.reset(
      {
        ...paymentChannelRawValue,
        id: { value: paymentChannelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentChannelFormDefaults {
    return {
      id: null,
    };
  }
}
