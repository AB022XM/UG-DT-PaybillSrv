import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../payment-channel.test-samples';

import { PaymentChannelFormService } from './payment-channel-form.service';

describe('PaymentChannel Form Service', () => {
  let service: PaymentChannelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentChannelFormService);
  });

  describe('Service methods', () => {
    describe('createPaymentChannelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaymentChannelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            channelId: expect.any(Object),
            channelCode: expect.any(Object),
            channelName: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing IPaymentChannel should create a new form with FormGroup', () => {
        const formGroup = service.createPaymentChannelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            channelId: expect.any(Object),
            channelCode: expect.any(Object),
            channelName: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getPaymentChannel', () => {
      it('should return NewPaymentChannel for default PaymentChannel initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPaymentChannelFormGroup(sampleWithNewData);

        const paymentChannel = service.getPaymentChannel(formGroup) as any;

        expect(paymentChannel).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaymentChannel for empty PaymentChannel initial value', () => {
        const formGroup = service.createPaymentChannelFormGroup();

        const paymentChannel = service.getPaymentChannel(formGroup) as any;

        expect(paymentChannel).toMatchObject({});
      });

      it('should return IPaymentChannel', () => {
        const formGroup = service.createPaymentChannelFormGroup(sampleWithRequiredData);

        const paymentChannel = service.getPaymentChannel(formGroup) as any;

        expect(paymentChannel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaymentChannel should not enable id FormControl', () => {
        const formGroup = service.createPaymentChannelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaymentChannel should disable id FormControl', () => {
        const formGroup = service.createPaymentChannelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
