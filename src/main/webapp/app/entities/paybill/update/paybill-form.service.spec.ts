import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../paybill.test-samples';

import { PaybillFormService } from './paybill-form.service';

describe('Paybill Form Service', () => {
  let service: PaybillFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaybillFormService);
  });

  describe('Service methods', () => {
    describe('createPaybillFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaybillFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            processTimestamp: expect.any(Object),
            feeAmount: expect.any(Object),
            feeDescription: expect.any(Object),
            feeDueFromDate: expect.any(Object),
            feeDueToDate: expect.any(Object),
            feeId: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            middleName: expect.any(Object),
            outstandingAmount: expect.any(Object),
            paymentCode: expect.any(Object),
            schoolCode: expect.any(Object),
            schoolName: expect.any(Object),
            studentClass: expect.any(Object),
            paymentChannel: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            biller: expect.any(Object),
          })
        );
      });

      it('passing IPaybill should create a new form with FormGroup', () => {
        const formGroup = service.createPaybillFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            processTimestamp: expect.any(Object),
            feeAmount: expect.any(Object),
            feeDescription: expect.any(Object),
            feeDueFromDate: expect.any(Object),
            feeDueToDate: expect.any(Object),
            feeId: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            middleName: expect.any(Object),
            outstandingAmount: expect.any(Object),
            paymentCode: expect.any(Object),
            schoolCode: expect.any(Object),
            schoolName: expect.any(Object),
            studentClass: expect.any(Object),
            paymentChannel: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            biller: expect.any(Object),
          })
        );
      });
    });

    describe('getPaybill', () => {
      it('should return NewPaybill for default Paybill initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPaybillFormGroup(sampleWithNewData);

        const paybill = service.getPaybill(formGroup) as any;

        expect(paybill).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaybill for empty Paybill initial value', () => {
        const formGroup = service.createPaybillFormGroup();

        const paybill = service.getPaybill(formGroup) as any;

        expect(paybill).toMatchObject({});
      });

      it('should return IPaybill', () => {
        const formGroup = service.createPaybillFormGroup(sampleWithRequiredData);

        const paybill = service.getPaybill(formGroup) as any;

        expect(paybill).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaybill should not enable id FormControl', () => {
        const formGroup = service.createPaybillFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaybill should disable id FormControl', () => {
        const formGroup = service.createPaybillFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
